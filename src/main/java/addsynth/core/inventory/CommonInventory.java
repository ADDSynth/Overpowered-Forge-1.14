package addsynth.core.inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Base class for inventories in ADDSynth's Mods. Contains all common functionality.
 * This can also be used as a standard Inventory and allows machines to insert and extract items.
 * When an inventory changes, the changes must be saved to disk. So for this reason,
 * you must specify a {@link IInventoryUser} that responds to inventory changes.
 * @author ADDSynth
 */
public class CommonInventory extends ItemStackHandler {

  @Nullable
  private IInventoryResponder responder;

  protected CommonInventory(final IInventoryUser responder, final int number_of_slots){
    super(number_of_slots);
    this.responder = responder;
  }

  public static CommonInventory create(final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new CommonInventory(null, slots.length) : null) : null;
  }
  
  public static CommonInventory create(final int number_of_slots){
    return number_of_slots > 0 ? new CommonInventory(null, number_of_slots) : null;
  }

  public static CommonInventory create(final IInventoryUser responder, final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new CommonInventory(responder, slots.length) : null) : null;
  }

  public static CommonInventory create(final IInventoryUser responder, final int number_of_slots){
    return number_of_slots > 0 ? new CommonInventory(responder, number_of_slots) : null;
  }

  /** Use this to set a custom responder to Inventory changes. This overrides the default behavior. */
  public final void setResponder(final IInventoryResponder responder){
    this.responder = responder;
  }

  @Override
  public final void setStackInSlot(final int slot, final @Nonnull ItemStack stack){
    if(is_valid_slot(slot)){
      this.stacks.set(slot, stack);
      onContentsChanged(slot);
    }
  }

  /** Extracts the entire ItemStack from the slot. */
  public final ItemStack extractItemStack(final int slot){
    final ItemStack stack = getStackInSlot(slot);
    setStackInSlot(slot, ItemStack.EMPTY);
    return stack;
  }

  public final boolean isEmpty(){
    for(final ItemStack stack : stacks){
      if(stack.isEmpty() == false){
        return false;
      }
    }
    return true;
  }

  /** Sets all slots in the inventory to Empty. */
  public final void setEmpty(){
    int i;
    for(i = 0; i < stacks.size(); i++){
      setStackInSlot(i, ItemStack.EMPTY);
    }
  }

  /** Returns all the ItemStacks in this Inventory. However, these ItemStacks SHOULD NOT
   *  be modified! You should only use this to check if the items matches a
   *  crafting recipe. You can also use {@link InventoryUtil#toInventory(ItemStackHandler)}
   *  if the inventory is an {@link ItemStackHandler}.
   */
  public final ItemStack[] getItemStacks(){
    return stacks.toArray(new ItemStack[stacks.size()]);
  }

  public final void drop_in_world(final World world, final BlockPos pos){
    drop_in_world(world, pos.getX(), pos.getY(), pos.getZ());
  }

  public final void drop_in_world(final World world, final double x, final double y, final double z){
    for(final ItemStack stack : stacks){
      if(stack.isEmpty() == false){
        InventoryHelper.spawnItemStack(world, x, y, z, stack);
      }
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
  @Deprecated
  protected final void validateSlotIndex(int slot){
    // in order to crash properly, this method can't do anything.
  }

  public void save(final CompoundNBT nbt){
    nbt.put("Inventory", serializeNBT());
  }

  public void load(final CompoundNBT nbt){
    deserializeNBT(nbt.getCompound("Inventory"));
  }

  public final void save(final CompoundNBT nbt, final String name){
    nbt.put(name, serializeNBT());
  }
  
  public final void load(final CompoundNBT nbt, final String name){
    deserializeNBT(nbt.getCompound(name));
  }

  @Override
  protected final void onContentsChanged(final int slot){
    if(responder != null){
      responder.onInventoryChanged();
    }
  }

}
