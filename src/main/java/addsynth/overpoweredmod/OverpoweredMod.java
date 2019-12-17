package addsynth.overpoweredmod;

import java.io.File;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.Compatability;
import addsynth.core.game.Game;
import addsynth.core.game.Icon;
import addsynth.core.game.RegistryUtil;
import addsynth.core.material.Material;
import addsynth.core.worldgen.OreGenerator;
import addsynth.energy.gameplay.gui.*;
// import addsynth.overpoweredmod.assets.Achievements;
import addsynth.overpoweredmod.client.gui.tiles.*;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.config.*;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Metals;
import addsynth.overpoweredmod.game.core.Tools;
import addsynth.overpoweredmod.game.recipes.CompressorRecipes;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import addsynth.overpoweredmod.network.NetworkHandler;
import addsynth.overpoweredmod.registers.Containers;
import addsynth.overpoweredmod.registers.Registers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
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
  public static final String VERSION = "v1.1.0 - July 16, 2019";
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public static ItemGroup creative_tab;
  public static ItemGroup gems_creative_tab;
  public static ItemGroup machines_creative_tab;
  public static ItemGroup tools_creative_tab;
  public static ItemGroup metals_creative_tab;

  private static boolean config_loaded;

  public OverpoweredMod(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    context.getModEventBus().addListener(OverpoweredMod::main_setup);
    context.getModEventBus().addListener(OverpoweredMod::client_setup);
    context.getModEventBus().addListener(OverpoweredMod::inter_mod_communications);
    init_config();
  }

  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredMod.log.info("Loading Configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      // OPTIMIZE: the two ways to get the mod context can probably be combined/merged, but I don't want to think about that right now.
  
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

  private static final void inter_mod_communications(final InterModEnqueueEvent event){
    request_ores_to_generate();
    if(Compatability.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

  private static final void request_ores_to_generate(){
    final String mod = ADDSynthCore.MOD_ID;
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
    setup_creative_tabs();
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

  private static final void setup_creative_tabs(){
    init_config();
    
    final Icon[] main_icons =     {new Icon(Init.energy_crystal, true)};
    final Icon[] gem_icons =      {new Icon(Gems.ruby, true)};
    final Icon[] machines_icons = {new Icon(OverpoweredMod.registry.getItemBlock(Machines.generator), true)};
    final Icon[] tool_icons = {
                               new Icon(Tools.energy_tools.pickaxe,     Features.energy_tools.get()),
                               new Icon(Tools.unidentified_armor[2][0], Features.identifier.get()),
                               new Icon(Tools.void_toolset.sword,       Features.void_tools.get()),
                               new Icon(Items.STONE_SHOVEL)
                             };
    final Icon[] metal_icons = {new Icon(Metals.TIN.ingot, true)};

    creative_tab          = Game.NewCreativeTab("overpowered", main_icons);
    gems_creative_tab     = Config.creative_tab_gems.get()     ? Game.NewCreativeTab("overpowered_gems",     gem_icons)      : creative_tab;
    machines_creative_tab = Config.creative_tab_machines.get() ? Game.NewCreativeTab("overpowered_machines", machines_icons) : creative_tab;
    tools_creative_tab    = Config.creative_tab_tools.get()    ? Game.NewCreativeTab("overpowered_tools",    tool_icons)     : creative_tab;
    metals_creative_tab   = Config.creative_tab_metals.get()   ? Game.NewCreativeTab("overpowered_metals",   metal_icons)    : creative_tab;
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

  // TODO: add item_explosion command in Overpowered version 1.4.

}
