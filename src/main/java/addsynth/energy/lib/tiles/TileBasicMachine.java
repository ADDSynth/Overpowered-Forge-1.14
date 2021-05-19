package addsynth.energy.lib.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.InventoryUtil;
import addsynth.core.inventory.SlotData;
import addsynth.energy.lib.main.Receiver;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is for TileEntities that need energy to do work, and also need an
 *  Input Inventory, but only for Input-only storage. If you need to do
 *  work on the items, use one of the other Machine classes.
 * @author ADDSynth
 */
public abstract class TileBasicMachine extends TileAbstractMachine implements IInputInventory {

  private boolean changed;
  protected final InputInventory inventory;

  public TileBasicMachine(TileEntityType type, SlotData[] slots, Receiver energy){
    super(type, energy);
    this.inventory = InputInventory.create(this, slots);
  }

  public TileBasicMachine(TileEntityType type, int input_slots, Item[] filter, Receiver energy){
    super(type, energy);
    this.inventory = InputInventory.create(this, input_slots, filter);
  }

  @Override
  public void tick(){
    if(onServerSide()){
      try{
        if(energy.tick()){
          changed = true;
        }
        if(changed){
          update_data();
          changed = false;
        }
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(inventory != null){ inventory.load(nbt);}
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(inventory != null){ inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory, null, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(pos, world, inventory);
  }

  @Override
  public InputInventory getInputInventory(){
    return inventory;
  }

}
