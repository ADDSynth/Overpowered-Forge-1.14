package addsynth.core.config.gui;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.config.*;
import addsynth.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public final class GuiADDSynthCoreConfig extends GuiConfig {

  public GuiADDSynthCoreConfig(final GuiScreen parentScreen){
    super(parentScreen, getElements(), ADDSynthCore.MOD_ID, false, false, "ADDSynthCore Config");
  }

  private static final ArrayList<IConfigElement> getElements(){
    ArrayList<IConfigElement> list = new ArrayList<>(10);
   
    list.add(ConfigUtil.addConfigFile("Main",                  ADDSynthCore.MOD_ID, Config.instance));
    list.add(ConfigUtil.addCategoryElements("Feature Disable", ADDSynthCore.MOD_ID, Features.instance, Features.MAIN));
    if(Config.show_advanced_config){
      list.add(ConfigUtil.addConfigFile("Worldgen",              ADDSynthCore.MOD_ID, WorldgenConfig.instance));
    }
    
    return list;
  }

}
