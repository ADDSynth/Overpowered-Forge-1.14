package addsynth.overpoweredmod.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.energy.config.MachineDataConfig;
import addsynth.energy.tiles.machines.MachineType;
import net.minecraftforge.common.ForgeConfigSpec;

public final class MachineValues {

  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_max_extract;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_shards_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> energy_crystal_shards_max_extract;
  public static ForgeConfigSpec.ConfigValue<Integer> light_block_energy;
  public static ForgeConfigSpec.ConfigValue<Integer> light_block_max_extract;

  // Standard Machines
  public static final MachineDataConfig gem_converter = new MachineDataConfig( 600,  35,   0.06 ,  60); // 21,000
  public static final MachineDataConfig inverter      = new MachineDataConfig(4000,  22.5, 0.1  , 200); // 90,000
  public static final MachineDataConfig magic_infuser = new MachineDataConfig(1200,  50,   0.075,  60); // 60,000
  public static final MachineDataConfig identifier    = new MachineDataConfig( 500,  16,   0.05 ,  10); //  8,000

  // Passive Machines
  public static final MachineDataConfig crystal_matter_generator =
    new MachineDataConfig(MachineType.PASSIVE, 16000,  31.25, 0,  600); // 500,000 energy for 1 shard every 13.3 minutes

  // Manual Activation Machines
  public static final MachineDataConfig portal =
    new MachineDataConfig(MachineType.MANUAL_ACTIVATION,  6000, 100,    0, 1200); // 5 minutes to generate

  // Always On Machines
  public static final MachineDataConfig advanced_ore_refinery =
    new MachineDataConfig(MachineType.ALWAYS_ON, 200, 25, 0.1, 0); // 5,000

  public static ForgeConfigSpec.ConfigValue<Integer> required_energy_per_laser;
  public static ForgeConfigSpec.ConfigValue<Integer> required_energy_per_laser_distance;

  public static ForgeConfigSpec.ConfigValue<Integer> fusion_energy_output_per_tick;

  //    20,000      25,000      30,000      40,000
  //   180,000     225,000     270,000     360,000
  // 1,620,000   2,025,000   2,430,000   3,240,000
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY      = 30_000;
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT = 40;
  private static final int DEFAULT_ENERGY_CRYSTAL_ENERGY             = DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY * 9;
  private static final int DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT        = 100;
  private static final int DEFAULT_LIGHT_BLOCK_ENERGY                = DEFAULT_ENERGY_CRYSTAL_ENERGY * 9;
  private static final int DEFAULT_LIGHT_BLOCK_MAX_EXTRACT           = 500;

  private static final int DEFAULT_FUSION_ENERGY_PER_TICK = 100;

  private static final int DEFAULT_ENERGY_PER_LASER_CANNON   = 5_000;
  private static final int DEFAULT_ENERGY_PER_LASER_DISTANCE =   100;

  private static final Pair<MachineValues, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(MachineValues::new);
  public static final MachineValues INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

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
    gem_converter.build(builder);
    builder.pop();
    
    builder.push("Inverter");
    inverter.build(builder);
    builder.pop();
    
    builder.push("Magic Infuser");
    magic_infuser.build(builder);
    builder.pop();
    
    builder.push("Identifier");
    identifier.build(builder);
    builder.pop();
    
    builder.push("Portal Control Panel");
    portal.build(builder);
    builder.pop();
    
    builder.push("Laser");
    required_energy_per_laser          = builder.defineInRange("Energy per Laser Cannon",
                                              DEFAULT_ENERGY_PER_LASER_CANNON,   0, Integer.MAX_VALUE);
    required_energy_per_laser_distance = builder.defineInRange("Energy per Laser Distance",
                                              DEFAULT_ENERGY_PER_LASER_DISTANCE, 0, Integer.MAX_VALUE);
    builder.pop();
    
    builder.push("Crystal Matter Generator");
    crystal_matter_generator.build(builder);
    builder.pop();
    
    builder.push("Advanced Ore Refinery");
    advanced_ore_refinery.build(builder);
    builder.pop();

    builder.push("Fusion Energy Converter");
    fusion_energy_output_per_tick = builder.defineInRange("Energy Produced per tick",
                                              DEFAULT_FUSION_ENERGY_PER_TICK, 0, Integer.MAX_VALUE);
    builder.pop();
    
  }

}
