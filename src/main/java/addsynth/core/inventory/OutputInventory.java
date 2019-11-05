package addsynth.core.inventory;

import addsynth.core.tiles.TileMachine;
import net.minecraft.item.ItemStack;

public final class OutputInventory extends CommonInventory {

  public OutputInventory(final TileMachine responder, final int output_slots){
    super(responder, output_slots);
  }

  /** This is useful for machines. Tests whether the input stack can fully be added to the output slot. */
  public final boolean can_add(final int slot, final ItemStack input_stack){
    if(input_stack == null){   return false; }
    if(input_stack.isEmpty()){ return false; }
    if(is_valid_slot(slot)){
      final ItemStack existing_stack = getStackInSlot(slot);
      if(existing_stack.isEmpty()){
        return true;
      }
      return existing_stack.isItemEqual(input_stack) && existing_stack.getCount() + input_stack.getCount() <= getStackLimit(slot, existing_stack);
    }
    return false;
  }

}
