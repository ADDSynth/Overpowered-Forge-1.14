package addsynth.energy.lib.tiles.machines;

import addsynth.energy.lib.config.MachineData;
import net.minecraft.tileentity.TileEntityType;

/** Passive Machines have no idle state. They are either OFF or RUNNING.
 *  Passive Machines do not have idle energy. */
public abstract class TilePassiveMachine extends TileSwitchableMachine {

  public TilePassiveMachine(final TileEntityType type, final MachineData data){
    super(type, MachineState.RUNNING, data);
  }

  public TilePassiveMachine(final TileEntityType type, final MachineData data, final boolean initial_power_state){
    super(type, initial_power_state ? MachineState.RUNNING : MachineState.OFF, data, initial_power_state);
  }

  @Override
  public void tick(){
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
          state = MachineState.RUNNING;
        }
        changed = true;
      }
      break;

    case POWERING_ON:
      power_time += 1;
      if(power_time >= power_on_time){
        state = MachineState.RUNNING;
        power_time = 0;
      }
      changed = true;
      break;

    case POWERING_OFF:
      powering_off();
      break;
    
    default:
      if(energy.isFull()){
        perform_work();
        energy.setEmpty();
        changed = true;
      }
      if(power_switch == false){
        turn_off();
      }
      break;
    }
  }

  protected abstract void perform_work();

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

}
