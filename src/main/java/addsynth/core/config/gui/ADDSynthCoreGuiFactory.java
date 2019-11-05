package addsynth.core.config.gui;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public final class ADDSynthCoreGuiFactory implements IModGuiFactory {

  public ADDSynthCoreGuiFactory(){
  }

  @Override
  public final void initialize(final Minecraft minecraftInstance){
  }

  @Override
  public final Set<RuntimeOptionCategoryElement> runtimeGuiCategories(){
    return null;
  }

  @Override
  public final boolean hasConfigGui(){
    return true;
  }

  @Override
  public final GuiScreen createConfigGui(final GuiScreen parentScreen){
    return new GuiADDSynthCoreConfig(parentScreen);
  }

}
