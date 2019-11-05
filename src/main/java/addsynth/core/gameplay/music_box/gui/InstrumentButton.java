package addsynth.core.gameplay.music_box.gui;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.MusicGrid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class InstrumentButton extends GuiButton {

  public final byte track;
  private int instrument;
  private static final ResourceLocation instruments_texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/instruments.png");
  private static final int texture_width = 64;
  private static final int texture_height = 64;
  private int texture_x;
  private int texture_y;

  public InstrumentButton(final int buttonId, final int x, final int y, final byte track, final byte instrument){
    super(buttonId, x, y, GuiMusicBox.instrument_button_width, GuiMusicBox.instrument_button_height, null);
    this.track = (byte)track;
    this.instrument = (byte)instrument;
    update_instrument();
  }

  public final void change_instrument(final byte instrument){
    this.instrument = instrument;
    update_instrument();
  }

  public final void increment_instrument(){
    instrument = (instrument + 1) % MusicGrid.instruments.length;
    update_instrument();
  }

  public final void update_instrument(){
    texture_x = texture_width * (instrument % 4);
    texture_y = texture_height * (instrument / 4);
  }

  @Override
  public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partial_ticks){
    if(visible){
      mc.getTextureManager().bindTexture(instruments_texture);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      // drawTexturedModalRect((float)xPosition, (float)yPosition, texture_x, texture_y, texture_x + texture_width, texture_y + texture_height);
      drawScaledCustomSizeModalRect(x, y, texture_x, texture_y, texture_width, texture_height, GuiMusicBox.instrument_button_width, GuiMusicBox.instrument_button_height, 256, 256);
    }
  }

  public final int getInstrument(){
    return instrument;
  }

}
