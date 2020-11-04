package addsynth.energy.tiles.machines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.CommonInventory;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.IOutputInventory;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.InventoryUtil;
import addsynth.core.inventory.MachineInventory;
import addsynth.core.inventory.OutputInventory;
import addsynth.core.inventory.SlotData;
import addsynth.energy.config.MachineData;
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
  implements IInputInventory, IOutputInventory {

  protected final MachineInventory inventory;

  public TileAlwaysOnMachine(TileEntityType type, SlotData[] slots, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(this, slots, output_slots);
  }

  public TileAlwaysOnMachine(TileEntityType type, int input_slots, Item[] filter, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(this, input_slots, filter, output_slots);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      try{
        machine_tick();
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
        perform_work();
        inventory.clear_working_inventory();
        energy.setEmpty();
        if(test_condition()){
          inventory.begin_work();
        }
        else{
          state = MachineState.IDLE;
        }
        changed = true;
      }
      break;

    case IDLE:
      if(test_condition()){
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

  // Error 2: Track how EnergyUtil transfers Energy from the Generator, it is NOT transferring 500 Energy per tick.

  /** Refresh Job queue. */
  private final void refresh(){
  }

  /** This function must test the input and output item slots. */
  protected abstract boolean test_condition();

  /** Finishes working on the center ItemStack and increments the output. */
  protected abstract void perform_work();

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
        return InventoryUtil.getInventoryCapability(inventory.input_inventory, inventory.output_inventory, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    refresh();
    changed = true;
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
    return inventory.input_inventory;
  }

  @Override
  public OutputInventory getOutputInventory(){
    return inventory.output_inventory;
  }

  public final CommonInventory getWorkingInventory(){
    return inventory.working_inventory;
  }
  
}
