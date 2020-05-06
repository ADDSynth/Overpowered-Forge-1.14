package addsynth.overpoweredmod.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.overpoweredmod.Debug;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Features {

  static {
    Debug.log_setup_info("Features class was loaded.");
  }

  public static ForgeConfigSpec.BooleanValue energy_tools;
  public static ForgeConfigSpec.BooleanValue void_tools;

  public static ForgeConfigSpec.BooleanValue light_block;
  public static ForgeConfigSpec.BooleanValue null_block;

  public static ForgeConfigSpec.BooleanValue gem_converter;
  public static ForgeConfigSpec.BooleanValue magic_infuser;
  public static ForgeConfigSpec.BooleanValue identifier;
  public static ForgeConfigSpec.BooleanValue lasers;
  public static ForgeConfigSpec.BooleanValue energy_suspension_bridge;
  public static ForgeConfigSpec.BooleanValue portal;
  public static ForgeConfigSpec.BooleanValue crystal_matter_generator;
  public static ForgeConfigSpec.BooleanValue advanced_ore_refinery;
  public static ForgeConfigSpec.BooleanValue fusion_container;
  
  public static ForgeConfigSpec.BooleanValue white_laser;
  public static ForgeConfigSpec.BooleanValue red_laser;
  public static ForgeConfigSpec.BooleanValue orange_laser;
  public static ForgeConfigSpec.BooleanValue yellow_laser;
  public static ForgeConfigSpec.BooleanValue green_laser;
  public static ForgeConfigSpec.BooleanValue cyan_laser;
  public static ForgeConfigSpec.BooleanValue blue_laser;
  public static ForgeConfigSpec.BooleanValue magenta_laser;
  
  public static ForgeConfigSpec.BooleanValue iron_frame_block;
  public static ForgeConfigSpec.BooleanValue black_hole;

  public static ForgeConfigSpec.BooleanValue dimensional_anchor;

  public static ForgeConfigSpec.BooleanValue bronze_trophy;
  public static ForgeConfigSpec.BooleanValue silver_trophy;
  public static ForgeConfigSpec.BooleanValue gold_trophy;
  public static ForgeConfigSpec.BooleanValue platinum_trophy;

  private static final Pair<Features, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Features::new);
  public static final Features INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

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

  public Features(final ForgeConfigSpec.Builder builder){
  
    builder.push("Feature Disable");
  
    builder.push("Items");
    energy_tools       = builder.define("Energy Tools", true);
    void_tools         = builder.define("Void Tools", true);
    dimensional_anchor = builder.define("Dimensional Anchor", true);
    builder.pop();

    builder.push("Machines");
    gem_converter              = builder.define("Gem Converter",            true);
    magic_infuser              = builder.define("Magic Infuser",            true);
    identifier                 = builder.define("Identifier",               true);
    energy_suspension_bridge   = builder.define("Energy Suspension Bridge", true);
    portal                     = builder.define("Portal",                   true);
    crystal_matter_generator   = builder.define("Crystal Matter Generator", true);
    advanced_ore_refinery      = builder.define("Advanced Ore Refinery",    true);
    fusion_container           = builder.define("Fusion Energy",            true);
    builder.pop();

    builder.push("Lasers");
    Features.lasers        = builder.define("Enable",        true);
    Features.white_laser   = builder.define("White Laser",   true);
    Features.red_laser     = builder.define("Red Laser",     true);
    Features.orange_laser  = builder.define("Orange Laser",  true);
    Features.yellow_laser  = builder.define("Yellow Laser",  true);
    Features.green_laser   = builder.define("Green Laser",   true);
    Features.cyan_laser    = builder.define("Cyan Laser",    true);
    Features.blue_laser    = builder.define("Blue Laser",    true);
    Features.magenta_laser = builder.define("Magenta Laser", true);
    builder.pop();

    builder.push("Blocks");
    Features.light_block      = builder.define("Light Block",      true);
    Features.null_block       = builder.define("Null Block",       true);
    Features.iron_frame_block = builder.define("Iron Frame Block", true);
    Features.black_hole       = builder.define("Black Hole",       true);
    builder.pop();
    
    builder.push("Trophies");
    Features.bronze_trophy   = builder.define("Bronze Trophy",   true);
    Features.silver_trophy   = builder.define("Silver Trophy",   true);
    Features.gold_trophy     = builder.define("Gold Trophy",     true);
    Features.platinum_trophy = builder.define("Platinum Trophy", true);
    builder.pop();
    
    builder.pop();
  }

  public static final boolean trophies(){
    return bronze_trophy.get() || silver_trophy.get() || gold_trophy.get() || platinum_trophy.get();
  }

}
