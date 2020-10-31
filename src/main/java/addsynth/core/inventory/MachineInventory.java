package addsynth.core.inventory;

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

  public final boolean can_work(){
    // Input must match a recipe.
    // Result of recipe must be able to be inserted into the Output.
    return false;
  }

  /** Decrements all inputs by 1 and transfers them to the output inventory.
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

  public final void clear_working_inventory(){
    working_inventory.setEmpty();
  }

  public final void loadFromNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.load(nbt);}
    if(working_inventory != null){ working_inventory.load(nbt, "WorkingInventory");}
    if( output_inventory != null){  output_inventory.load(nbt);}
  }

  public final void saveToNBT(final CompoundNBT nbt){
    if(  input_inventory != null){   input_inventory.save(nbt);}
    if(working_inventory != null){ working_inventory.save(nbt, "WorkingInventory");}
    if( output_inventory != null){  output_inventory.save(nbt);}
  }

  public final void drop(final BlockPos pos, final World world){
    InventoryUtil.drop_inventories(pos, world, input_inventory, working_inventory, output_inventory);
  }

}
