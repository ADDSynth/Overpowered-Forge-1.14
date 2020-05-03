package addsynth.energy.tiles.machines;

import addsynth.core.inventory.SlotData;
import addsynth.core.util.StringUtil;
import addsynth.energy.Energy;
import addsynth.energy.tiles.TileEnergyReceiver;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileWorkMachine extends TileEnergyReceiver implements ITickableTileEntity {

  protected final MachineType type;
  protected MachineState state;

  protected boolean power_switch;
  protected boolean can_run;

  protected final double idle_energy;

  protected int power_time;
  protected final int max_power_time;

  public TileWorkMachine(final TileEntityType type, final SlotData[] slots, final int output_slots, final MachineData data){
    super(type, slots, output_slots, new Energy(data.total_energy_needed, data.get_max_receive()));
    this.type = data.type;
    if(data.type == MachineType.ALWAYS_ON){
      state = MachineState.IDLE;
      power_switch = true;
    }
    idle_energy = data.get_idle_energy();
    max_power_time = data.get_power_time();
  }

  public TileWorkMachine(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final MachineData data){
    super(type, input_slots, filter, output_slots,  new Energy(data.total_energy_needed, data.get_max_receive()));
    this.type = data.type;
    if(data.type == MachineType.ALWAYS_ON){
      state = MachineState.IDLE;
      power_switch = true;
    }
    idle_energy = data.get_idle_energy();
    max_power_time = data.get_power_time();
  }

// ============================================================================

  @Override
  public void onLoad(){
    if(world.isRemote == false){
      test_condition();
    }
  }

  @Override
  public void tick(){
    if(world.isRemote){
      switch(state){
      case OFF:
        if(power_switch){
          turn_on();
        }
        break;
      case POWERING_ON:  powering_on();  break;
      case POWERING_OFF: powering_off(); break;
      case IDLE:         idle();         break;
      case RUNNING:      do_work();      break;
      }
    }
    super.tick();
  }

  /** Prepare to Turn on. Switches machine to Powering On if it has power time. */
  private void turn_on(){
    if(max_power_time > 0){
      state = MachineState.POWERING_ON;
    }
    else{
      switch_machine_state();
    }
  }

  private void turn_off(){
    if(max_power_time > 0){
      state = MachineState.POWERING_OFF;
    }
    else{
      state = MachineState.OFF;
    }
  }

  private void powering_on(){
    power_time += 1;
    if(power_time >= max_power_time){
      switch_machine_state();
      power_time = 0;
    }
  }

  private void powering_off(){
    power_time += 1;
    if(power_time >= max_power_time){
      state = MachineState.OFF;
      power_time = 0;
    }
  }

  /** When machine turns ON for real. Switches to either IDLE or RUNNING depending on Machine type. */
  private void switch_machine_state(){
    if(type == MachineType.PASSIVE){
      state = MachineState.RUNNING;
    }
    else{
      state = MachineState.IDLE;
    }
  }

  private final boolean check_power_state(){
    return power_switch == false && type != MachineType.ALWAYS_ON;
  }

  private void idle(){
    if(check_power_state()){
      turn_off();
    }
    else{
      if(can_run){
        begin_work();
        state = MachineState.RUNNING;
      }
    }
  }

  protected void do_work(){
    if(energy.isFull()){
      perform_work();
      energy.setEmpty();
      if(check_power_state()){ // always on, can't be turned off
        turn_off();
      }
      else{
        if(type != MachineType.PASSIVE){ // passive machines never enter Idle state
          test_condition();
          if(can_run == false){
            state = MachineState.IDLE;
          }
        }
      }
    }
  }

// ============================================================================

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    state        = MachineState.value[nbt.getInt("State")];
    power_switch = nbt.getBoolean("Power Switch");
    power_time   = nbt.getInt("Power Time");
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("State",state.ordinal());
    nbt.putBoolean("Power Switch", power_switch);
    nbt.putInt("Power Time", power_time);
    return nbt;
  }

// ============================================================================

  @Override
  public void onInventoryChanged(){
    test_condition();
  }

  /** This function must test the input and output item slots and set the can_run variable. */
  protected abstract void test_condition();

  /** Decrements input by 1, and transfers it to the center slot to begin working on it. */
  protected void begin_work(){
  }

  /** Finishes working on the center ItemStack and increments the output. */
  protected void perform_work(){
  }

  public double getNeededEnergy(){
    if(energy != null){
      if(state == MachineState.POWERING_OFF || state == MachineState.IDLE){
        return idle_energy;
      }
      if(state == MachineState.RUNNING){
        return energy.getRequestedEnergy();
      }
      return 0;
    }
    return 0;
  }

  public void toggleRun(){
    power_switch = !power_switch;
    update_data();
  }

  @SuppressWarnings("incomplete-switch")
  public final float getWorkTimePercentage(){
    float time = 0.0f;
    switch(state){
    case POWERING_ON: case POWERING_OFF:
      if(max_power_time > 0){
        time = (float)power_time / max_power_time;
      }
      break;
    case RUNNING:
      time = energy.getEnergyPercentage();
      break;
    }
    return time;
  }

  public final String getTimeLeft(){
    final double rate = energy.getDifference();
    return StringUtil.print_time(energy.getCapacity(), rate);
  }

  public final String getTotalTimeLeft(){
    final double rate = energy.getDifference();
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty() == false){
      return StringUtil.print_time(stack.getCount() * energy.getCapacity(), rate);
    }
    return StringUtil.print_time(energy.getCapacity(), rate);
  }

// ============================================================================

  public final boolean get_switch_state(){
    return power_switch;
  }

  @Deprecated
  public final boolean isOn(){
    return state != MachineState.OFF && state != MachineState.POWERING_ON;
  }

  public final String getStatus(){
    return state.getStatus();
  }

}
