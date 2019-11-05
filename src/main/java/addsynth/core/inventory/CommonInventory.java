package addsynth.core.inventory;

import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import addsynth.core.tiles.TileMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class CommonInventory extends ItemStackHandler {

  private final TileMachine responder;

  public CommonInventory(final TileMachine responder, final int number_of_slots){
    super(number_of_slots);
    this.responder = responder;
  }

  @Override
  public final void setStackInSlot(final int slot, final @Nonnull ItemStack stack){
    if(is_valid_slot(slot)){
      if(ItemStack.areItemStacksEqual(this.stacks.get(slot), stack) == false){
        this.stacks.set(slot, stack);
        onContentsChanged(slot);
      }
    }
  }

  /** I've checked. This calls onInventoryChanged() in {@link TileMachine} and is only used
   *  to call test_condition() in {@link addsynth.energy.tiles.machines.PassiveMachine}, which
   *  must check both the Input and Output inventories, so passing the slot index is useless.
   *  We can pass an Inventory_Type enum value if we really need to.
   */
  @Override
  protected final void onContentsChanged(final int slot){
    if(responder != null){
      responder.onInventoryChanged();
    }
  }

  protected final boolean is_valid_slot(final int slot){
    final int size = stacks.size();
    if(size > 0){
      if(slot >= 0 && slot < size){
        return true;
      }
      if(size == 1){
        ADDSynthCore.log.error("Invalid slot: "+slot+", there is only slot 0.");
      }
      else{
        ADDSynthCore.log.error("Invalid slot: "+slot+", only 0 to "+Integer.toString(size - 1)+" allowed.");
      }
    }
    else{
      ADDSynthCore.log.error("Invalid slot: "+slot+", this ItemStackHandler does not have any slots.");
    }
    Thread.dumpStack();
    return false;
  }

  @Override
  protected final void validateSlotIndex(int slot){
    // in order to crash properly, this method can't do anything.
  }

}
