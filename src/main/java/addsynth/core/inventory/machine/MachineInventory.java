package addsynth.core.inventory.machine;

import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.IInventoryResponder;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.InventoryUtil;
import addsynth.core.inventory.OutputInventory;
import addsynth.core.inventory.SlotData;
import addsynth.core.inventory.WorkingInventory;
import addsynth.core.recipe.jobs.JobSystem;
import addsynth.core.recipe.jobs.WorkJob;
import addsynth.core.recipe.shapeless.RecipeCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Machines that move a recipe from an Input to a {@link WorkingInventory} use this. */
public final class MachineInventory implements IInventoryResponder, IInventorySystem {

  private final InputInventory input_inventory;
  private final WorkingInventory working_inventory;
  private final OutputInventory output_inventory;
  private boolean changed;
  private boolean can_work;
  @Nonnull
  private WorkJob[] jobs = new WorkJob[0];
  @Nullable
  private Function<ItemStack[], ItemStack> resultProvider;

  public MachineInventory(final SlotData[] slots, final int output_slots){
    input_inventory   =  InputInventory.create(null, slots);
    working_inventory = WorkingInventory.create(slots);
    output_inventory  = OutputInventory.create(null, output_slots);
    setResponder(this);
  }

  public MachineInventory(final int input_slots, final Item[] filter, final int output_slots){
    input_inventory   =  InputInventory.create(null, input_slots, filter);
    working_inventory = WorkingInventory.create(input_slots);
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
  }

  /** Do NOT call this every time the inventory is changed! Only call
   *  this once per tick, or when we need to recheck the input inventory. */
  @Override
  public final void recheck(){
    if(resultProvider != null){
      // !! We still need to get the result of the input, because we ALSO need to check if we can output it into the output slots!
      jobs = JobSystem.getJobs(input_inventory.getItemStacks(), resultProvider);
      can_work = jobs.length > 0 ? output_inventory.can_add(0, jobs[0].getResult()) : false;
    }
    else{
      // TileEntity wants to handle the logic itself
      jobs = JobSystem.getJobs(input_inventory.getItemStacks());
      can_work = jobs.length > 0 && output_inventory.isEmpty();
    }
  }

  @Override
  public final boolean can_work(){
    return can_work;
  }

  public final int getJobs(){
    return jobs != null ? jobs.length : 0;
  }

  @Override
  public final boolean tick(){
    if(changed){
      recheck();
      changed = false;
      return true;
    }
    return false;
  }

  /** Decrements all inputs by 1 and transfers them to the working inventory.<br>
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
      if(jobs.length > 0){
        working_inventory.setResult(jobs[0].getResult());
      }
    }
  }

  /** Passes result ItemStack to the output inventory, clears the working inventory,
   *  then recalculates jobs.  */
  public final void finish_work(){
    output_inventory.insertItem(0, working_inventory.getResult(), false);
    // OPTIMIZE: actually, there's no need to save the result of the Working inventory, just pass its contents into the
    //           same resultProvider to output the result.
    working_inventory.setEmpty();
    recheck(); // For machines it is normal to check if we can do another job, once this one finishes.
  }

  public final void loadFromNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.load(nbt);}
    if(working_inventory != null){ working_inventory.load(nbt);}
    if( output_inventory != null){  output_inventory.load(nbt);}
    recheck();
  }

  public final void saveToNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.save(nbt);}
    if(working_inventory != null){ working_inventory.save(nbt);}
    if( output_inventory != null){  output_inventory.save(nbt);}
  }

  public final void drop(final BlockPos pos, final World world){
    InventoryUtil.drop_inventories(pos, world, input_inventory, working_inventory, output_inventory);
  }

  public final InputInventory getInputInventory(){
    return input_inventory;
  }
  
  public final WorkingInventory getWorkingInventory(){
    return working_inventory;
  }
  
  public final OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
