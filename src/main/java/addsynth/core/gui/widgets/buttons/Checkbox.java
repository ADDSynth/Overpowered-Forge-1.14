package addsynth.core.gui.widgets.buttons;

import addsynth.core.ADDSynthCore;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public abstract class Checkbox extends AbstractButton {

  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
  private static final int texture_x = 0;
  private static final int texture_y = 32;
  private static final int texture_width = 24;
  private static final int texture_height = 24;
  public static final int gui_size = 12;

  public Checkbox(final int x, final int y, String label){
    super(x, y, gui_size, gui_size, label);
  }

  protected abstract boolean get_toggle_state();

  @Override
  public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
    final boolean checked = get_toggle_state();
    WidgetUtil.common_button_render_setup(texture);
    blit(x, y, gui_size, gui_size, checked ? texture_x : texture_x + texture_height, texture_y, texture_width, texture_height, 256, 256);
    GuiUtil.draw_text_left(getMessage(), x + 16, y + 2);
  }

}
