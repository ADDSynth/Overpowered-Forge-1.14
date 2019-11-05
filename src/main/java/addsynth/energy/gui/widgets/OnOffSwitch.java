package addsynth.energy.gui.widgets;

import addsynth.core.ADDSynthCore;
import addsynth.energy.tiles.TileEnergyReceiver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Draws a custom button which displays an on/off switch depending on the Machine's running state.
 * Currently, we only use this to toggle the running state of an EnergyReceiver machine.
 */
public final class OnOffSwitch extends GuiButton {

  private final TileEnergyReceiver tile;
  private static final ResourceLocation gui_switch = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");

  /**
   * Call with guiLeft + standard x = 6 and guiTop + standard y = 17.
   * @param buttonId
   * @param x
   * @param y
   * @param tile
   */
  public OnOffSwitch(final int buttonId, final int x, final int y, final TileEnergyReceiver tile){
    super(buttonId, x, y, 34, 16, null);
    this.tile = tile;
  }

  /**
   * Draws the button depending on the running boolean of the TileEnergyReceiver, otherwise it contains the same code
   * as Vanilla draws a GuiButton.
   */
  @Override
  public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partial_ticks){
    if(this.visible)
    {
      int texture_y = 0;
      if(tile != null){
        if(tile.isRunning() == false){
          texture_y = 16;
        }
      }

      mc.getTextureManager().bindTexture(gui_switch);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      // this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
      // final int hover_state = this.getHoverState(this.hovered);
      
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      this.drawTexturedModalRect(x, y, 0, texture_y, width, height);

      final FontRenderer fontrenderer = mc.fontRenderer;
      final int text_color = 14737632;
      if(tile != null){
        if(tile.isRunning()){
          this.drawCenteredString(fontrenderer, "On", x + 20, y + 4, text_color);
        }
        else{
          this.drawCenteredString(fontrenderer, "Off", x + 14, y + 4, text_color);
        }
      }
      else{
        this.drawCenteredString(fontrenderer, "[null]", x + (this.width / 2), y + 4, text_color);
      }
    }
  }

}
