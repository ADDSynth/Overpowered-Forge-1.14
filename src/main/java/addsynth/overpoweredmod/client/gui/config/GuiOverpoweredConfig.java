package addsynth.overpoweredmod.client.gui.config;

import java.util.ArrayList;
import addsynth.core.util.ConfigUtil;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public final class GuiOverpoweredConfig extends GuiConfig {

  // Absorb and learn:
  // https://github.com/BuildCraft/BuildCraft/blob/8.0.x/common/buildcraft/core/client/ConfigGuiFactoryBC.java
  // https://github.com/Glitchfiend/BiomesOPlenty/blob/BOP-1.10.2-5.0.x/src/main/java/biomesoplenty/client/gui/GuiBOPConfig.java
  // https://github.com/Railcraft/Railcraft/blob/mc-1.10.2/src/main/java/mods/railcraft/client/core/RailcraftGuiConfig.java

  public GuiOverpoweredConfig(final GuiScreen parentScreen){
    super(parentScreen, getElements(), OverpoweredMod.MOD_ID, false, false, "Overpowered Config");
  }

  private static final ArrayList<IConfigElement> getElements(){
    ArrayList<IConfigElement> list = new ArrayList<>(10);
   
    list.add(ConfigUtil.addConfigFile("Main",            OverpoweredMod.MOD_ID, Config.instance));
    list.add(ConfigUtil.addConfigFile("Feature Disable", OverpoweredMod.MOD_ID, FeatureConfig.instance));
    if(Config.show_advanced_config){
      list.add(ConfigUtil.addConfigFile("Values",          OverpoweredMod.MOD_ID, ValuesConfig.instance));
    }
    
    return list;
  }

}
