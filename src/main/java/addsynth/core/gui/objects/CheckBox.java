package addsynth.core.gui.objects;

import addsynth.core.ADDSynthCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class CheckBox extends GuiButton {

  public boolean checked;
  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
  private static final int texture_x = 0;
  private static final int texture_y = 32;
  private static final int texture_width = 24;
  private static final int texture_height = 24;

  public CheckBox(final int buttonId, final int x, final int y, final boolean checked){
    super(buttonId, x, y, 12, 12, null);
    this.checked = checked;
  }

  public final void toggle(){
    checked = !checked;
  }

  @Override
  public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partial_ticks){
    if(visible){
      mc.getTextureManager().bindTexture(texture);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      drawScaledCustomSizeModalRect(x, y, checked ? texture_x : texture_x + 24, texture_y,
        texture_width, texture_height, 12, 12, 256, 256);
    }
  }

}
