package addsynth.overpoweredmod;

import java.io.File;
import addsynth.core.game.RegistryUtil;
import addsynth.energy.gameplay.gui.*;
// import addsynth.overpoweredmod.assets.Achievements;
import addsynth.overpoweredmod.client.gui.tiles.*;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.config.*;
import addsynth.overpoweredmod.containers.Containers;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.game.recipes.CompressorRecipes;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import addsynth.overpoweredmod.init.Registers;
import addsynth.overpoweredmod.init.Setup;
import addsynth.overpoweredmod.network.NetworkHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = OverpoweredMod.MOD_ID) // MAYBE: look into using SaveInspectionHandler to control which saved worlds are opened with THIS version of the mod.
public class OverpoweredMod {

  public static final String MOD_ID = "overpowered";
  public static final String MOD_NAME = "Overpowered";
  public static final String VERSION = "v1.1.0 - July 16, 2019";
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  private static boolean config_loaded;

  public OverpoweredMod(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    context.getModEventBus().addListener(OverpoweredMod::main_setup);
    context.getModEventBus().addListener(OverpoweredMod::client_setup);
    // OPTIMIZE: the two ways to get the mod context can probably be combined/merged, but I don't want to think about that right now.
    init_config();
  }

  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredMod.log.info("Loading Configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
  
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,   MOD_NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC, MOD_NAME+File.separator+"feature_disable.toml");
      context.registerConfig(ModConfig.Type.COMMON, Values.CONFIG_SPEC,   MOD_NAME+File.separator+"values.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(OverpoweredMod::mod_config_event);

      config_loaded = true;
  
      OverpoweredMod.log.info("Done Loading Configuration files.");
    }
  }
  
  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin constructing Overpowered...");
          
    Setup.register_world_generators(); // TODO: Send this to ADDSynthCore via InterMod Communications
    // Achievements.registerAchievements();
    if(Features.compressor.get()){ // running this code doesn't hurt anything, but this boolean check is an optimization.
      CompressorRecipes.register();
    }
    NetworkHandler.registerMessages();
    WeirdDimension.register();
    // TODO: Railcraft doesn't exist for 1.14 yet, but still should find a way to disable the Iron to Steel smelting recipe.
    // PRIORITY: Also must find a way to adjust recipes to use ingots if the Compressor is disabled!
    DeferredWorkQueue.runLater(() -> OreRefineryRecipes.register());
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init_mod_compatability());
    
    log.info("Done constructing Overpowered.");
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
  }

  private static final void register_guis(){
    ScreenManager.registerFactory(Containers.GENERATOR,                  GuiGenerator::new);
    ScreenManager.registerFactory(Containers.ENERGY_STORAGE_CONTAINER,   GuiEnergyStorageContainer::new);
    ScreenManager.registerFactory(Containers.UNIVERSAL_ENERGY_INTERFACE, GuiUniversalEnergyInterface::new);
    ScreenManager.registerFactory(Containers.COMPRESSOR,                 GuiCompressor::new);
    ScreenManager.registerFactory(Containers.ELECTRIC_FURNACE,           GuiElectricFurnace::new);
    ScreenManager.registerFactory(Containers.GEM_CONVERTER,              GuiGemConverter::new);
    ScreenManager.registerFactory(Containers.INVERTER,                   GuiInverter::new);
    ScreenManager.registerFactory(Containers.MAGIC_INFUSER,              GuiMagicUnlocker::new);
    ScreenManager.registerFactory(Containers.IDENTIFIER,                 GuiIdentifier::new);
    ScreenManager.registerFactory(Containers.PORTAL_CONTROL_PANEL,       GuiPortalControlPanel::new);
    ScreenManager.registerFactory(Containers.PORTAL_FRAME,               GuiPortalFrame::new);
    ScreenManager.registerFactory(Containers.LASER_HOUSING,              GuiLaserHousing::new);
    ScreenManager.registerFactory(Containers.ADVANCED_ORE_REFINERY,      GuiAdvancedOreRefinery::new);
    ScreenManager.registerFactory(Containers.CRYSTAL_MATTER_GENERATOR,   GuiCrystalMatterGenerator::new);
    ScreenManager.registerFactory(Containers.FUSION_CHAMBER,             GuiSingularityContainer::new);
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
