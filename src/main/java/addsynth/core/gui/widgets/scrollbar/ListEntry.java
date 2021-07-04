package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import addsynth.core.util.color.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class ListEntry extends AbstractButton {

  private final ResourceLocation highlight_texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/gui_textures.png");
  private final int texture_x = 0;
  private final int texture_y = 224;

  private int entry_id;
  // private String text; // not much of a performance benefit
  private boolean selected;
  private Scrollbar responder;

  public ListEntry(int x, int y, int width, int height){
    super(x, y, width, height, "");
  }

  public void setScrollbar(final Scrollbar scrollbar){
    this.responder = scrollbar;
  }

  @Override
  @SuppressWarnings("resource")
  public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
    Minecraft minecraft = Minecraft.getInstance();
    FontRenderer fontrenderer = minecraft.fontRenderer;
    minecraft.getTextureManager().bindTexture(highlight_texture);
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    if((isHovered && StringUtil.StringExists(getMessage())) || selected){
      blit(x, y, texture_x, texture_y, width, height);
    }
    this.drawString(fontrenderer, getMessage(), this.x + 1, this.y + 1, Color.WHITE.get());
  }

  @Override
  public void onPress(){
    if(StringUtil.StringExists(getMessage())){
      responder.setSelected(entry_id, true, false);
    }
  }

  public void set(final int entry_id, final String message){
    this.entry_id = entry_id;
    // this.text = message;
    setMessage(message);
  }

  /** Do not use this to set the selected entry. Use Scrollbar.setSelected(). */
  public void setSelected(final int selected_entry_id){
    this.selected = selected_entry_id >= 0 && entry_id == selected_entry_id;
  }

  @Override
  public void playDownSound(SoundHandler p_playDownSound_1_){
  }

}
