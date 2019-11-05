package addsynth.overpoweredmod.config;

import java.io.File;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class FeatureConfig extends Configuration {

  public static FeatureConfig instance;

  private static final String MAIN     = "Main";
  private static final String ITEMS    = "Items";
  private static final String TOOLS    = ITEMS + ".Tools"; // UNUSED
  private static final String BLOCKS   = "Blocks";
  private static final String MACHINES = "Machines";
  private static final String LASERS   = "Lasers";
  private static final String TROPHIES = "Trophies";
  
  // https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/util/ConfigManagerCore.java
  // https://github.com/Railcraft/Railcraft/blob/mc-1.10.2/src/main/java/mods/railcraft/common/core/RailcraftConfig.java
  // https://github.com/Glitchfiend/BiomesOPlenty/blob/BOP-1.10.2-5.0.x/src/main/java/biomesoplenty/common/config/GameplayConfigurationHandler.java
  // https://github.com/mezz/JustEnoughItems/blob/1.10/src/main/java/mezz/jei/config/Config.java
  // https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/rv4-1.10/src/main/java/appeng/core/AEConfig.java

  /*
   * September 29, 2018 - Here's what I discovered:
   * Both Railcraft and Applied Energistics have a method of disabling things in their mod.
   * Railcraft disables it completely, it doesn't register the block, so Forge will warn
   *   players that a block no longer exists when loading a world.
   * Applied Energistics still registers the item, so the Forge thinks the item is still in the world.
   *   But the blocks are actually removed from the world, but the warning never appears. This makes me
   *   think that Applied Energistics somehow checks the world data and checks to see if that feature is
   *   enabled in the config.
  */

  public FeatureConfig(final File file){
    super(file, true); // creates the file if it doesn't exist and loads properties from it.
    load_values(); // sets internal values based on the currently loaded properties. Creates properties if they don't exist.
  }

  public static final void initialize(final File file){
    Debug.log_setup_info("Begin initializing Features Config...");
    instance = new FeatureConfig(file);
    Debug.log_setup_info("Finished initializing Features Config.");
  }

  private final void load_values(){
    Features.achievements = get(MAIN, "Achievements", true).getBoolean();
  
    Features.energy_tools       = get(ITEMS, "Energy Tools", true).getBoolean();
    Features.void_tools         = get(ITEMS, "Void Tools", true).getBoolean();
    Features.dimensional_anchor = get(ITEMS, "Dimensional Anchor", true).getBoolean();
    
    Features.energy_storage_container   = get(MACHINES, "Energy Storage Container", true).getBoolean();
    Features.universal_energy_interface = get(MACHINES, "Universal Energy Interface", true).getBoolean();
    Features.compressor                 = get(MACHINES, "Compressor", true, "If this is disabled, crafting recipes will use ingots instead of plates.").getBoolean();
    Features.electric_furnace           = get(MACHINES, "Electric Furnace",         true).getBoolean();
    Features.gem_converter              = get(MACHINES, "Gem Converter",            true).getBoolean();
    Features.magic_infuser              = get(MACHINES, "Magic Infuser",            true).getBoolean();
    Features.identifier                 = get(MACHINES, "Identifier",               true).getBoolean();
    Features.portal                     = get(MACHINES, "Portal",                   true).getBoolean();
    Features.crystal_matter_generator   = get(MACHINES, "Crystal Matter Generator", true).getBoolean();
    Features.advanced_ore_refinery      = get(MACHINES, "Advanced Ore Refinery",    true).getBoolean();
    Features.fusion_container           = get(MACHINES, "Fusion Energy",            true).getBoolean();

    Features.lasers        = get(LASERS, "Enable",        true).getBoolean();
    Features.white_laser   = get(LASERS, "White Laser",   true).getBoolean();
    Features.red_laser     = get(LASERS, "Red Laser",     true).getBoolean();
    Features.orange_laser  = get(LASERS, "Orange Laser",  true).getBoolean();
    Features.yellow_laser  = get(LASERS, "Yellow Laser",  true).getBoolean();
    Features.green_laser   = get(LASERS, "Green Laser",   true).getBoolean();
    Features.cyan_laser    = get(LASERS, "Cyan Laser",    true).getBoolean();
    Features.blue_laser    = get(LASERS, "Blue Laser",    true).getBoolean();
    Features.magenta_laser = get(LASERS, "Magenta Laser", true).getBoolean();

    Features.light_block      = get(BLOCKS, "Light Block", true).getBoolean();
    Features.null_block       = get(BLOCKS, "Null Block", true).getBoolean();
    Features.iron_frame_block = get(BLOCKS, "Iron Frame Block", true).getBoolean();
    Features.black_hole       = get(BLOCKS, "Black Hole", true).getBoolean();
    
    Features.bronze_trophy   = get(TROPHIES, "Bronze Trophy", true).getBoolean();
    Features.silver_trophy   = get(TROPHIES, "Silver Trophy", true).getBoolean();
    Features.gold_trophy     = get(TROPHIES, "Gold Trophy", true).getBoolean();
    Features.platinum_trophy = get(TROPHIES, "Platinum Trophy", true).getBoolean();
    Features.trophies        = Features.bronze_trophy || Features.silver_trophy || Features.gold_trophy || Features.platinum_trophy;
    
    if(this.hasChanged()){
      save();
    }
  }

  @SubscribeEvent
  public final void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
    if(event.getModID().equals(OverpoweredMod.MOD_ID)){
      this.load_values();
    }
  }
  
}
