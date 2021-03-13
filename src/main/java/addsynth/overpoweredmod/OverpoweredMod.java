package addsynth.overpoweredmod;

import java.io.File;
import addsynth.core.game.Compatability;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.game.RecipeUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.Material;
import addsynth.material.MaterialsUtil;
import addsynth.material.worldgen.OreGenerator;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.config.*;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.GuiAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.crystal_matter_generator.GuiCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.fusion.chamber.GuiFusionChamber;
import addsynth.overpoweredmod.machines.gem_converter.GuiGemConverter;
import addsynth.overpoweredmod.machines.generator.GuiGenerator;
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
import net.minecraftforge.fml.InterModComms;
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

@Mod(value = OverpoweredMod.MOD_ID) // MAYBE: look into using SaveInspectionHandler to control which saved worlds are opened with THIS version of the mod.
public class OverpoweredMod {

  public static final String MOD_ID = "overpowered";
  public static final String MOD_NAME = "Overpowered";
  public static final String VERSION = "1.3.4";
  public static final String VERSION_DATE = "December 28, 2020";
  private static final boolean is_beta = false;
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  private static boolean config_loaded;

  public OverpoweredMod(){
    OverpoweredMod.log.info("Begin constructing OverpoweredMod class object...");
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(OverpoweredMod::main_setup);
    bus.addListener(OverpoweredMod::client_setup);
    bus.addListener(OverpoweredMod::inter_mod_communications);
    init_config();
    OverpoweredMod.log.info("Done constructing OverpoweredMod class object.");
  }

  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredMod.log.info("Loading Configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      // MAYBE: the two ways to get the mod context can probably be combined/merged, but I don't want to think about that right now.
      //        what exactly is the relationship of FMLJavaModLoadingContext and ModLoadingContext?
  
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,        MOD_NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC,      MOD_NAME+File.separator+"feature_disable.toml");
      context.registerConfig(ModConfig.Type.COMMON, MachineValues.CONFIG_SPEC, MOD_NAME+File.separator+"machine_values.toml");
      context.registerConfig(ModConfig.Type.COMMON, Values.CONFIG_SPEC,        MOD_NAME+File.separator+"values.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(OverpoweredMod::mod_config_event);

      config_loaded = true;
  
      OverpoweredMod.log.info("Done Loading Configuration files.");
    }
  }
  
  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin Overpowered main setup...");
    
    log.info("Overpowered Mod by ADDSynth, version "+VERSION+(is_beta ? "-BETA" : "")+", built on "+VERSION_DATE+".");
    
    NetworkHandler.registerMessages();
    // WeirdDimension.register();
    // TODO: Railcraft doesn't exist for 1.14 yet, but still should find a way to disable the Iron to Steel smelting recipe.
    RecipeUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    RecipeUtil.registerResponder(Filters::regenerate_machine_filters);
    MaterialsUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    MaterialsUtil.registerResponder(Filters::regenerate_machine_filters);
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init_mod_compatability());
    
    log.info("Finished Overpowered main setup.");
  }

  private static final void inter_mod_communications(final InterModEnqueueEvent event){
    request_ores_to_generate();
    if(Compatability.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

  private static final void request_ores_to_generate(){
    final String mod = ADDSynthMaterials.MOD_ID;
    final String message = OreGenerator.REQUEST_ORE;
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.RUBY);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.TOPAZ);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.CITRINE);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.EMERALD);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.SAPPHIRE);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.AMETHYST);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.TIN);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.COPPER);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.ALUMINUM);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.SILVER);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.PLATINUM);
    InterModComms.sendTo(MOD_ID, mod, message, () -> Material.TITANIUM);
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
  }

  private static final void register_guis(){
    ScreenManager.registerFactory(Containers.GENERATOR,                  GuiGenerator::new);
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

  // TODO: add item_explosion command in Overpowered version 1.4.
  //       also add a zombie_raid command.
  //       also add bat blackout command.

}
