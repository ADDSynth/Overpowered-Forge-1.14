package addsynth.core.inventory;

import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.items.ItemUtil;
import addsynth.core.recipe.shapeless.RecipeCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Machines that move a recipe from an Input to a working Inventory use this. */
public final class MachineInventory implements IInventoryResponder, IInventorySystem {

  private final InputInventory input_inventory;
  private final CommonInventory working_inventory;
  private final OutputInventory output_inventory;
  private boolean changed;
  private boolean can_work;
  /** Inventory System result field is only used to check
   *  if the result can be inserted into the output inventory. */
  @Nonnull
  private ItemStack result = ItemStack.EMPTY;
  @Nullable
  private Function<ItemStack[], ItemStack> resultProvider;

  public MachineInventory(final SlotData[] slots, final int output_slots){
    input_inventory   =  InputInventory.create(null, slots);
    working_inventory = CommonInventory.create(slots);
    output_inventory  = OutputInventory.create(null, output_slots);
    setResponder(this);
  }

  public MachineInventory(final int input_slots, final Item[] filter, final int output_slots){
    input_inventory   =  InputInventory.create(null, input_slots, filter);
    working_inventory = CommonInventory.create(input_slots);
    output_inventory  = OutputInventory.create(null, output_slots);
    setResponder(this);
  }

  /** Your TileEntity MUST call this in its constructor! */
  public final void setRecipeProvider(final RecipeCollection recipe_collection){
    resultProvider = (ItemStack[] input) -> {
      return recipe_collection.getResult(input, null);
    };
  }

  ///** Your TileEntity MUST call this in its constructor! */
  //public final void setRecipeProvider(Function<ItemStack[], ItemStack> resultProvider){
  //  this.resultProvider = resultProvider;
  //}

  /** Your TileEntity MUST call this in its constructor! */
  public final void setRecipeProvider(Function<ItemStack, ItemStack> resultProvider){
    this.resultProvider = (ItemStack[] input) -> {
      return resultProvider.apply(input[0]);
    };
  }

  public final void setResponder(final IInventoryResponder responder){
    if(  input_inventory != null){  input_inventory.setResponder(responder);}
    if(working_inventory != null){working_inventory.setResponder(responder);}
    if( output_inventory != null){ output_inventory.setResponder(responder);}
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
    recheck();
  }

  @Override
  public final void recheck(){
    if(resultProvider != null){
      result = resultProvider.apply(input_inventory.getItemStacks());
      can_work = ItemUtil.itemStackExists(result) ? output_inventory.can_add(0, result) : false;
    }
    else{
      // ADDSynthCore.log.fatal(new NullPointerException("MachineInventory does not have a recipeProvider. Your TileEntity forgot to call setRecipeProvider()!"));
      result = ItemStack.EMPTY;
      can_work = false;
    }
  }

  @Override
  public final boolean can_work(){
    return can_work;
  }

  @Override
  public final boolean tick(){
    if(changed){
      changed = false;
      return true;
    }
    return false;
  }

  /** Decrements all inputs by 1 and transfers them to the working inventory
   *  to begin working on it.<br />
   *  <b>Note:</b> It is STRONGLY recommended you DO NOT Override this! */
  @Override
  public void begin_work(){
    if(input_inventory != null){
      int i;
      ItemStack stack;
      for(i = 0; i < input_inventory.getSlots(); i++){
        stack = input_inventory.extractItem(i, 1, false);
        working_inventory.setStackInSlot(i, stack);
      }
    }
  }

  public final void output_result(){
    output_inventory.insertItem(0, result.copy(), false);
  }

  public final void clear_working_inventory(){
    working_inventory.setEmpty();
  }

  public final void loadFromNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.load(nbt);}
    if(working_inventory != null){ working_inventory.load(nbt, "WorkingInventory");}
    if( output_inventory != null){  output_inventory.load(nbt);}
    // !!! IMPORTANT: machines need to remember the result of the recipe they're currently working on!
    // result = ItemUtil.loadItemStackFromNBT(nbt, "Output");
    recheck();
  }

  public final void saveToNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.save(nbt);}
    if(working_inventory != null){ working_inventory.save(nbt, "WorkingInventory");}
    if( output_inventory != null){  output_inventory.save(nbt);}
    // ItemUtil.saveItemStackToNBT(nbt, result, "Output");
  }

  public final void drop(final BlockPos pos, final World world){
    InventoryUtil.drop_inventories(pos, world, input_inventory, working_inventory, output_inventory);
  }

  public final InputInventory getInputInventory(){
    return input_inventory;
  }
  
  public final CommonInventory getWorkingInventory(){
    return working_inventory;
  }
  
  public final OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
