package addsynth.energy.tiles.machines;

import addsynth.energy.config.MachineData;
import addsynth.energy.main.ICustomEnergyUser;
import addsynth.energy.main.Receiver;
import addsynth.energy.tiles.TileAbstractMachine;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** Work Machines are the most commonly used Machine types. Their behaviour is
 *  specifically defined and are well managed.
 * @author ADDSynth
 */
public abstract class TileAbstractWorkMachine extends TileAbstractMachine implements ICustomEnergyUser {

  protected boolean changed;
  protected MachineState state;

  public TileAbstractWorkMachine(final TileEntityType type, final MachineState initial_state, final MachineData data){
    super(type, new Receiver(data.total_energy_needed, data.get_max_receive()));
    this.state = initial_state;
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    state = MachineState.value[nbt.getInt("State")];
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("State", state.ordinal());
    return nbt;
  }

  protected abstract void machine_tick();

  public float getWorkTimePercentage(){
    if(state == MachineState.RUNNING){
      return energy.getEnergyPercentage();
    }
    return 0.0f;
  }

  @Override
  public final double getAvailableEnergy(){
    return 0;
  }

  public final MachineState getState(){
    return state;
  }

  public final String getStatus(){
    return state.getStatus();
  }

}
