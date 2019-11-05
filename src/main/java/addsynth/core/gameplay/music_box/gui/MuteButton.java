package addsynth.core.gameplay.music_box.gui;

import addsynth.core.ADDSynthCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class MuteButton extends GuiButton {

  private boolean mute;
  public final byte track;
  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
  private static final int texture_width = 24;
  private static final int texture_height = 24;
  private static final int texture_x = 64;
  private static final int texture_y = 32;

  public MuteButton(final int buttonId, final int x, final int y, final byte track, final boolean mute){
    super(buttonId, x, y, GuiMusicBox.mute_button_size, GuiMusicBox.mute_button_size, null);
    this.track = track;
    this.mute = mute;
  }

  public final void toggle_mute(){
    mute = !mute;
  }

  @Override
  public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partial_ticks) {
    if(visible){
      mc.getTextureManager().bindTexture(texture);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      drawScaledCustomSizeModalRect(x, y, mute ? texture_x + 24 : texture_x, texture_y,
        texture_width, texture_height, GuiMusicBox.mute_button_size, GuiMusicBox.mute_button_size, 256, 256);
    }
  }

}
