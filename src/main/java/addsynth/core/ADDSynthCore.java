package addsynth.core;

import java.io.File;
import java.util.stream.Stream;
import addsynth.core.config.*;
import addsynth.core.game.Game;
import addsynth.core.game.Icon;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.CompatabilityManager;
import addsynth.core.gameplay.Containers;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.material.Material;
import addsynth.core.material.types.OreMaterial;
import addsynth.core.worldgen.OreGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
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
  public static ItemGroup creative_tab;
  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public ADDSynthCore(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    context.getModEventBus().addListener(ADDSynthCore::main_setup);
    context.getModEventBus().addListener(ADDSynthCore::client_setup);
    context.getModEventBus().addListener(ADDSynthCore::process_imc_messages);
    init_config();
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

      config_loaded = true;

      ADDSynthCore.log.info("Done loading configuration files.");
    }
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin constructing ADDSynthCore ...");
  
    NetworkHandler.registerMessages();
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init());
    Debug.debug();

    log.info("Done constructing ADDSynthCore.");
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    create_creative_tab();
    ScreenManager.registerFactory(Containers.MUSIC_BOX, GuiMusicBox::new);
  }

  private static final void process_imc_messages(final InterModProcessEvent event){
    final Stream<IMCMessage> message_stream = event.getIMCStream();
    message_stream.forEach(message -> {
      final String sender = message.getSenderModId();
      final String type = message.getMethod();
      final Object payload = message.getMessageSupplier().get();
      if(type.equals(OreGenerator.REQUEST_ORE)){
        if(payload instanceof OreMaterial){
          OreGenerator.request_to_generate(sender, (OreMaterial)payload);
        }
        else{
          ADDSynthCore.log.error("Mod '"+sender+"' sent an IMC message to ADDSynthCore requesting to generate an ore "+
            "for '"+payload.getClass().getSimpleName()+"{"+payload.toString()+"}' but it is not an OreMaterial type! "+
            "You can only register ore generators with ADDSynthCore by sending an IMC message with the "+
            Material.class.getName()+" you want to generate. The Material must be of type OreMaterial or an extension.");
        }
      }
    });
  }

  // Phew! Thank god the FMLClientSetupEvent runs after Blocks and Items are registered!
  private static final void create_creative_tab(){
    final Icon[] icons = {
      new Icon(registry.getItemBlock(Core.caution_block), Features.caution_block.get()),
      new Icon(registry.getItemBlock(Core.music_box), Features.music_box.get()),
      new Icon(Core.stone_scythe, Features.scythes.get()),
      new Icon(registry.getItemBlock(Blocks.GRASS))
    };
    creative_tab = Game.NewCreativeTab("addsynthcore", icons);
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
