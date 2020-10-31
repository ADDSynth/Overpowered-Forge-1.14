package addsynth.core.inventory;

import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemHandlerHelper;

/** This inventory only allows items to be inserted. It also has an Item filter to control
 *  which items are allowed. Pass null as the item filter to allow all items.
 */
public final class InputInventory extends CommonInventory {

  private final SlotData[] slot_data;
  public boolean custom_stack_limit_is_vanilla_dependant = true;

  private InputInventory(final IInputInventory responder, @Nonnull final SlotData[] slots){
    super(responder, slots.length);
    this.slot_data = slots;
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots, null)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots, final Item[] filter){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots, filter)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new InputInventory(responder, slots) : null) : null;
  }

  @Override
  public @Nonnull ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate){
    if(stack.isEmpty()){       return ItemStack.EMPTY; }
    if(stack.getCount() == 0){ return ItemStack.EMPTY; }
    if(is_valid_slot(slot) == false){ return stack; }
    try{
      if(this.slot_data[slot].is_item_valid(stack) == false){
        return stack;
      }
      final ItemStack existing_stack = this.stacks.get(slot);
      int limit = getStackLimit(slot, stack);
      if(existing_stack.isEmpty() == false){
        if(ItemHandlerHelper.canItemStacksStack(stack, existing_stack) == false){
          return stack;
        }
        limit -= existing_stack.getCount();
      }
      if(limit <= 0){
        return stack;
      }
      final boolean reached_limit = stack.getCount() > limit;
      if(simulate == false){
        if(existing_stack.isEmpty()){
          if(reached_limit){ this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(stack, limit)); }
          else{              this.stacks.set(slot, stack); }
        }
        else{
          if(reached_limit){ existing_stack.grow(limit); }
          else{              existing_stack.grow(stack.getCount()); }
        }
        onContentsChanged(slot);
      }
      if(reached_limit){
        return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit);
      }
      return ItemStack.EMPTY; // none left over, all of stack was added.
    }
    catch(Exception e){
      e.printStackTrace();
      return ItemStack.EMPTY;
    }
  }

  @Override
  public @Nonnull ItemStack extractItem(int slot, int amount, boolean simulate){
    if(amount == 0){ return ItemStack.EMPTY; }
    try{
      validateSlotIndex(slot);
      final ItemStack existing_stack = this.stacks.get(slot);
      if(existing_stack.isEmpty()){ return ItemStack.EMPTY; }
      final int extract_amount = Math.min(amount, existing_stack.getMaxStackSize());
      if(existing_stack.getCount() <= extract_amount){
        if(simulate == false){
          this.stacks.set(slot, ItemStack.EMPTY);
          onContentsChanged(slot);
        }
        return existing_stack;
      }
      if(simulate == false){
        this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing_stack, existing_stack.getCount() - extract_amount));
        onContentsChanged(slot);
      }
      return ItemHandlerHelper.copyStackWithSize(existing_stack, extract_amount);
    }
    catch(Exception e){
      e.printStackTrace();
      return ItemStack.EMPTY;
    }
  }

  public final void add(final int slot, final ItemStack stack){
    insertItem(slot, stack, false);
  }

  public final void decrease(final int slot){
    decrease(slot, 1);
  }

  public final void decrease(final int slot, final int amount){
    try{
      if(stacks.get(slot) != null){
        if(stacks.get(slot).isEmpty() == false){
          stacks.get(slot).shrink(amount);
        }
      }
    }
    catch(IndexOutOfBoundsException e){
      ADDSynthCore.log.error("InputInventory -> decrease() function: slot argument is out of bounds. Must be within 0 and "+(stacks.size() - 1)+".");
      e.printStackTrace();
    }
    catch(NullPointerException e){
      e.printStackTrace();
    }
  }

  public final void decrease_input(){
    decrease_input(1);
  }

  public final void decrease_input(final int amount){
    int i;
    for(i = 0; i < stacks.size(); i++){
      decrease(i,amount);
    }
  }

  @Override
  protected final int getStackLimit(final int slot, @Nonnull final ItemStack stack){
    if(this.slot_data[slot].stack_limit < 0){
      return stack.getMaxStackSize();
    }
    if(custom_stack_limit_is_vanilla_dependant){
      return Math.min(this.slot_data[slot].stack_limit, stack.getMaxStackSize());
    }
    return this.slot_data[slot].stack_limit;
  }

  @Override
  public final void save(final CompoundNBT nbt){
    nbt.put("InputInventory", serializeNBT());
  }

  @Override
  public final void load(final CompoundNBT nbt){
    deserializeNBT(nbt.getCompound("InputInventory"));
  }

}
