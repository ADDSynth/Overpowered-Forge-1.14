package addsynth.core.gui.objects;

import net.minecraft.client.gui.inventory.GuiContainer;

public class ProgressBar {

  // private final ResourceLocation texture;
  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int replace_x;
  private final int replace_y;

  public enum Direction {LEFT_TO_RIGHT,RIGHT_TO_LEFT,BOTTOM_TO_TOP,TOP_TO_BOTTOM}
  public enum Round {FLOOR, NEAREST, CEILING}

  public ProgressBar(int x, int y, int width, int height, int replace_x, int replace_y) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.replace_x = replace_x;
    this.replace_y = replace_y;
  }
/*
  public ProgressBar(ResourceLocation gui_texture, int x, int y, int width, int height, int replace_x, int replace_y) {
    this.texture = gui_texture;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.replace_x = replace_x;
    this.replace_y = replace_y;
  }
*/
  public void draw(GuiContainer gui, int gui_left, int gui_top, Direction direction, float value, Round round_type){
    float texture_percentage = 0.0f;
    switch(direction){
    case LEFT_TO_RIGHT: case RIGHT_TO_LEFT: texture_percentage = width  * value; break;
    case BOTTOM_TO_TOP: case TOP_TO_BOTTOM: texture_percentage = height * value; break;
    }
    int progress = 0;
    switch(round_type){
    case FLOOR: progress = (int)Math.floor(texture_percentage); break;
    case NEAREST: progress = Math.round(texture_percentage); break;
    case CEILING: progress = (int)Math.ceil(texture_percentage); break;
    }
    // gui.mc.renderEngine.bindTexture(texture);
    switch(direction){
    case LEFT_TO_RIGHT: gui.drawTexturedModalRect(gui_left + x, gui_top + y, replace_x, replace_y, progress, height); break;
    case RIGHT_TO_LEFT: gui.drawTexturedModalRect(gui_left + x + width - progress, gui_top + y, replace_x + width - progress, replace_y, progress, height); break;
    case TOP_TO_BOTTOM: gui.drawTexturedModalRect(gui_left + x, gui_top + y, replace_x, replace_y, width, progress); break;
    case BOTTOM_TO_TOP: gui.drawTexturedModalRect(gui_left + x, gui_top + y + height - progress, replace_x, replace_y + height - progress, width, progress); break;
    }
  }

}
