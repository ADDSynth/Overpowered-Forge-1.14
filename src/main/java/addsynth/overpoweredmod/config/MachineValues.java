package addsynth.overpoweredmod.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class MachineValues {

  private static final Pair<MachineValues, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(MachineValues::new);
  public static final MachineValues INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_max_extract;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_shards_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_shards_max_extract;
  public static ForgeConfigSpec.ConfigValue<Integer> light_block_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> light_block_max_extract;

  public static ForgeConfigSpec.ConfigValue<Integer> fusion_energy_output_per_tick;
  
  public static ForgeConfigSpec.ConfigValue<Integer> gem_converter_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> inverter_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> magic_infuser_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> identifier_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> portal_control_panel_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> required_energy_per_laser;
  public static ForgeConfigSpec.ConfigValue<Integer> required_energy_per_laser_distance;
  public static ForgeConfigSpec.ConfigValue<Integer> crystal_matter_generator_required_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> advanced_ore_refinery_required_energy;

  public static ForgeConfigSpec.ConfigValue<Integer> gem_converter_work_time;
  public static ForgeConfigSpec.ConfigValue<Integer> inverter_work_time;
  public static ForgeConfigSpec.ConfigValue<Integer> magic_infuser_work_time;
  public static ForgeConfigSpec.ConfigValue<Integer> identifier_work_time;
  public static ForgeConfigSpec.ConfigValue<Integer> crystal_matter_generator_work_time;
  public static ForgeConfigSpec.ConfigValue<Integer> advanced_ore_refinery_work_time;

  //    20,000      25,000      30,000      40,000
  //   180,000     225,000     270,000     360,000
  // 1,620,000   2,025,000   2,430,000   3,240,000
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY      = 30_000;
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT = 40;
  private static final int DEFAULT_ENERGY_CRYSTAL_ENERGY             = DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY * 9;
  private static final int DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT        = 100;
  private static final int DEFAULT_LIGHT_BLOCK_ENERGY                = DEFAULT_ENERGY_CRYSTAL_ENERGY * 9;
  private static final int DEFAULT_LIGHT_BLOCK_MAX_EXTRACT           = 500;

  private static final int DEFAULT_FUSION_ENERGY_PER_TICK            =   100;

  private static final int DEFAULT_GEM_CONVERTER_REQUIRED_ENERGY  =  20_000;
  private static final int DEFAULT_INVERTER_REQUIRED_ENERGY       =  90_000;
  private static final int DEFAULT_MAGIC_INFUSER_REQUIRED_ENERGY  =  60_000;
  private static final int DEFAULT_IDENTIFIER_REQUIRED_ENERGY     =   8_000;
  private static final int DEFAULT_PORTAL_REQUIRED_ENERGY         = 600_000; // for 1 portal
  private static final int DEFAULT_ENERGY_PER_LASER_CANNON        =   5_000;
  private static final int DEFAULT_ENERGY_PER_LASER_DISTANCE      =     100;
  private static final int DEFAULT_GEM_REPLICATOR_REQUIRED_ENERGY = 500_000; // for 1 shard
  private static final int DEFAULT_ORE_REFINERY_REQUIRED_ENERGY   =  10_000;

  private static final int DEFAULT_GEM_CONVERTER_WORK_TIME    =  600;
  private static final int DEFAULT_INVERTER_WORK_TIME         = 4000;
  private static final int DEFAULT_MAGIC_INFUSER_WORK_TIME    =  300;
  private static final int DEFAULT_IDENTIFIER_WORK_TIME       =  100;
  private static final int DEFAULT_CRYSTAL_MATTER_GENERATOR_WORK_TIME = 18000;
  private static final int DEFAULT_ORE_REFINERY_WORK_TIME     =  200;

  public MachineValues(final ForgeConfigSpec.Builder builder){

    builder.push("Generator");
    energy_crystal_shards_energy      = builder.defineInRange("Energy Crystal Shards Produced Energy",
                                          DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY, 0, Integer.MAX_VALUE);
    energy_crystal_shards_max_extract = builder.defineInRange("Energy Crystal Shards Extract Rate",
                                          DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    energy_crystal_energy             = builder.defineInRange("Energy Crystal Produced Energy",
                                          DEFAULT_ENERGY_CRYSTAL_ENERGY, 0, Integer.MAX_VALUE);
    energy_crystal_max_extract        = builder.defineInRange("Energy Crystal Extract Rate",
                                          DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    light_block_energy                = builder.defineInRange("Light Block Produced Energy",
                                          DEFAULT_LIGHT_BLOCK_ENERGY, 0, Integer.MAX_VALUE);
    light_block_max_extract           = builder.defineInRange("Light Block Extract Rate",
                                          DEFAULT_LIGHT_BLOCK_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Gem Converter");
    gem_converter_required_energy = builder.defineInRange("Required Energy", DEFAULT_GEM_CONVERTER_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    gem_converter_work_time       = builder.defineInRange("Work Time",       DEFAULT_GEM_CONVERTER_WORK_TIME,       0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Inverter");
    inverter_required_energy = builder.defineInRange("Required Energy", DEFAULT_INVERTER_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    inverter_work_time       = builder.defineInRange("Work Time",       DEFAULT_INVERTER_WORK_TIME,       0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Magic Infuser");
    magic_infuser_required_energy = builder.defineInRange("Required Energy", DEFAULT_MAGIC_INFUSER_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    magic_infuser_work_time       = builder.defineInRange("Work Time",       DEFAULT_MAGIC_INFUSER_WORK_TIME,       0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Identifier");
    identifier_required_energy = builder.defineInRange("Required Energy", DEFAULT_IDENTIFIER_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    identifier_work_time       = builder.defineInRange("Work Time",       DEFAULT_IDENTIFIER_WORK_TIME,       0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Portal Control Panel");
    portal_control_panel_required_energy = builder.defineInRange("Required Energy", DEFAULT_PORTAL_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Laser");
    required_energy_per_laser          = builder.defineInRange("Energy per Laser Cannon",
                                              DEFAULT_ENERGY_PER_LASER_CANNON,   0, Integer.MAX_VALUE);
    required_energy_per_laser_distance = builder.defineInRange("Energy per Laser Distance",
                                              DEFAULT_ENERGY_PER_LASER_DISTANCE, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Crystal Matter Generator");
    crystal_matter_generator_required_energy = builder.defineInRange("Required Energy",
                                              DEFAULT_GEM_REPLICATOR_REQUIRED_ENERGY,     0, Integer.MAX_VALUE);
    crystal_matter_generator_work_time       = builder.defineInRange("Work Time",
                                              DEFAULT_CRYSTAL_MATTER_GENERATOR_WORK_TIME, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Advanced Ore Refinery");
    advanced_ore_refinery_required_energy = builder.defineInRange("Required Energy",
                                              DEFAULT_ORE_REFINERY_REQUIRED_ENERGY, 0, Integer.MAX_VALUE);
    advanced_ore_refinery_work_time       = builder.defineInRange("Work Time",
                                              DEFAULT_ORE_REFINERY_WORK_TIME,       0, Integer.MAX_VALUE);
    builder.pop();

    builder.push("Fusion Energy Converter");
    fusion_energy_output_per_tick = builder.defineInRange("Energy Produced per tick",
                                              DEFAULT_FUSION_ENERGY_PER_TICK, 0, Integer.MAX_VALUE);
    builder.pop();
    
  }

}
