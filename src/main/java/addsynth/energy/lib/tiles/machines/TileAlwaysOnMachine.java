package addsynth.energy.lib.tiles.machines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.*;
import addsynth.energy.lib.config.MachineData;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** Machines that are always running cannot be turned off. They switch to an
 *  Idle state when they can't do work. These machines don't have idle energy. */
public abstract class TileAlwaysOnMachine extends TileAbstractWorkMachine
  implements IInputInventory, IOutputInventory, IMachineInventory {

  protected final MachineInventory inventory;

  public TileAlwaysOnMachine(TileEntityType type, SlotData[] slots, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(slots, output_slots);
  }

  public TileAlwaysOnMachine(TileEntityType type, int input_slots, Item[] filter, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(input_slots, filter, output_slots);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      try{
        machine_tick();
        if(inventory.tick()){
          changed = true;
        }
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
  protected void machine_tick(){
    switch(state){
    case RUNNING:
      if(energy.isFull()){
        inventory.finish_work();
        energy.setEmpty();
        if(inventory.can_work()){
          inventory.begin_work();
        }
        else{
          state = MachineState.IDLE;
        }
        changed = true;
      }
      break;

    case IDLE:
      if(inventory.can_work()){
        state = MachineState.RUNNING;
        inventory.begin_work();
        changed = true;
      }
      break;
    
    default:
      state = MachineState.IDLE;
      changed = true;
    }
  }

  // Even though this is meant to be somewhat of a library or API, there's currently no way
  // to specify non-default behiavour for this type of machine. See TileStandardWorkMachine.

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  @Override
  public final int getJobs(){
    return inventory.getJobs();
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    inventory.loadFromNBT(nbt);
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    inventory.saveToNBT(nbt);
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory.getInputInventory(), inventory.getOutputInventory(), side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

  @Override
  public void drop_inventory(){
    inventory.drop(pos, world);
  }

  @Override
  public InputInventory getInputInventory(){
    return inventory.getInputInventory();
  }

  @Override
  public OutputInventory getOutputInventory(){
    return inventory.getOutputInventory();
  }

  @Override
  public final CommonInventory getWorkingInventory(){
    return inventory.getWorkingInventory();
  }
  
}
