package addsynth.overpoweredmod.config;

import java.io.File;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public final class ValuesConfig extends ModConfig {

  public static ValuesConfig instance;

  private static final String GENERATOR = "Generator Energy Values";
  private static final String ENERGY_MACHINES = "Other Energy Machines";
  private static final String MACHINE_REQUIRED_ENERGY = "Machine Required Energy";
  private static final String MACHINE_WORK_TIME = "Machine Work Time";
  private static final String OTHER = "Misc";

  //    20,000      25,000      30,000      40,000
  //   180,000     225,000     270,000     360,000
  // 1,620,000   2,025,000   2,430,000   3,240,000
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY      = 30_000;
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT = 40;
  private static final int DEFAULT_ENERGY_CRYSTAL_ENERGY             = DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY * 9;
  private static final int DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT        = 100;
  private static final int DEFAULT_LIGHT_BLOCK_ENERGY                = DEFAULT_ENERGY_CRYSTAL_ENERGY * 9;
  private static final int DEFAULT_LIGHT_BLOCK_MAX_EXTRACT           = 500;

  private static final int DEFAULT_ENERGY_STORAGE_CAPACITY       = 1_000_000;
  private static final int DEFAULT_ENERGY_STORAGE_MAX_EXTRACT        =   500;
  private static final int DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER = 1_000;
  private static final int DEFAULT_FUSION_ENERGY_PER_TICK            =   100;

  private static final int DEFAULT_COMPRESSOR_REQUIRED_ENERGY     =   4_000;
  private static final int DEFAULT_GEM_CONVERTER_REQUIRED_ENERGY  =  20_000;
  private static final int DEFAULT_INVERTER_REQUIRED_ENERGY       =  90_000;
  private static final int DEFAULT_MAGIC_INFUSER_REQUIRED_ENERGY  =  60_000;
  private static final int DEFAULT_IDENTIFIER_REQUIRED_ENERGY     =   8_000;
  private static final int DEFAULT_PORTAL_REQUIRED_ENERGY         = 600_000; // for 1 portal
  private static final int DEFAULT_ENERGY_PER_LASER_CANNON        =   5_000;
  private static final int DEFAULT_ENERGY_PER_LASER_DISTANCE      =     100;
  private static final int DEFAULT_GEM_REPLICATOR_REQUIRED_ENERGY = 500_000; // for 1 shard
  private static final int DEFAULT_ORE_REFINERY_REQUIRED_ENERGY   =  10_000;
  private static final int DEFAULT_ELECTRIC_FURNACE_REQUIRED_ENERGY = 1_000;

  private static final int DEFAULT_COMPRESSOR_WORK_TIME       =  200;
  private static final int DEFAULT_GEM_CONVERTER_WORK_TIME    =  600;
  private static final int DEFAULT_INVERTER_WORK_TIME         = 4000;
  private static final int DEFAULT_MAGIC_INFUSER_WORK_TIME    =  300;
  private static final int DEFAULT_IDENTIFIER_WORK_TIME       =  100;
  private static final int DEFAULT_CRYSTAL_MATTER_GENERATOR_WORK_TIME = 18000;
  private static final int DEFAULT_ORE_REFINERY_WORK_TIME     =  200;
  private static final int DEFAULT_ELECTRIC_FURNACE_WORK_TIME =  200;

  private static final float DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE = 0.002f; // 1 / 500

  public ValuesConfig(final File file){
    super(file, true);
    load_values();
  }

  public static final void initialize(final File file){
    Debug.log_setup_info("Begin initializing Values Config...");
    instance = new ValuesConfig(file);
    Debug.log_setup_info("Finished initializing Values Config.");
  }

  private final void load_values(){

    // Generator
    Values.energy_crystal_shards_energy      = unsigned(GENERATOR, "Energy Crystal Shards Produced Energy", DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY);
    Values.energy_crystal_shards_max_extract = unsigned(GENERATOR, "Energy Crystal Shards Extract Rate",    DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT);
    Values.energy_crystal_energy             = unsigned(GENERATOR, "Energy Crystal Produced Energy",        DEFAULT_ENERGY_CRYSTAL_ENERGY);
    Values.energy_crystal_max_extract        = unsigned(GENERATOR, "Energy Crystal Extract Rate",           DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT);
    Values.light_block_energy                = unsigned(GENERATOR, "Light Block Produced Energy",           DEFAULT_LIGHT_BLOCK_ENERGY);
    Values.light_block_max_extract           = unsigned(GENERATOR, "Light Block Extract Rate",              DEFAULT_LIGHT_BLOCK_MAX_EXTRACT);

    // Other Energy Values
    Values.energy_storage_container_capacity     = unsigned(ENERGY_MACHINES, "Energy Storage Container Capacity",     DEFAULT_ENERGY_STORAGE_CAPACITY);
    Values.energy_storage_container_extract_rate = unsigned(ENERGY_MACHINES, "Energy Storage Container Extract Rate", DEFAULT_ENERGY_STORAGE_MAX_EXTRACT);
    Values.universal_energy_interface_buffer     = unsigned(ENERGY_MACHINES, "Universal Energy Interface Capacity",   DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER);
    Values.fusion_energy_output_per_tick         = unsigned(ENERGY_MACHINES, "Fusion Energy Production Rate",         DEFAULT_FUSION_ENERGY_PER_TICK);

    // Machine Energy
    Values.compressor_required_energy            = unsigned(MACHINE_REQUIRED_ENERGY, "Compressor",              DEFAULT_COMPRESSOR_REQUIRED_ENERGY);
    Values.gem_converter_required_energy         = unsigned(MACHINE_REQUIRED_ENERGY, "Gem Converter",           DEFAULT_GEM_CONVERTER_REQUIRED_ENERGY);
    Values.inverter_required_energy              = unsigned(MACHINE_REQUIRED_ENERGY, "Inverter",                DEFAULT_INVERTER_REQUIRED_ENERGY);
    Values.magic_infuser_required_energy         = unsigned(MACHINE_REQUIRED_ENERGY, "Magic Infuser",           DEFAULT_MAGIC_INFUSER_REQUIRED_ENERGY);
    Values.identifier_required_energy            = unsigned(MACHINE_REQUIRED_ENERGY, "Identifier",              DEFAULT_IDENTIFIER_REQUIRED_ENERGY);
    Values.portal_control_panel_required_energy  = unsigned(MACHINE_REQUIRED_ENERGY, "Portal Control Panel",    DEFAULT_PORTAL_REQUIRED_ENERGY);
    Values.required_energy_per_laser             = unsigned(MACHINE_REQUIRED_ENERGY, "Energy per Laser Cannon", DEFAULT_ENERGY_PER_LASER_CANNON);
    Values.required_energy_per_laser_distance    = unsigned(MACHINE_REQUIRED_ENERGY, "Energy per Laser Distance", DEFAULT_ENERGY_PER_LASER_DISTANCE);
    Values.crystal_matter_generator_required_energy = unsigned(MACHINE_REQUIRED_ENERGY, "Crystal Matter Generator", DEFAULT_GEM_REPLICATOR_REQUIRED_ENERGY);
    Values.advanced_ore_refinery_required_energy = unsigned(MACHINE_REQUIRED_ENERGY, "Advanced Ore Refinery",   DEFAULT_ORE_REFINERY_REQUIRED_ENERGY);
    Values.electric_furnace_required_energy      = unsigned(MACHINE_REQUIRED_ENERGY, "Electric Furnace",        DEFAULT_ELECTRIC_FURNACE_REQUIRED_ENERGY);

    // Machine Work Time
    Values.compressor_work_time    = unsigned(MACHINE_WORK_TIME, "Compressor",    DEFAULT_COMPRESSOR_WORK_TIME);
    Values.gem_converter_work_time = unsigned(MACHINE_WORK_TIME, "Gem Converter", DEFAULT_GEM_CONVERTER_WORK_TIME);
    Values.inverter_work_time      = unsigned(MACHINE_WORK_TIME, "Inverter",      DEFAULT_INVERTER_WORK_TIME);
    Values.magic_infuser_work_time = unsigned(MACHINE_WORK_TIME, "Magic Infuser", DEFAULT_MAGIC_INFUSER_WORK_TIME);
    Values.identifier_work_time    = unsigned(MACHINE_WORK_TIME, "Identifier",    DEFAULT_IDENTIFIER_WORK_TIME);
    Values.crystal_matter_generator_work_time = unsigned(MACHINE_WORK_TIME, "Crystal Matter Generator", DEFAULT_CRYSTAL_MATTER_GENERATOR_WORK_TIME);
    Values.advanced_ore_refinery_work_time    = unsigned(MACHINE_WORK_TIME, "Advanced Ore Refinery",    DEFAULT_ORE_REFINERY_WORK_TIME);
    Values.electric_furnace_work_time         = unsigned(MACHINE_WORK_TIME, "Electric Furnace",         DEFAULT_ELECTRIC_FURNACE_WORK_TIME);

    // float value, chance that ANYTHING will drop
    // all 4 ring weight values
    // all 5 armor weight values
  
    // Spawn chance of Unknown Trees in the weird dimension.
    Values.unknown_dimension_tree_spawn_chance = getFloat("Weird Tree Spawn Chance", OTHER, DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE, Float.MIN_NORMAL, 1.0f,
      "This float value determines the chance a weird tree will spawn for each chunk\nin the Unknown Dimension.");

    if(this.hasChanged()){
      save();
    }
  }

  private final int unsigned(final String category, final String name, final int default_value){
    return get(category, name, default_value, null, 0, Integer.MAX_VALUE).getInt();
  }

  @SubscribeEvent
  public final void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
    if(event.getModID().equals(OverpoweredMod.MOD_ID)){
      this.load_values();
    }
  }
  
}
