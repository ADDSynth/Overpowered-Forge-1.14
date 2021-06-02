package addsynth.core.gameplay.team_manager.gui;

import java.util.function.Consumer;
import addsynth.core.ADDSynthCore;
import addsynth.core.gui.widgets.WidgetUtil;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class ColorButtons extends AbstractButton {

  private static final ResourceLocation color_buttons = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/color_buttons.png");

  public static final int button_gui_size = 16;
  private static final int gui_width = button_gui_size * 8;
  public static final int gui_height = button_gui_size * 2;
  
  private static final int button_texture_size = 24;
  // private static final int texture_width  = button_texture_size * 8;
  // private static final int texture_height = button_texture_size * 2;

  private int color;

  private int render_x;
  private int render_y;
  private int i;
  private int j;
  private int gui_x;
  private int gui_y;
  private int texture_x;
  private int texture_y;

  private final Consumer<Integer> responder;

  public ColorButtons(final int x_position, final int y_position){
    super(x_position, y_position, gui_width, gui_height, "");
    responder = null;
  }

  public ColorButtons(final int x_position, final int y_position, final Consumer<Integer> responder){
    super(x_position, y_position, gui_width, gui_height, "");
    this.responder = responder;
  }

  @Override
  public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
    WidgetUtil.common_button_render_setup(color_buttons);
    render_x = color % 8;
    render_y = color / 8;
    for(j = 0; j < 2; j++){
      for(i = 0; i < 8; i++){
        gui_x = x + (i * button_gui_size);
        gui_y = y + (j * button_gui_size);
        texture_x = (i * button_texture_size);
        texture_y = (j * button_texture_size) + (i == render_x && j == render_y ? button_texture_size*2 : 0);
        blit(gui_x, gui_y, button_gui_size, button_gui_size, texture_x, texture_y, button_texture_size, button_texture_size, 256, 256);
      }
    }
  }

  @Override
  public void onClick(double mouse_x, double mouse_y){
    color = WidgetUtil.getMouseInsideCell(x, y, (int)mouse_x, (int)mouse_y, button_gui_size, 8, 2);
  }

  @Override
  public void onPress(){
    if(responder != null){
      responder.accept(getColor());
    }
  }

  public final int getColor(){
    // White color in the top-left should be color 0, however the colors listed in
    // net.minecraft.util.text.TextFormatting are listed in reverse order.
    return 15 - color;
  }
  
  public final void setColor(int color){
    this.color = 15 - color;
  }

}
