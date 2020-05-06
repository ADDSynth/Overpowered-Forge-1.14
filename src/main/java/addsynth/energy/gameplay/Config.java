package addsynth.energy.gameplay;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.energy.config.MachineDataConfig;
import addsynth.energy.tiles.machines.MachineType;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  public static final MachineDataConfig compressor_data = new MachineDataConfig(MachineType.ALWAYS_ON, 200, 10, 0, 0); // 2,000

  public static ForgeConfigSpec.BooleanValue electric_furnace;

  public static ForgeConfigSpec.BooleanValue         energy_storage_container;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_storage_container_capacity;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_storage_container_extract_rate;

  public static ForgeConfigSpec.BooleanValue         universal_energy_interface;
  public static ForgeConfigSpec.ConfigValue<Integer> universal_energy_interface_buffer;

  private static final int DEFAULT_ENERGY_STORAGE_CAPACITY       = 1_000_000;
  private static final int DEFAULT_ENERGY_STORAGE_MAX_EXTRACT        =   500;
  private static final int DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER = 1_000;

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public Config(final ForgeConfigSpec.Builder builder){
  
    builder.push("Compressor");
    compressor_data.build(builder);
    builder.pop();
    
    builder.push("Electric Furnace");
    electric_furnace           = builder.define("Enabled", true);
    builder.pop();
    
    builder.push("Energy Storage Block");
    energy_storage_container   = builder.define("Enabled", true);
    energy_storage_container_capacity     = builder.defineInRange("Energy Storage Container Capacity",
                                              DEFAULT_ENERGY_STORAGE_CAPACITY, 0, Integer.MAX_VALUE);
    energy_storage_container_extract_rate = builder.defineInRange("Energy Storage Container Extract Rate",
                                              DEFAULT_ENERGY_STORAGE_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Universal Energy Interface");
    universal_energy_interface = builder.define("Enabled", true);
    universal_energy_interface_buffer     = builder.defineInRange("Universal Energy Interface Buffer",
                                              DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER, 0, Integer.MAX_VALUE);
    builder.pop();
  }

}
