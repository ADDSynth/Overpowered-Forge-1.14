package addsynth.energy.lib.gui.widgets;

import addsynth.core.gui.widgets.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.core.util.math.RoundMode;
import addsynth.energy.lib.tiles.machines.TileAbstractWorkMachine;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;

/** A specialized ProgressBar for use in drawing work time. To use properly, you must
 *  call the draw() function in the drawBackground() method, and then you can call
 *  getWorkTimeProgress() to draw the work time percentage in the drawForeground() method.
 */
public final class WorkProgressBar extends ProgressBar {

  private float work_time;

  public WorkProgressBar(int x, int y, int width, int height, int texture_x, int texture_y){
    super(x, y, width, height, texture_x, texture_y);
  }

  public final void draw(ContainerScreen gui, TileAbstractWorkMachine tile){
    work_time = tile.getWorkTimePercentage();
    super.draw(gui, Direction.LEFT_TO_RIGHT, work_time, RoundMode.Floor);
  }

  public final void draw(ContainerScreen gui, Direction direction, TileAbstractWorkMachine tile){
    work_time = tile.getWorkTimePercentage();
    super.draw(gui, direction, work_time, RoundMode.Floor);
  }

  public final float getWorkTime(){
    return work_time;
  }

  public final String getWorkTimeProgress(){
    return ((int)Math.floor(work_time*100)) + "%";
  }

  /** Use this if your gui has no intention of drawing a progress bar. */
  public static final String getWorkTimeProgress(TileAbstractWorkMachine tile){
    return StringUtil.toPercentageString(tile.getWorkTimePercentage(), RoundMode.Floor);
  }

}
