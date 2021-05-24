package addsynth.overpoweredmod;

import java.io.File;
import addsynth.core.game.Compatability;
import addsynth.core.game.RegistryUtil;
import addsynth.core.recipe.RecipeUtil;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.config.*;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.GuiAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.crystal_matter_generator.GuiCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.energy_extractor.GuiCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.GuiFusionChamber;
import addsynth.overpoweredmod.machines.gem_converter.GuiGemConverter;
import addsynth.overpoweredmod.machines.identifier.GuiIdentifier;
import addsynth.overpoweredmod.machines.inverter.GuiInverter;
import addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.GuiMagicInfuser;
import addsynth.overpoweredmod.machines.portal.control_panel.GuiPortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.GuiPortalFrame;
import addsynth.overpoweredmod.machines.suspension_bridge.GuiEnergySuspensionBridge;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = OverpoweredTechnology.MOD_ID) // MAYBE: look into using SaveInspectionHandler to control which saved worlds are opened with THIS version of the mod.
public class OverpoweredTechnology {

  public static final String MOD_ID = "overpowered"; // FUTURE: version 1.5 will rename the modid to overpowered_technology. All assets must also be renamed.
  public static final String MOD_NAME = "Overpowered Technology";
  public static final String VERSION = "1.3.4";
  public static final String VERSION_DATE = "December 28, 2020";
  private static final boolean is_beta = false;
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  private static boolean config_loaded;

  public OverpoweredTechnology(){
    OverpoweredTechnology.log.info("Begin constructing "+OverpoweredTechnology.class.getSimpleName()+" class object...");
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(OverpoweredTechnology::main_setup);
    bus.addListener(OverpoweredTechnology::client_setup);
    bus.addListener(OverpoweredTechnology::inter_mod_communications);
    init_config();
    OverpoweredTechnology.log.info("Done constructing "+OverpoweredTechnology.class.getSimpleName()+" class object.");
  }

  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredTechnology.log.info("Loading Configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      // MAYBE: the two ways to get the mod context can probably be combined/merged, but I don't want to think about that right now.
      //        what exactly is the relationship of FMLJavaModLoadingContext and ModLoadingContext?
  
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,        MOD_NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC,      MOD_NAME+File.separator+"feature_disable.toml");
      context.registerConfig(ModConfig.Type.COMMON, MachineValues.CONFIG_SPEC, MOD_NAME+File.separator+"machine_values.toml");
      context.registerConfig(ModConfig.Type.COMMON, Values.CONFIG_SPEC,        MOD_NAME+File.separator+"values.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(OverpoweredTechnology::mod_config_event);

      config_loaded = true;
  
      OverpoweredTechnology.log.info("Done Loading Configuration files.");
    }
  }
  
  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin "+MOD_NAME+" main setup...");
    
    log.info("Overpowered Mod by ADDSynth, version "+VERSION+(is_beta ? "-BETA" : "")+", built on "+VERSION_DATE+".");
    
    NetworkHandler.registerMessages();
    // WeirdDimension.register();
    // TODO: Railcraft doesn't exist for 1.14 yet, but still should find a way to disable the Iron to Steel smelting recipe.
    RecipeUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    RecipeUtil.registerResponder(Filters::regenerate_machine_filters);
    MaterialsUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    MaterialsUtil.registerResponder(Filters::regenerate_machine_filters);
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init_mod_compatability());
    
    log.info("Finished "+MOD_NAME+" main setup.");
  }

  private static final void inter_mod_communications(final InterModEnqueueEvent event){
    if(Compatability.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
  }

  private static final void register_guis(){
    ScreenManager.registerFactory(Containers.CRYSTAL_ENERGY_EXTRACTOR,   GuiCrystalEnergyExtractor::new);
    ScreenManager.registerFactory(Containers.GEM_CONVERTER,              GuiGemConverter::new);
    ScreenManager.registerFactory(Containers.INVERTER,                   GuiInverter::new);
    ScreenManager.registerFactory(Containers.MAGIC_INFUSER,              GuiMagicInfuser::new);
    ScreenManager.registerFactory(Containers.IDENTIFIER,                 GuiIdentifier::new);
    ScreenManager.registerFactory(Containers.ENERGY_SUSPENSION_BRIDGE,   GuiEnergySuspensionBridge::new);
    ScreenManager.registerFactory(Containers.PORTAL_CONTROL_PANEL,       GuiPortalControlPanel::new);
    ScreenManager.registerFactory(Containers.PORTAL_FRAME,               GuiPortalFrame::new);
    ScreenManager.registerFactory(Containers.LASER_HOUSING,              GuiLaserHousing::new);
    ScreenManager.registerFactory(Containers.ADVANCED_ORE_REFINERY,      GuiAdvancedOreRefinery::new);
    ScreenManager.registerFactory(Containers.CRYSTAL_MATTER_GENERATOR,   GuiCrystalMatterGenerator::new);
    ScreenManager.registerFactory(Containers.FUSION_CHAMBER,             GuiFusionChamber::new);
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
