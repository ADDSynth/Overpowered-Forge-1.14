package addsynth.core.inventory.container.slots;

import javax.annotation.Nonnull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RestrictedSlot extends SlotItemHandler { // OPTIMIZE: combine InputSlot and Restricted Slot

  private final Item[] valid_items;

  public RestrictedSlot(IItemHandler itemHandler, int index, final Item[] valid_items, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
    this.valid_items = valid_items;
  }

  @Override
  public final boolean isItemValid(@Nonnull final ItemStack stack){
    if(valid_items == null){
      return super.isItemValid(stack);
    }
    final Item item = stack.getItem();
    for(Item test_item : valid_items){
      if(item == test_item){
        return super.isItemValid(stack);
      }
    }
    return false;
  }

}
