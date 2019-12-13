package addsynth.core;

import java.io.File;
import addsynth.core.config.*;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.CompatabilityManager;
import addsynth.core.gameplay.Containers;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.init.Setup;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.worldgen.OreGenerator;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
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

@Mod(value = ADDSynthCore.MOD_ID)
public final class ADDSynthCore {

  public static final String MOD_ID = "addsynthcore";
  public static final String NAME = "ADDSynthCore";
  public static final String VERSION = "October 7, 2019";

  private static boolean config_loaded;
  public static final Logger log = LogManager.getLogger(NAME);
  private static ItemGroup creative_tab;
  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public ADDSynthCore(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    context.getModEventBus().addListener(ADDSynthCore::main_setup);
    context.getModEventBus().addListener(ADDSynthCore::client_setup);
    init_config();
    creative_tab = create_creative_tab();
  }

  public static final ItemGroup creative_tab(){ // Security!
    return creative_tab;
  }

  public static final void init_config(){
    if(config_loaded == false){
      ADDSynthCore.log.info("Begin loading configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,         NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC,       NAME+File.separator+"feature_disable.toml");
      context.registerConfig(ModConfig.Type.COMMON, WorldgenConfig.CONFIG_SPEC, NAME+File.separator+"worldgen.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthCore::mod_config_event);

      Setup.config_loaded = true;
  
      ADDSynthCore.log.info("Done loading configuration files.");
    }
  }

  private static final ItemGroup create_creative_tab(){
    return null;
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin constructing ADDSynthCore ...");
  
    OreGenerator.initialize();
    NetworkHandler.registerMessages();
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init());
    Debug.debug();

    log.info("Done constructing ADDSynthCore.");
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    ScreenManager.registerFactory(Containers.MUSIC_BOX, GuiMusicBox::new);
  }

}
