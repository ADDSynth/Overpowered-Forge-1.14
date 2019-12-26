package addsynth.energy.tiles.machines;

import addsynth.core.Constants;
import addsynth.core.inventory.SlotData;
import addsynth.energy.CustomEnergyStorage;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** <p>Passive Machines will be in the <code>ACTIVE</code> state, only if the {@link #can_run}
 *     variable is true, and in the <code>IDLE</code> state if the <code>can_run</code> variable
 *     is false.
 *  <p>In the {@link #test_condition()} function, when you implement it in derrived classes,
 *     check for your own conditions on when the machine should run and change the <code>can_run</code>
 *     variable in there. <code>test_condition()</code> is automatically executed when we detect
 *     the contents of either inventory has changed.
 *  <p>Machines in the <code>ACTIVE</code> state behave just like WorkMachines in that they check
 *     if their energy is full every tick and then begin to increment {@link WorkMachine#work_units}.
 *     Machines in the <code>IDLE</code> state will accept a very small amount of energy every tick.
 * @author ADDSynth
 */
public abstract class PassiveMachine extends WorkMachine {

  public enum State {
    IDLE, RUN;
    public static final State[] value = State.values();
  }

  private State state = State.IDLE;
  protected boolean can_run;
  private final int max_passive_ticks;

  public PassiveMachine(final TileEntityType type, final SlotData[] slots, final int output_slots, final CustomEnergyStorage energy, final int work_units_required){
    super(type, slots, output_slots, energy, work_units_required);
    this.max_passive_ticks = Constants.ticks_per_second;
  }

  public PassiveMachine(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy, final int work_units_required){
    super(type, input_slots, filter, output_slots, energy, work_units_required);
    this.max_passive_ticks = Constants.ticks_per_second;
  }

  public PassiveMachine(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy, final int work_units_required, final int max_passive_ticks){
    super(type, input_slots, filter, output_slots, energy, work_units_required);
    this.max_passive_ticks = max_passive_ticks;
  }

  @Override
  public void read(CompoundNBT nbt){
    super.read(nbt);
    state = State.value[nbt.getInt("State")];
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("State",state.ordinal());
    return nbt;
  }

  @Override
  public final void onLoad(){
    if(world.isRemote == false){
      test_condition();
    }
  }

  @Override
  public final void tick(){
    if(world != null){
      if(world.isRemote == false){
        if(running){
          switch(state){
          case IDLE:
            if(can_run){
              changeState(State.RUN);
            }
            break;
          case RUN:
            if(can_run){
              machine_tick();
            }
            else{
              changeState(State.IDLE);
            }
            break;
          }
        }
      }
    }
    energy.update();
  }

  @Override
  protected void machine_tick(){
    if(energy.isFull()){
      work_units += 1;
      if(work_units >= work_units_required){
        performWork();
        work_units = 0;
        energy.setEmpty();
        test_condition();
      }
      update_data();
    }
  }

  /** This function must test the input and output item slots and set the can_run variable. */
  protected abstract void test_condition();

  @Override
  public void onInventoryChanged(){
    test_condition();
  }

  private final void changeState(State state){
    if(state == State.IDLE){
      work_units = 0;
    }
    this.state = state;
    update_data();
  }

  public final State getState(){
    return state;
  }

  @Override
  public final String getStatus(){
    if(running){
      if(state == State.RUN){
        if(energy.needsEnergy()){
          return "Charging";
        }
        return "Working";
      }
      return "Idle";
    }
    return "Off";
  }

  @Override
  public final boolean needsEnergy(){
    boolean pass = false;
    if(running){
      switch(state){
      case IDLE:
        if(world.getGameTime() % max_passive_ticks == 0){
          pass = energy.needsEnergy();
        }
        break;
      case RUN:
        pass = energy.needsEnergy();
        break;
      }
    }
    return pass;
  }

}
