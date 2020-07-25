package addsynth.core.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.items.ItemUtil;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/** Use this to create a TileEntity with a single generic inventory.
 *  Players and Hoppers can insert and extract ANY type of item. Please
 *  use {@link TileMachine} if you need to restrict what types of items
 *  can be inserted, or have Hoppers to only extract from Output slots.
 * @author ADDSynth
 */
// FEATURE: I can upgrade this later if I really need a storage inventory to restrict what items can be inserted.
//    TODO: Make Fusion Chamber and Portal Frame override from this class instead of TileMachine.
public abstract class TileWithStorage extends TileBase {

  protected final ItemStackHandler inventory;

  public TileWithStorage(final TileEntityType type, ItemStackHandler inventory){
    super(type);
    this.inventory = inventory;
  }

  @Override
  public void read(CompoundNBT nbt){
    super.read(nbt);
    if(inventory != null){
      inventory.deserializeNBT(nbt.getCompound("ItemStackHandler")); // FEATURE: develope a system that checks for the old name and saves it under the new name.
    }
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt){
    super.write(nbt);
    if(inventory != null){
      nbt.put("ItemStackHandler",inventory.serializeNBT());
    }
    return nbt;
  }

  @Override
  public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
    if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
      return (LazyOptional.of(()->inventory)).cast();
    }
    return super.getCapability(capability, facing);
  }
  
  /**
   * I suppose I can use this within my own code, like in guis and stuff, as long as I know that tile has
   * an ItemStackHandler.
   */
  public final ItemStackHandler getItemStackHandler(){
    return inventory;
  }

  /** On blocks that have an inventory, call this function during the breackBlock() method,
   *  followed by the super.breakBlock() call which completes the removal of the block and TileEntity.
   *  Derived classes can override this method if they have special rules for dropping the inventory.
   */
  public void drop_inventory(){
    if(inventory != null){
      final double x = (double)pos.getX();
      final double y = (double)pos.getY();
      final double z = (double)pos.getZ();
      int i;
      ItemStack stack;
      for(i = 0; i < inventory.getSlots(); i++){
        stack = inventory.getStackInSlot(i);
        if(ItemUtil.itemStackExists(stack)){
          InventoryHelper.spawnItemStack(world, x, y, z, stack);
        }
      }
    }
  }

}
