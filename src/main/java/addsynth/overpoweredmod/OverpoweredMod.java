package addsynth.overpoweredmod;

import addsynth.core.game.RegistryUtil;
// import addsynth.overpoweredmod.assets.Achievements;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.compatability.*;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.game.recipes.CompressorRecipes;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import addsynth.overpoweredmod.init.Registers;
import addsynth.overpoweredmod.init.Setup;
import addsynth.overpoweredmod.network.NetworkHandler;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = OverpoweredMod.MOD_ID) // MAYBE: look into using SaveInspectionHandler to control which saved worlds are opened with THIS version of the mod.
public class OverpoweredMod {

  public static final String MOD_ID = "overpowered";
  public static final String MOD_NAME = "Overpowered";
  public static final String VERSION = "v1.1.0 - July 16, 2019";
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public OverpoweredMod(){
    FMLJavaModLoadingContext.get().getModEventBus().addListener(OverpoweredMod::main_setup);
    Setup.init_config();
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin constructing Overpowered...");
          
    Setup.register_world_generators(); // TODO: Send this to ADDSynthCore via InterMod Communications
    // Achievements.registerAchievements();
    if(Features.compressor){ // running this code doesn't hurt anything, but this boolean check is an optimization.
      CompressorRecipes.register();
    }
    NetworkHandler.registerMessages();
    NetworkRegistry.INSTANCE.registerGuiHandler(OverpoweredMod.instance,new GuiHandler());
    WeirdDimension.register();
    DeferredWorkQueue.runLater(() -> OreRefineryRecipes.register());
    DeferredWorkQueue.runLater(() -> CompatabilityManager.init_mod_compatability());
    
    log.info("Done constructing Overpowered.");
  }
}
