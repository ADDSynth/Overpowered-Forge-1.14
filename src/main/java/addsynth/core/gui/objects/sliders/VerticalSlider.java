package addsynth.core.gui.objects.sliders;

public final class VerticalSlider extends Slider {

  private final float step_size;

  public VerticalSlider(int id, int x, int y, int width, int height, int min_value, int max_value, int default_value) {
    super(id, x, y, width, height, min_value, max_value, default_value);
    this.step_size = this.height / this.steps;
  }

}
