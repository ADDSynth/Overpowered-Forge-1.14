package addsynth.core.game.inventory;

import javax.annotation.Nonnull;
import addsynth.core.game.items.ItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

/** This is the inventory that machines transfer their items to while they're working on them. */
public final class WorkingInventory extends CommonInventory {

  @Nonnull
  private ItemStack result = ItemStack.EMPTY;

  private WorkingInventory(final IInventoryResponder responder, int number_of_slots){
    super(responder, number_of_slots);
  }

  public static final WorkingInventory create(final int number_of_slots){
    return number_of_slots > 0 ? new WorkingInventory(null, number_of_slots) : null;
  }

  public static final WorkingInventory create(final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new WorkingInventory(null, slots.length) : null) : null;
  }

  public static final WorkingInventory create(final IInventoryResponder responder, final int number_of_slots){
    return number_of_slots > 0 ? new WorkingInventory(responder, number_of_slots) : null;
  }

  public static final WorkingInventory create(final IInventoryResponder responder, final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new WorkingInventory(responder, slots.length) : null) : null;
  }

  public final void setResult(@Nonnull final ItemStack result){
    this.result = result;
  }
  
  public final ItemStack getResult(){
    return result.copy();
  }

  @Override
  public final void setEmpty(){
    super.setEmpty();
    result = ItemStack.EMPTY;
  }

  @Override
  public final void save(final CompoundNBT nbt){
    nbt.put("WorkingInventory", serializeNBT());
    // IMPORTANT: Need to remember the result of the recipe we're currently working on!
    ItemUtil.saveItemStackToNBT(nbt, result, "Output");
  }

  @Override
  public final void load(final CompoundNBT nbt){
    deserializeNBT(nbt.getCompound("WorkingInventory"));
    result = ItemUtil.loadItemStackFromNBT(nbt, "Output"); // TODO: Not necessarily important to change the name its saved to, but I can change it to a more purposeful name, once I create the NBTUtil class to help in saving/loading to a new name.
  }

}
