package addsynth.core.gui.objects.sliders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/** 
 *  Using sliders, there will be a button click sound. To solve this, override the onMouseClicked() method,
 *  loop through all the objects in buttonList, get the GuiButton objects, then check if they are an instance
 *  of this Slider class. otherwise call this.actionPerformed() method.
 */
public abstract class Slider extends GuiButton {

  private final int min_value;
  private final int max_value;
  private final int default_value;
  private final int current_value;
  protected final int steps;
  
  // private static final int draw_x;
  // private static final int draw_y;

  public Slider(int id, int x, int y, int width, int height, int min_value, int max_value, int default_value) {
    super(id, x, y, width, height, null);
    this.min_value = min_value;
    this.max_value = max_value;
    this.default_value = default_value;
    this.current_value = default_value;
    this.steps = Math.min(max_value - min_value, this.height);
  }

  @Override
  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    
  }

  public final int get_value(){
    return current_value;
  }

}
