package addsynth.energy.tiles.machines;

import addsynth.energy.config.MachineData;
import net.minecraft.tileentity.TileEntityType;

/** Manual Machines can be switched off, but only accept energy when they're on.
 *  They do not perform any action automatically and you must check for and empty
 *  energy yourself.
 * @author ADDSynth
 */
public abstract class TileManualMachine extends TileSwitchableMachine {

  public TileManualMachine(final TileEntityType type, final MachineData data){
    super(type, MachineState.RUNNING, data);
  }

  public TileManualMachine(final TileEntityType type, final MachineData data, final boolean initial_power_state){
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
      if(power_switch == false){
        turn_off();
      }
    }
  }

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

}
