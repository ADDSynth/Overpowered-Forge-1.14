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

/** The Standard Work Machine, can be turned off, has an idle state, and when
 *  it can do work it transfers an item from the input inventory to the working
 *  inventory and switches to the Running state. This machine has idle energy.
 * @author ADDSynth
 */
public abstract class TileStandardWorkMachine extends TileSwitchableMachine
  implements IInputInventory, IOutputInventory {

  protected final MachineInventory inventory;
  private final double idle_energy;

  public TileStandardWorkMachine(TileEntityType type, SlotData[] slots, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(this, slots, output_slots);
    this.idle_energy = data.get_idle_energy();
  }

  public TileStandardWorkMachine(TileEntityType type, int input_slots, Item[] filter, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(this, input_slots, filter, output_slots);
    this.idle_energy = data.get_idle_energy();
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
    case OFF:
      if(power_switch){
        if(power_on_time > 0){
          state = MachineState.POWERING_ON;
        }
        else{
          state = MachineState.IDLE;
        }
        changed = true;
      }
      break;

    case POWERING_ON:
      power_time += 1;
      if(power_time >= power_on_time){
        state = MachineState.IDLE;
        power_time = 0;
      }
      changed = true;
      break;

    case POWERING_OFF:
      powering_off();
      break;

    case IDLE:
      if(power_switch == false){
        turn_off();
      }
      else{
        if(test_condition()){
          state = MachineState.RUNNING;
          inventory.begin_work();
          onJobStart();
          changed = true;
        }
      }
      break;
      
    case RUNNING:
      if(energy.isFull()){
        perform_work();
        inventory.clear_working_inventory();
        energy.setEmpty();
        if(power_switch == false){
          turn_off();
        }
        else{
          if(test_condition()){
            inventory.begin_work();
            onJobStart();
          }
          else{
            state = MachineState.IDLE;
          }
        }
        changed = true;
      }
      else{
        if(power_switch == false){
          turn_off();
        }
      }

      break;
    }
  }

  /** Refresh Job queue. */
  private final void refresh(){
  }

  /** This function must test the input and output item slots. */
  protected abstract boolean test_condition();

  protected void onJobStart(){}

  /** Finishes working on the center ItemStack and increments the output. */
  protected abstract void perform_work();

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(inventory != null){ inventory.loadFromNBT(nbt);}
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(inventory != null){ inventory.saveToNBT(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory.input_inventory, inventory.output_inventory, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public final void onInventoryChanged(){
    refresh();
    changed = true;
  }

  @Override
  public double getNeededEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    if(state == MachineState.IDLE){
    }
    return 0;
  }

  @Override
  public void receiveEnergy(double add_energy){
    if(state == MachineState.RUNNING){
      energy.receiveEnergy(add_energy);
    }
  }

  @Override
  public final void drop_inventory(){
    inventory.drop(pos, world);
  }

  @Override
  public final InputInventory getInputInventory(){
    return inventory.input_inventory;
  }
  
  @Override
  public final OutputInventory getOutputInventory(){
    return inventory.output_inventory;
  }

  public final CommonInventory getWorkingInventory(){
    return inventory.working_inventory;
  }
  
}
