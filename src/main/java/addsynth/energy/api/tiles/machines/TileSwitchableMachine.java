package addsynth.energy.api.tiles.machines;

import addsynth.energy.api.config.MachineData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** Machines with a Power Switch can be switched off to conserve energy.
 *  Most Work Machines derive from this class.
 * @author ADDSynth
 */
public abstract class TileSwitchableMachine extends TileAbstractWorkMachine implements ISwitchableMachine {

  protected boolean power_switch;
  protected int power_time;
  protected final int power_on_time;
  protected final int power_off_time;

  public TileSwitchableMachine(TileEntityType type, MachineState initial_state, MachineData data){
    this(type, initial_state, data, true);
  }

  public TileSwitchableMachine(TileEntityType type, MachineState initial_state, MachineData data, boolean initial_power_state){
    super(type, initial_state, data);
    power_on_time  = data.get_power_time();
    power_off_time = data.get_power_time();
    power_switch = initial_power_state;
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    power_switch = nbt.getBoolean("Power Switch");
    power_time   = nbt.getInt("Power Time");
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putBoolean("Power Switch", power_switch);
    nbt.putInt("Power Time", power_time);
    return nbt;
  }

  protected final void powering_off(){
    power_time += 1;
    if(power_time >= power_off_time){
      state = MachineState.OFF;
      power_time = 0;
    }
    changed = true;
  }

  protected final void turn_off(){
    if(power_off_time > 0){
      state = MachineState.POWERING_OFF;
    }
    else{
      state = MachineState.OFF;
    }
    changed = true;
  }

  @Override
  public final float getWorkTimePercentage(){
    if(state == MachineState.POWERING_ON){
      if(power_on_time > 0){
        return (float)power_time / power_on_time;
      }
    }
    if(state == MachineState.POWERING_OFF){
      if(power_off_time > 0){
        return (float)power_time / power_off_time;
      }
    }
    return super.getWorkTimePercentage();
  }

  @Override
  public void togglePowerSwitch(){
    if(onServerSide()){
      power_switch = !power_switch;
      changed = true;
    }
  }

  @Override
  public final boolean get_switch_state(){
    return power_switch;
  }

}
