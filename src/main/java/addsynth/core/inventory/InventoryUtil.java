package addsynth.core.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public final class InventoryUtil {

  public static final Inventory toInventory(final ItemStackHandler handler){
    return new Inventory(getItemStacks(handler));
  }

  public static final ItemStack[] getItemStacks(final ItemStackHandler handler){
    final int max = handler.getSlots();
    final ItemStack[] stacks = new ItemStack[max];
    int i;
    for(i = 0; i < max; i++){
      stacks[i] = handler.getStackInSlot(i);
    }
    return stacks;
  }

}
