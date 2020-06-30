package addsynth.energy;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import addsynth.core.game.RegistryUtil;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.compressor.GuiCompressor;
import addsynth.energy.gameplay.electric_furnace.GuiElectricFurnace;
import addsynth.energy.gameplay.energy_storage.GuiEnergyStorageContainer;
import addsynth.energy.gameplay.universal_energy_interface.GuiUniversalEnergyInterface;
import addsynth.energy.registers.Containers;
import addsynth.energy.registers.NetworkHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(value = ADDSynthEnergy.MOD_ID)
public class ADDSynthEnergy {

  public static final String MOD_ID = "addsynth_energy";
  public static final String MOD_NAME = "ADDSynth Energy";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = "June 30, 2020";
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public static final ItemGroup creative_tab = new ItemGroup("addsynth_energy"){
    @Override
    public final ItemStack createIcon(){
      return new ItemStack(registry.getItemBlock(EnergyBlocks.wire));
    }
  };

  public ADDSynthEnergy(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthEnergy::main_setup);
    bus.addListener(ADDSynthEnergy::client_setup);
    init_config();
  }

  private static final void init_config(){
    new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

    final ModLoadingContext context = ModLoadingContext.get();
    context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,   MOD_NAME+File.separator+"main.toml");

    FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthEnergy::mod_config_event);
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    NetworkHandler.registerMessages();
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
  }

  private static final void register_guis(){
    ScreenManager.registerFactory(Containers.COMPRESSOR,                 GuiCompressor::new);
    ScreenManager.registerFactory(Containers.ENERGY_STORAGE_CONTAINER,   GuiEnergyStorageContainer::new);
    ScreenManager.registerFactory(Containers.UNIVERSAL_ENERGY_INTERFACE, GuiUniversalEnergyInterface::new);
    ScreenManager.registerFactory(Containers.ELECTRIC_FURNACE,           GuiElectricFurnace::new);
  }

  public static final void mod_config_event(final ModConfig.ModConfigEvent event){
    event.getConfig().save();
  }

}
