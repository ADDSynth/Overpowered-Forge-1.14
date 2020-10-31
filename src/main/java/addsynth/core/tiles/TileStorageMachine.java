package addsynth.core.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.InventoryUtil;
import addsynth.core.inventory.SlotData;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is a TileEntity that only has an Input Inventory, thus it also utilizes
 *  an Item filter and prevents machines from taking items out.
 * @author ADDSynth
 */
public abstract class TileStorageMachine extends TileBase implements IInputInventory {

  protected final InputInventory input_inventory;

  public TileStorageMachine(final TileEntityType type, final SlotData[] slots){
    super(type);
    this.input_inventory = InputInventory.create(this, slots);
  }

  public TileStorageMachine(final TileEntityType type, final int input_slots, final Item[] filter){
    super(type);
    this.input_inventory = InputInventory.create(this, input_slots, filter);
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(input_inventory != null){input_inventory.load(nbt);}
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt){
    super.write(nbt);
    if(input_inventory != null){input_inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(input_inventory, null, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    update_data();
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(pos, world, input_inventory);
  }

  @Override
  public final InputInventory getInputInventory(){
    return input_inventory;
  }

}
