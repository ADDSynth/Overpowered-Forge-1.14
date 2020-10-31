package addsynth.core.inventory;

import javax.annotation.Nonnull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** SlotData allows you to specify item filter and stack limit on a per-slot basis.
 *  {@link InputInventory} uses this. */
public final class SlotData {

  public final Item[] filter;
  public final int stack_limit;

  public final static SlotData[] create_new_array(final int number_of_slots){
    return create_new_array(number_of_slots, null);
  }

  public final static SlotData[] create_new_array(final int number_of_slots, final Item[] filter){
    final SlotData[] data = new SlotData[number_of_slots];
    int i;
    for(i = 0; i < number_of_slots; i++){
      data[i] = new SlotData(filter);
    }
    return data;
  }

  public SlotData(){
    this.filter = null;
    this.stack_limit = -1; // stack size depends on the ItemStack
  }

  public SlotData(final Item item){
    this.filter = new Item[]{item};
    this.stack_limit = -1;
  }

  public SlotData(final Item[] filter){
    this.filter = filter;
    this.stack_limit = -1;
  }

  public SlotData(final int slot_limit){
    this.filter = null;
    this.stack_limit = slot_limit;
  }

  public SlotData(final Item[] filter, final int slot_limit){
    this.filter = filter;
    this.stack_limit = slot_limit;
  }

  public final boolean is_item_valid(@Nonnull final ItemStack stack){
    if(filter == null){
      return true;
    }
    if(stack.isEmpty() == false){
      final Item item = stack.getItem();
      for(Item valid_item : filter){
        if(item == valid_item){
          return true;
        }
      }
    }
    return false;
  }

}
