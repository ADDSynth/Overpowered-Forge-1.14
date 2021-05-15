package addsynth.energy.api.config;

import javax.annotation.Nonnegative;
import addsynth.core.Constants;

public class MachineData {

  public static final float DEFAULT_IDLE_ENERGY = 1.0f / Constants.ticks_per_second;

  public final MachineType type;

  /** Number of work units needed to perform 1 operation. */
  @Nonnegative
  protected final int work_time;

  /** Number of Ticks the machine spends 'powering on' when the player turns the machine off. */
  @Nonnegative
  protected final int power_on_time;

  /** Amount of Energy needed to increment the work time by 1. */
  @Nonnegative
  protected final double max_energy_accepted;

  /** Amount of Energy that is used every tick when the machine is not doing work. */
  @Nonnegative
  protected final double idle_energy;

  /** Total amount energy needed to perform 1 operation. */
  public final double total_energy_needed;

  public MachineData(MachineType type, int work_time, double energy_needed){
    this(type, work_time, energy_needed, DEFAULT_IDLE_ENERGY, 0);
  }

  public MachineData(MachineType type, int work_time, double energy_needed, double idle_energy){
    this(type, work_time, energy_needed, idle_energy, 0);
  }

  public MachineData(MachineType type, int work_time, double energy_needed, double idle_energy, int power_on_time){
    this.type                = type;
    this.work_time           = work_time;
    this.power_on_time       = power_on_time;
    this.max_energy_accepted = energy_needed;
    this.idle_energy         = idle_energy < 0 ? DEFAULT_IDLE_ENERGY : idle_energy;
    this.total_energy_needed = energy_needed * work_time;
  }

  public int get_work_time(){
    return work_time;
  }
  
  public double get_max_receive(){
    return max_energy_accepted;
  }

  public double get_idle_energy(){
    return idle_energy;
  }
  
  public int get_power_time(){
    return power_on_time;
  }

}
