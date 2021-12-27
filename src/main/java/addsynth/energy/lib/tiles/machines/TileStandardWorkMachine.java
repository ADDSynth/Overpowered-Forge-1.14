package addsynth.energy.lib.tiles.machines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.*;
import addsynth.core.inventory.machine.IMachineInventory;
import addsynth.core.inventory.machine.MachineInventory;
import addsynth.energy.lib.config.MachineData;
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
  implements IInputInventory, IOutputInventory, IMachineInventory {

  protected final MachineInventory inventory;
  private final double idle_energy;

  public TileStandardWorkMachine(TileEntityType type, SlotData[] slots, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(slots, output_slots);
    this.idle_energy = data.get_idle_energy();
  }

  public TileStandardWorkMachine(TileEntityType type, int input_slots, Item[] filter, int output_slots, MachineData data){
    super(type, MachineState.IDLE, data);
    this.inventory = new MachineInventory(input_slots, filter, output_slots);
    this.idle_energy = data.get_idle_energy();
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
        if(can_work()){
          state = MachineState.RUNNING;
          begin_work();
          changed = true;
        }
      }
      break;
      
    case RUNNING:
      if(energy.isFull()){
        perform_work();
        energy.setEmpty();
        if(power_switch == false){
          turn_off();
        }
        else{
          if(can_work()){
            begin_work();
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

  /** Called multiple times a tick. Returns whether the machine can perform work.
   *  Override to specify non-default behaviour.
   */
  protected boolean can_work(){
    return inventory.can_work();
  }

  /** This is called to start a job.
   *  Override to specify non-default behaviour.
   */
  protected void begin_work(){
    inventory.begin_work();
  }

  /** Finishes working on the center ItemStack and increments the output.
   *  Override to specify non-default behaviour.
   */
  protected void perform_work(){
    inventory.finish_work();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public int getJobs(){
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
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
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
    if(state == MachineState.IDLE){
    }
    return 0;
  }

  @Override
  public final void drop_inventory(){
    inventory.drop(pos, world);
  }

  @Override
  public final InputInventory getInputInventory(){
    return inventory.getInputInventory();
  }
  
  @Override
  public final OutputInventory getOutputInventory(){
    return inventory.getOutputInventory();
  }

  @Override
  public final CommonInventory getWorkingInventory(){
    return inventory.getWorkingInventory();
  }
  
}
