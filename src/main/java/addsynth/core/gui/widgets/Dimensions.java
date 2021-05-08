package addsynth.core.gui.widgets;

public final class Dimensions {

  public final int x;
  public final int y;
  public final int width;
  public final int height;
  public final int max_width;
  public final int max_height;
  // public final int scale_x;
  // public final int scale_y;

  public Dimensions(int x, int y, int width, int height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.max_width = width;
    this.max_height = height;
    // this.scale_x = 256;
    // this.scale_y = 256;
  }
  
  /*
  public Dimensions(int x, int y, int width, int height, int texture_scale_x, int texture_scale_y){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.max_width = width;
    this.max_height = height;
    this.scale_x = texture_scale_x;
    this.scale_y = texture_scale_y;
  }
  */

  public Dimensions(int x, int y, int width, int height, int max_width, int max_height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.max_width = max_width;
    this.max_height = max_height;
  }

  /*
  public Dimensions(int x, int y, int width, int height, int max_width, int max_height, int texture_scale_x, int texture_scale_y){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.max_width = max_width;
    this.max_height = max_height;
    this.scale_x = texture_scale_x;
    this.scale_y = texture_scale_y;
  }
  */

}
