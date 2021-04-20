package addsynth.core.gui.widgets.sliders;

public final class VerticalSlider extends Slider {

  private final float step_size;

  public VerticalSlider(int x, int y, int width, int height, int min_value, int max_value, int default_value){
    super(x, y, width, height, min_value, max_value, default_value);
    this.step_size = this.height / this.steps;
  }

}
