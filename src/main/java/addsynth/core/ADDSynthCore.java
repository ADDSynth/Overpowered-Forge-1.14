package addsynth.core;

import java.io.File;
import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.core.config.*;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.commands.ADDSynthCommands;
import addsynth.core.gameplay.compat.CompatabilityManager;
import addsynth.core.gameplay.team_manager.data.CriteriaData;
import addsynth.core.gameplay.team_manager.data.TeamData;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.material.MaterialsUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = ADDSynthCore.MOD_ID)
public final class ADDSynthCore {

  public static final String MOD_ID = "addsynthcore";
  public static final String NAME = "ADDSynthCore";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = "June 3, 2022";

  private static boolean config_loaded;
  public static final Logger log = LogManager.getLogger(NAME);
  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public static final ItemGroup creative_tab = new ItemGroup("addsynthcore"){
    @Override
    public final ItemStack createIcon(){
      return Features.caution_block.get() ? new ItemStack(registry.getItemBlock(Core.caution_block), 1) :
             Features.music_box.get()     ? new ItemStack(registry.getItemBlock(Core.music_box), 1) :
             Features.scythes.get()       ? new ItemStack(Core.stone_scythe, 1) :
             Features.team_manager.get()  ? new ItemStack(Core.team_manager, 1) :
             new ItemStack(Blocks.GRASS, 1);
    }
  };

  public ADDSynthCore(){
    ADDSynthCore.log.info("Begin constructing ADDSynthCore class object...");
    // FIX: Configs aren't working again? Can't disable things before they're registered.

    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthCore::main_setup);
    bus.addListener(ADDSynthCore::client_setup);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::onServerStarting);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::onServerStarted);

    init_config();

    if(Features.team_manager.get()){
      MinecraftForge.EVENT_BUS.addListener(TeamData::serverTick);
    }
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCommands::tick);
    
    ADDSynthCore.log.info("Done constructing ADDSynthCore class object.");
  }

  public static final void init_config(){
    if(config_loaded == false){
      ADDSynthCore.log.info("Begin loading configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,         NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC,       NAME+File.separator+"feature_disable.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthCore::mod_config_event);

      config_loaded = true;

      ADDSynthCore.log.info("Done loading configuration files.");
    }
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin ADDSynthCore main setup...");
    log.info(CommonUtil.get_mod_info(NAME, "ADDSynth", VERSION, DevStage.DEVELOPMENT, VERSION_DATE));
  
    Debug.debug();
    if(Config.debug_mod_detection.get()){
      DeferredWorkQueue.runLater(() -> Compatibility.debug());
    }
    NetworkHandler.registerMessages();
    MaterialsUtil.registerResponder(CompatabilityManager::set_scythe_harvest_blocks);
    MaterialsUtil.registerResponder(Debug::dump_tags);
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init());

    log.info("Finished ADDSynthCore main setup.");
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    if(Features.team_manager.get()){
      CriteriaData.calculate();
    }
  }

  public static void onServerStarting(final FMLServerStartingEvent event){
    ADDSynthCommands.register(event.getCommandDispatcher());
    // TODO: I can customize recipes here?
    // TODO: change the recipes of the Trophies to use ingots instead of metal plates, if no plates exist.
    // And if I can do that, go ahead and reimplement the ability to disable the Trophy base item.
  }

  public static void onServerStarted(final FMLServerStartedEvent event){
    if(Compatibility.PROJECT_E.loaded){
      EMCValue.check_emc_values();
    }
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
