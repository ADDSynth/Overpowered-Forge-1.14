package addsynth.core;

import addsynth.core.game.IProxy;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.CompatabilityManager;
import addsynth.core.gameplay.GuiHandler;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.init.CoreRegister;
import addsynth.core.gameplay.init.Setup;
import addsynth.core.worldgen.OreGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = ADDSynthCore.MOD_ID)
public final class ADDSynthCore {

  public static final String MOD_ID = "addsynthcore";
  public static final String NAME = "ADDSynthCore";
  public static final String VERSION = "October 7, 2019";
  public static final Logger log = LogManager.getLogger(NAME);

  @Instance(value = MOD_ID)
  public static ADDSynthCore instance;

  @SidedProxy(modId = MOD_ID,clientSide="addsynth.core.gameplay.init.ClientProxy",
                             serverSide="addsynth.core.gameplay.init.ServerProxy")
  public static IProxy proxy;

  public static final ItemGroup creative_tab = create_creative_tab();

  private static final ItemGroup create_creative_tab(){
    Setup.init_config();
    return null;
  }

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  @EventHandler
  public static final void preinit(FMLPreInitializationEvent event){
    log.info("Begin Preinitialization...");
  
    Setup.init_config();
    CoreRegister.register_tileentities();
    proxy.preinit();
  
    log.info("Finished Preinitialization.");
  }

  @EventHandler
  public static final void init(FMLInitializationEvent event){
    log.info("Begin Initialization...");

    Debug.init();
    OreGenerator.initialize();
    NetworkHandler.registerMessages();
    NetworkRegistry.INSTANCE.registerGuiHandler(ADDSynthCore.instance,new GuiHandler());
    
    log.info("Finished Initialization.");
  }

  @EventHandler
  public static final void postinit(FMLPostInitializationEvent event){
    log.info("Begin PostInitialization...");

    Debug.postInit();
    CompatabilityManager.init();

    log.info("Finished PostInitialization.");
  }

}
