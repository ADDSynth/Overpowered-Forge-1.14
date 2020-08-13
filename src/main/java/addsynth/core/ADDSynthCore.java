package addsynth.core;

import java.io.File;
import java.util.stream.Stream;
import addsynth.core.config.*;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.Containers;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.compat.CompatabilityManager;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.material.Material;
import addsynth.core.material.MaterialsUtil;
import addsynth.core.material.types.OreMaterial;
import addsynth.core.worldgen.OreGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
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
  public static final String VERSION_DATE = "August 13, 2020";

  private static boolean config_loaded;
  public static final Logger log = LogManager.getLogger(NAME);
  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public static final ItemGroup creative_tab = new ItemGroup("addsynthcore"){
    @Override
    public final ItemStack createIcon(){
      return Features.caution_block.get() ? new ItemStack(registry.getItemBlock(Core.caution_block), 1) :
             Features.music_box.get()     ? new ItemStack(registry.getItemBlock(Core.music_box), 1) :
             Features.scythes.get()       ? new ItemStack(Core.stone_scythe, 1) :
             new ItemStack(Blocks.GRASS, 1);
    }
  };

  public ADDSynthCore(){
    ADDSynthCore.log.info("Begin constructing ADDSynthCore class object...");
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthCore::main_setup);
    bus.addListener(ADDSynthCore::client_setup);
    bus.addListener(ADDSynthCore::process_imc_messages);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::onServerStarting); // UNUSED
    init_config();
    ADDSynthCore.log.info("Done constructing ADDSynthCore class object.");
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
    log.info("Begin ADDSynthCore main setup...");
  
    Debug.debug();
    NetworkHandler.registerMessages();
    MaterialsUtil.registerResponder(CompatabilityManager::set_scythe_harvest_blocks);
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init());

    log.info("Finished ADDSynthCore main setup.");
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    ScreenManager.registerFactory(Containers.MUSIC_BOX, GuiMusicBox::new);
  }

  private static final void process_imc_messages(final InterModProcessEvent event){
    final Stream<IMCMessage> message_stream = event.getIMCStream();
    message_stream.forEach(message -> {
      final String sender  = message.getSenderModId();
      final String type    = message.getMethod();
      final Object payload = message.getMessageSupplier().get();
      if(type.equals(OreGenerator.REQUEST_ORE)){
        handle_generate_ore_requests(sender, payload);
      }
    });
  }

  private static final void handle_generate_ore_requests(final String sender, final Object payload){
    if(payload instanceof OreMaterial){
      OreGenerator.request_to_generate(sender, (OreMaterial)payload);
      if(OreGenerator.generate == false){
        DeferredWorkQueue.runLater(() -> OreGenerator.register());
        OreGenerator.generate = true;
      }
    }
    else{
      ADDSynthCore.log.error("Mod '"+sender+"' sent an IMC message to ADDSynthCore requesting to generate an ore "+
        "for '"+payload.getClass().getSimpleName()+"{"+payload.toString()+"}' but it is not an OreMaterial type! "+
        "You can only register ore generators with ADDSynthCore by sending an IMC message with the "+
        Material.class.getName()+" you want to generate. The Material must be of type OreMaterial or an extension.");
    }
  }

  public static void onServerStarting(final FMLServerStartingEvent event){
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
