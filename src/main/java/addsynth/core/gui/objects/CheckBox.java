package addsynth.core.gui.objects;

import addsynth.core.ADDSynthCore;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public abstract class CheckBox extends AbstractButton {

  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
  private static final int texture_x = 0;
  private static final int texture_y = 32;
  private static final int texture_width = 24;
  private static final int texture_height = 24;

  public CheckBox(final int x, final int y){
    super(x, y, 12, 12, "");
  }

  protected abstract boolean get_toggle_state();

  @Override
  public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
    final boolean checked = get_toggle_state();
    Minecraft.getInstance().getTextureManager().bindTexture(texture);
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

    blit(x, y, 12, 12, checked ? texture_x : texture_x + 24, texture_y, texture_width, texture_height, 256, 256);
  }

}
