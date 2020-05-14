package addsynth.energy.config;

import addsynth.energy.tiles.machines.MachineData;
import addsynth.energy.tiles.machines.MachineType;
import net.minecraftforge.common.ForgeConfigSpec;

public final class MachineDataConfig extends MachineData {

  public ForgeConfigSpec.ConfigValue<Integer> work_time_config;
  public ForgeConfigSpec.ConfigValue<Double>  energy_usage_config;
  public ForgeConfigSpec.ConfigValue<Double>  idle_energy_config;
  public ForgeConfigSpec.ConfigValue<Integer> power_on_time_config;

  public MachineDataConfig(int default_work_time, double default_energy, double default_idle_energy, int default_power_on_time){
    super(MachineType.STANDARD, default_work_time, default_energy, default_idle_energy, default_power_on_time);
  }

  public MachineDataConfig(MachineType type, int default_work_time, double default_energy, double default_idle_energy, int default_power_on_time){
    super(type, default_work_time, default_energy, default_idle_energy, default_power_on_time);
  }

  public final void build(final ForgeConfigSpec.Builder builder){
    work_time_config     = builder.defineInRange("Work Time",               work_time,     0, Integer.MAX_VALUE);
    energy_usage_config  = builder.defineInRange("Maximum Energy Per Tick", max_energy_accepted, 0, Float.MAX_VALUE);
    if(type != MachineType.PASSIVE){
      idle_energy_config   = builder.defineInRange("Idle Energy Drain",     idle_energy,   0, Float.MAX_VALUE);
    }
    if(type != MachineType.ALWAYS_ON){
      power_on_time_config = builder.defineInRange("Power Cycle Time",      power_on_time, 0, Integer.MAX_VALUE);
    }
  }

  @Override
  public final int get_work_time(){
    return work_time_config.get();
  }
  
  @Override
  public final double get_max_receive(){
    return energy_usage_config.get();
  }
  
  @Override
  public final double get_idle_energy(){
    return idle_energy_config != null ? idle_energy_config.get() : 0;
  }
  
  @Override
  public final int get_power_time(){
    return power_on_time_config != null ? power_on_time_config.get() : 0;
  }

}
