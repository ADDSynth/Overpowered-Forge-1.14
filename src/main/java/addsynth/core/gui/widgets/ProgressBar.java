package addsynth.core.gui.widgets;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;

public class ProgressBar {

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int texture_x;
  private final int texture_y;

  public enum Direction {LEFT_TO_RIGHT,RIGHT_TO_LEFT,BOTTOM_TO_TOP,TOP_TO_BOTTOM}
  public enum Round {FLOOR, NEAREST, CEILING}

  public ProgressBar(int x, int y, int width, int height, int texture_x, int texture_y){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.texture_x = texture_x;
    this.texture_y = texture_y;
  }

  public void draw(ContainerScreen gui, Direction direction, float value, Round round_type){
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
    switch(direction){
    case LEFT_TO_RIGHT: gui.blit(gui.getGuiLeft() + x, gui.getGuiTop() + y, texture_x, texture_y, progress, height); break;
    case RIGHT_TO_LEFT: gui.blit(gui.getGuiLeft() + x + width - progress, gui.getGuiTop() + y, texture_x + width - progress, texture_y, progress, height); break;
    case TOP_TO_BOTTOM: gui.blit(gui.getGuiLeft() + x, gui.getGuiTop() + y, texture_x, texture_y, width, progress); break;
    case BOTTOM_TO_TOP: gui.blit(gui.getGuiLeft() + x, gui.getGuiTop() + y + height - progress, texture_x, texture_y + height - progress, width, progress); break;
    }
  }

}
