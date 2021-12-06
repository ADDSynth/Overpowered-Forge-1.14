package addsynth.material;

import java.io.File;
import java.util.stream.Stream;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.material.compat.MaterialsCompat;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.worldgen.OreGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ADDSynthMaterials.MOD_ID)
public final class ADDSynthMaterials {

  public static final String MOD_ID = "addsynth_materials";
  public static final String MOD_NAME = "ADDSynth Materials";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = ADDSynthCore.VERSION_DATE;

  public static final Logger log = LogManager.getLogger(MOD_NAME);
  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);
  private static boolean config_loaded;

  public static final ItemGroup creative_tab = new ItemGroup(MOD_ID){
    @Override
    public final ItemStack createIcon(){
      return new ItemStack(Material.RUBY.gem);
    }
  };

  public ADDSynthMaterials(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthMaterials::main_setup);
    bus.addListener(MaterialsCompat::sendIMCMessages);
    bus.addListener(ADDSynthMaterials::process_imc_messages);
    init_config();
  }

  public static final void init_config(){
    if(config_loaded == false){
      ADDSynthMaterials.log.info("Begin loading configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      context.registerConfig(ModConfig.Type.COMMON, WorldgenConfig.CONFIG_SPEC, MOD_NAME+File.separator+"worldgen.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthMaterials::mod_config_event);

      config_loaded = true;

      ADDSynthMaterials.log.info("Done loading configuration files.");
    }
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    // log.info("Begin ADDSynthMaterials main setup...");
    log.info(CommonUtil.get_mod_info(MOD_NAME, "ADDSynth", VERSION, DevStage.DEVELOPMENT, VERSION_DATE));
    OreGenerator.register();
    // DeferredWorkQueue.runLater(OreGenerator::register);
    // log.info("Finished ADDSynthMaterials main setup.");
  }

  private static final void process_imc_messages(final InterModProcessEvent event){
    final Stream<IMCMessage> message_stream = event.getIMCStream();
    message_stream.forEach(message -> {
      final String sender  = message.getSenderModId();
      final String type    = message.getMethod();
      final Object payload = message.getMessageSupplier().get();
    });
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
