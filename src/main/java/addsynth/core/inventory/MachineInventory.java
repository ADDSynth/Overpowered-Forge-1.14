package addsynth.core.inventory;

import java.util.function.Function;
import addsynth.core.items.ItemUtil;
import addsynth.core.recipe.shapeless.RecipeCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Machines that move a recipe from an Input to a working Inventory use this. */
public final class MachineInventory {

  public final InputInventory input_inventory;
  public final CommonInventory working_inventory;
  public final OutputInventory output_inventory;
  private ItemStack result;

  public <T extends IInputInventory & IOutputInventory> MachineInventory
  (T responder, final SlotData[] slots, final int output_slots){
    input_inventory   =  InputInventory.create(responder, slots);
    working_inventory = CommonInventory.create(slots);
    output_inventory  = OutputInventory.create(responder, output_slots);
  }

  public <T extends IInputInventory & IOutputInventory> MachineInventory
  (T responder, final int input_slots, final Item[] filter, final int output_slots){
    input_inventory   =  InputInventory.create(responder, input_slots, filter);
    working_inventory = CommonInventory.create(input_slots);
    output_inventory  = OutputInventory.create(responder, output_slots);
  }

  /** For standard machine inventories, this must be called to check if the inventory contains a valid recipe. */
  public final boolean can_work(final RecipeCollection recipes){
    result = recipes.getResult(input_inventory.getItemStacks(), null);
    return ItemUtil.itemStackExists(result) ? output_inventory.can_add(0, result) : false;
  }

  /** For standard machine inventories, this must be called to check if the inventory contains a valid recipe. */
  public final boolean can_work(final Function<ItemStack, ItemStack> result_provider){
    result = result_provider.apply(input_inventory.getStackInSlot(0));
    return ItemUtil.itemStackExists(result) ? output_inventory.can_add(0, result) : false;
  }

  /** Use this if your inventory has lots of slots, otherwise it's more efficient to check directly.
   *  @see addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser#test_condition()
   *  @see addsynth.overpoweredmod.machines.identifier.TileIdentifier#test_condition()
   */
  public final boolean can_work(){
    return !input_inventory.isEmpty() && output_inventory.isEmpty();
  }

  /** Decrements all inputs by 1 and transfers them to the working inventory
   *  to begin working on it.<br />
   *  <b>Note:</b> It is STRONGLY recommended you DO NOT Override this! */
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
    result = ItemUtil.loadItemStackFromNBT(nbt, "Output");
  }

  public final void saveToNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.save(nbt);}
    if(working_inventory != null){ working_inventory.save(nbt, "WorkingInventory");}
    if( output_inventory != null){  output_inventory.save(nbt);}
    ItemUtil.saveItemStackToNBT(nbt, result, "Output");
  }

  public final void drop(final BlockPos pos, final World world){
    InventoryUtil.drop_inventories(pos, world, input_inventory, working_inventory, output_inventory);
  }

}
