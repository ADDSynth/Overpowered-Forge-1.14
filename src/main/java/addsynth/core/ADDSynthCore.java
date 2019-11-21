package addsynth.core;

import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.CompatabilityManager;
import addsynth.core.gameplay.GuiHandler;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.init.CoreRegister;
import addsynth.core.gameplay.init.Setup;
import addsynth.core.worldgen.OreGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = ADDSynthCore.MOD_ID)
public final class ADDSynthCore {

  public static final String MOD_ID = "addsynthcore";
  public static final String NAME = "ADDSynthCore";
  public static final String VERSION = "October 7, 2019";
  public static final Logger log = LogManager.getLogger(NAME);

  public static final ItemGroup creative_tab = create_creative_tab();

  private static final ItemGroup create_creative_tab(){
    Setup.init_config();
    return null;
  }

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public ADDSynthCore(){
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthCore::main_setup);
    Setup.init_config();
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin constructing ADDSynthCore ...");
  
    OreGenerator.initialize();
    NetworkHandler.registerMessages();
    NetworkRegistry.INSTANCE.registerGuiHandler(ADDSynthCore.instance,new GuiHandler());
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init());
    Debug.debug();

    log.info("Done constructing ADDSynthCore.");
  }

}
