package addsynth.core.inventory;

import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public final class OutputInventory extends CommonInventory {

  private OutputInventory(final IOutputInventory responder, final int output_slots){
    super(responder, output_slots);
  }

  public static final OutputInventory create(final IOutputInventory responder, final int number_of_slots){
    return number_of_slots > 0 ? new OutputInventory(responder, number_of_slots) : null;
  }

  /** This is useful for machines. Tests whether the input stack can fully be added to the output slot. */
  public final boolean can_add(final int slot, @Nullable final ItemStack input_stack){
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

  @Override
  public final void save(final CompoundNBT nbt){
    nbt.put("OutputInventory", serializeNBT());
  }

  @Override
  public final void load(final CompoundNBT nbt){
    deserializeNBT(nbt.getCompound("OutputInventory"));
  }

}
