package addsynth.overpoweredmod.init;

import java.io.File;
import addsynth.core.material.Material;
import addsynth.core.material.types.Gem;
import addsynth.core.material.types.Metal;
import addsynth.core.worldgen.OreGenerator;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.FeatureConfig;
import addsynth.overpoweredmod.config.ValuesConfig;
import addsynth.overpoweredmod.game.core.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;


public final class Setup {

  public static boolean config_loaded;
  public static boolean items_registered;
  public static boolean registered_recipes;
  public static boolean creative_tabs_registered;
  
  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredMod.log.info("Loading Configuration files...");

      final File config_directory = new File(Loader.instance().getConfigDir(),OverpoweredMod.MOD_NAME); // let java do it in case we run on other OS besides Windows.

      Config.initialize(       new File(config_directory,"main.cfg"));
      FeatureConfig.initialize(new File(config_directory,"feature_disable.cfg"));
      ValuesConfig.initialize( new File(config_directory,"values.cfg"));

      MinecraftForge.EVENT_BUS.register(Config.instance);
      MinecraftForge.EVENT_BUS.register(FeatureConfig.instance);
      MinecraftForge.EVENT_BUS.register(ValuesConfig.instance);

      config_loaded = true;

      OverpoweredMod.log.info("Done Loading Configuration files.");
    }
  }
  
  /** <p>This should ONLY be called from OverpoweredMod class.</p>
   *  <p>This doesn't actually set up the WorldGenerators, instead it sets a boolean value in ADDSynthCore.
   *  It's ADDSynthCore that actually sets up the WorldGenerators when its Initialization Event is fired.
   *  So you have to ensure that it gets fired AFTER this function is called.</p>
   */
  public static final void register_world_generators(){
    Debug.log_setup_info("Begin registering World Generators...");

    OreGenerator.request_to_generate(Material.RUBY);
    OreGenerator.request_to_generate(Material.TOPAZ);
    OreGenerator.request_to_generate(Material.CITRINE);
    OreGenerator.request_to_generate(Material.EMERALD);
    OreGenerator.request_to_generate(Material.SAPPHIRE);
    OreGenerator.request_to_generate(Material.AMETHYST);
    OreGenerator.request_to_generate(Material.TIN);
    OreGenerator.request_to_generate(Material.ALUMINUM);
    OreGenerator.request_to_generate(Material.COPPER);
    OreGenerator.request_to_generate(Material.SILVER);
    OreGenerator.request_to_generate(Material.PLATINUM);
    OreGenerator.request_to_generate(Material.TITANIUM);
    
    Debug.log_setup_info("Finished registering World Generators.");
  }

}
