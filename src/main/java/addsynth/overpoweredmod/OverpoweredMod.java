package addsynth.overpoweredmod;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import addsynth.core.game.IProxy;
import addsynth.core.game.RegistryUtil;
// import addsynth.overpoweredmod.assets.Achievements;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import addsynth.overpoweredmod.init.Registers;
import addsynth.overpoweredmod.init.Setup;
import addsynth.overpoweredmod.network.NetworkHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance; // used to retrieve other mods
import net.minecraftforge.fml.common.SidedProxy; // used to ensure client only stuff like guis
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


@Mod(modid   = OverpoweredMod.MOD_ID, // MAYBE: look into using SaveInspectionHandler to control which saved worlds are opened with THIS version of the mod.
     name    = OverpoweredMod.MOD_NAME,
     version = OverpoweredMod.VERSION,
     acceptedMinecraftVersions = "[1.12.2]",
     dependencies = "required-after:forge@[14.23.5.2816,];required-after:addsynthcore;after:baubles@[1.5.2,]",
     guiFactory = "addsynth.overpoweredmod.client.gui.config.OverpoweredGuiFactory",
     updateJSON = "http://www.gyrostudiostechnology.com/minecraft/mods/overpowered/update.json",
     modLanguage = "java",
     canBeDeactivated = true)

// http://mcforge.readthedocs.io/en/latest/gettingstarted/structuring/#what-is-mod
// I FINALLY found out how the dependancy string is supposed to be structured:
//      net.minecraftforge.fml.common.Loader     on Line 713,        computeDependancies()
//  and   FMLModContainer.class        line 209       bindMetadata(MetadataCollection mc)
public class OverpoweredMod {

  public static final String MOD_ID = "overpoweredmod"; // FUTURE: rename mod id to just overpowered, once we rename everything to ADDSynthsMods.jar
  public static final String MOD_NAME = "Overpowered";
  public static final String VERSION = "v1.1.0 - July 16, 2019";
    
  @Instance(value = MOD_ID)
  public static OverpoweredMod instance;
    
  @SidedProxy(modId = MOD_ID,clientSide="addsynth.overpoweredmod.init.proxy.ClientProxy",
    		                 serverSide="addsynth.overpoweredmod.init.proxy.ServerProxy")
  public static IProxy proxy;
  
  /** It is far superior to get a Logger ourselves doing it this way instead of using
   *  {@link FMLPreInitializationEvent#getModLog()} for 2 reasons. I've checked out what that
   *  function does and it just basically does the same thing, but WE use the Mod's name, not
   *  the ID. And secondly, this gets assigned as soon as we need it, and not at the beginning
   *  of the PreInit event.
   */
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);


  @EventHandler
  public static final void preInit(final FMLPreInitializationEvent preInitEvent){
    log.info("Begin PreInitialization...");
          
    Setup.init_config();
    Registers.registerTileEntities();
    Setup.register_world_generators();
    proxy.preinit(); // loads Creative Tabs.
    
    log.info("Finished PreInitialization.");
  }




  @EventHandler
  public static final void init(final FMLInitializationEvent event){
    log.info("Begin Initialization...");
    
    Setup.register_oredictionary_names();
    // Achievements.registerAchievements();
    NetworkHandler.registerMessages();
    NetworkRegistry.INSTANCE.registerGuiHandler(OverpoweredMod.instance,new GuiHandler());
    WeirdDimension.register();
    proxy.init();
    
    log.info("Finished Initialization.");
  }




  @EventHandler
  public static final void postInit(final FMLPostInitializationEvent postInitEvent){
    log.info("Begin PostInitialization...");
    
    OreRefineryRecipes.register();
    CompatabilityManager.init_mod_compatability(); // test other mods and adjust things.
    
    log.info("Finished PostInitialization.");
  }

}
