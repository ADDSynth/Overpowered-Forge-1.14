package addsynth.core.gui.objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public final class AdjustableButton extends GuiButton {

  public AdjustableButton(int buttonId, int x, int y, int width, int height, String buttonText) {
    super(buttonId, x, y, width, height, buttonText);
  }

    /**
     * Draws this button to the screen. This is my own overridden method. It auto adjusts for a variable width AND height.
     * Otherwise it is exactly the same as {@link GuiButton#drawButton}.
     */
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks){
      if(this.visible)
      {
        final int x = this.x;
        final int y = this.y;
        final int max_width = 200;
        final int max_height = 20;
        final int half_width = Math.min(this.width / 2, max_width / 2);
        final int half_height = Math.min(this.height / 2, max_height / 2);
        final int second_x = max_width - half_width;
        final int second_y = max_height - half_height;
        
        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        final int hover_state = this.getHoverState(this.hovered);
        final int button_texture_y = 46 + (hover_state * 20);
            
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        
        this.drawTexturedModalRect(x             , y              , 0       , button_texture_y           , half_width, half_height);
        this.drawTexturedModalRect(x + half_width, y              , second_x, button_texture_y           , half_width, half_height);
        this.drawTexturedModalRect(x             , y + half_height, 0       , button_texture_y + second_y, half_width, half_height);
        this.drawTexturedModalRect(x + half_width, y + half_height, second_x, button_texture_y + second_y, half_width, half_height);
        this.mouseDragged(mc, mouseX, mouseY);
        
        int text_color = 14737632;
        if (packedFGColour != 0){
          text_color = packedFGColour;
        }
        else{
          if (!this.enabled){
            text_color = 10526880;
          }
          else{
            if (this.hovered){
              text_color = 16777120;
            }
          }
        }

        this.drawCenteredString(mc.fontRenderer, this.displayString, x + half_width, y + half_height - 4, text_color);
      }
    }

}
