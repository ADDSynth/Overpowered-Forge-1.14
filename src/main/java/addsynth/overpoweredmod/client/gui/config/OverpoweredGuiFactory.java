package addsynth.overpoweredmod.client.gui.config;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public final class OverpoweredGuiFactory implements IModGuiFactory {

  public OverpoweredGuiFactory(){
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
    return new GuiOverpoweredConfig(parentScreen);
  }

}
