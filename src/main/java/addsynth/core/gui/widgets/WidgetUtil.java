package addsynth.core.gui.widgets;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.math.MathUtility;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public final class WidgetUtil {

  private static final Minecraft minecraft = Minecraft.getInstance();
  // private static final FontRenderer font = minecraft.fontRenderer;
  private static final TextureManager texture_manager = minecraft.getTextureManager();
  
// ======================================= WIDGET POSITIONING =========================================
  
  /** I often need to draw widgets a certain way, by drawing different parts seperately.
   *  However, if you need to draw two halves of a button that is 61 pixels, it won't work,
   *  because the two halves will be 30 and 30. So this function returns [half, total-half].
   * @param total_size
   */
  public static final int[] get_half_lengths(final int total_size){
    final int half_size = total_size / 2;
    return new int[] {half_size, total_size - half_size};
  }
  
  public static final int get_first_half(final int total_size){
    return total_size / 2;
  }
  
  public static final int get_second_half(final int total_size){
    return total_size - (total_size / 2);
  }
  
  public static final int[] evenAlignment(final int total_area, final int[] widget_sizes){
    final int widgets = widget_sizes.length;
    final int[] widget_position = new int[widgets];
    int widget_total_size = 0;
    int i;
    for(i = 0; i < widgets; i++){
      widget_total_size += widget_sizes[i];
    }
    int total_space = total_area - widget_total_size;
    int j;
    if(total_space >= 0){
      final int[] space = MathUtility.divide_evenly(total_space, widgets+1);
      for(i = 0; i < widgets; i++){
        widget_position[i] = space[i];
        for(j = 0; j < i; j++){
          widget_position[i] += space[j] + widget_sizes[j];
        }
      }
      return widget_position;
    }
    ADDSynthCore.log.warn(WidgetUtil.class.getName()+".evenAlignment() has aligned gui widgets with negative spacing. Everything still works okay, but the gui probably looks bad. This is most likely a programming error and should not have happened.");
    final int[] space = MathUtility.divide_evenly(total_space, widgets-1);
    for(i = 1; i < widgets; i++){
      widget_position[i] = 0;
      for(j = 0; j < i; j++){
        widget_position[i] += space[j] + widget_sizes[j];
      }
    }
    return widget_position;
  }

  public static final int[] evenAlignment(final int total_area, final int widget_size, final int number_of_widgets){
    int[] widget_sizes = new int[number_of_widgets];
    int z;
    for(z = 0; z < number_of_widgets; z++){
      widget_sizes[z] = widget_size;
    }
    return evenAlignment(total_area, widget_sizes);
    /* OPTIMIZE: Give this method its own algorithm that uses the same size for all widgets.
    final int[] widget_position = new int[number_of_widgets];
    final int total_space = total_area - (widget_size*number_of_widgets);
    int[] space = new int[number_of_widgets];
    if(total_space > 0){
      space = MathUtility.divide_evenly(total_space, number_of_widgets+1);
    }
    int i;
    int j;
    for(i = 0; i < number_of_widgets; i++){
      for(j = 0; j < i; j++){
      }
    }
    return widget_position;
    */
  }
  
  /** Use this whenever you need to draw text beside a widget. This will return the Y position
   *  where you need to draw the text. But remember, widgets are drawn relative to the screen,
   *  not the gui, so you may need to subtract guiHeight, if you've previously added it.
   * @param widget_y
   * @param widget_height
   */
  public static final int centerYAdjacent(final int widget_y, final int widget_height){
    return widget_y + ((widget_height - 8) / 2);
  }

// ========================================= BUTTON RENDER ===========================================
  
  public static final void common_button_render_setup(ResourceLocation texture){
    common_button_render_setup(texture, 1.0f);
  }
  
  public static final void common_button_render_setup(ResourceLocation texture, float alpha){
    texture_manager.bindTexture(texture);
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
  }
  
  /** Call this to draw a button texture, that has its own texture, and you don't need to draw any text.
   * 
   * @param widget
   * @param texture
   * @param texture_x
   * @param texture_y
   * @param width
   * @param height
   */
  public static final void renderButton(Widget widget, ResourceLocation texture, int texture_x, int texture_y, int width, int height){
    common_button_render_setup(texture); // widget.alpha is not visible
    widget.blit(widget.x, widget.y, texture_x, texture_y, width, height);
  }

  /** Call this to draw your own button texture, and you don't need to draw any text.
   *  Assumes a texture size of 256x256.
   * @param widget
   * @param texture
   * @param texture_x
   * @param texture_y
   * @param width
   * @param height
   * @param texture_width
   * @param texture_height
   */
  public static final void renderButton(Widget widget, ResourceLocation texture, int texture_x, int texture_y, int width, int height, int texture_width, int texture_height){
    common_button_render_setup(texture);
    AbstractGui.blit(widget.x, widget.y, width, height, texture_x, texture_y, texture_width, texture_height, 256, 256);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into top and bottom parts. */
  public static final void verticalSplitRender(int gui_x, int gui_y, int texture_x, int texture_y, int width, int height, int max_height){
    final int[] half_height = get_half_lengths(Math.min(height, max_height));
    final int     gui_y2 = gui_y + half_height[0];
    final int texture_y2 = texture_y + max_height - half_height[1];
    AbstractGui.blit(gui_x, gui_y,  width, half_height[0], texture_x, texture_y,  width, half_height[0], 256, 256);
    AbstractGui.blit(gui_x, gui_y2, width, half_height[1], texture_x, texture_y2, width, half_height[1], 256, 256);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into top and bottom parts. */
  public static final void verticalSplitRender(Dimensions gui, Dimensions texture){
    verticalSplitRender(gui.x, gui.y, texture.x, texture.y, gui.width, gui.height, texture.max_height);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into top and bottom parts. */
  public static final void verticalSplitRender(Dimensions gui, Dimensions texture, int scale_width, int scale_height){
    final int[]     gui_half_height = get_half_lengths(gui.height);
    final int[] texture_half_height = get_half_lengths(Math.min(texture.height, texture.max_height));
    final int     gui_y2 = gui.y + gui_half_height[0];
    final int texture_y2 = texture.y + texture.max_height - texture_half_height[1];
    AbstractGui.blit(gui.x, gui.y,  gui.width, gui_half_height[0], texture.x, texture.y,  texture.width, texture_half_height[0], scale_width, scale_height);
    AbstractGui.blit(gui.x, gui_y2, gui.width, gui_half_height[1], texture.x, texture_y2, texture.width, texture_half_height[1], scale_width, scale_height);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into left and right parts. */
  public static final void horizontalSplitRender(int gui_x, int gui_y, int texture_x, int texture_y, int width, int height, int max_width){
    final int[] half_width = get_half_lengths(Math.min(width, max_width));
    final int     gui_x2 = gui_x + half_width[0];
    final int texture_x2 = texture_x + max_width - half_width[1];
    AbstractGui.blit(gui_x,  gui_y, half_width[0], height, texture_x,  texture_y, half_width[0], height, 256, 256);
    AbstractGui.blit(gui_x2, gui_y, half_width[1], height, texture_x2, texture_y, half_width[1], height, 256, 256);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into left and right parts. */
  public static final void horizontalSplitRender(Dimensions gui, Dimensions texture){
    horizontalSplitRender(gui.x, gui.y, texture.x, texture.y, gui.width, gui.height, texture.max_width);
  }
  
  /** Used to render a larger texture into a smaller space, by splitting the texture into left and right parts. */
  public static final void horizontalSplitRender(Dimensions gui, Dimensions texture, int scale_width, int scale_height){
    final int[]     gui_half_width = get_half_lengths(gui.width);
    final int[] texture_half_width = get_half_lengths(Math.min(texture.width, texture.max_width));
    final int     gui_x2 = gui.x + gui_half_width[0];
    final int texture_x2 = texture.x + texture.max_width - texture_half_width[1];
    AbstractGui.blit(gui.x,  gui.y, gui_half_width[0], gui.height, texture.x,  texture.y, texture_half_width[0], texture.height, scale_width, scale_height);
    AbstractGui.blit(gui_x2, gui.y, gui_half_width[1], gui.height, texture_x2, texture.y, texture_half_width[1], texture.height, scale_width, scale_height);
  }
  
  public static final void crossSplitRender(int gui_x, int gui_y, int texture_x, int texture_y, int width, int height, int max_width, int max_height){
    final int[] half_width  = WidgetUtil.get_half_lengths(Math.min(width,  max_width));
    final int[] half_height = WidgetUtil.get_half_lengths(Math.min(height, max_height));
    final int gui_x2 = gui_x + half_width[0];
    final int gui_y2 = gui_y + half_height[0];
    final int texture_x2 = texture_x + max_width  - half_width[1];
    final int texture_y2 = texture_y + max_height - half_height[1];
    AbstractGui.blit(gui_x,  gui_y,  half_width[0], half_height[0], texture_x,  texture_y,  half_width[0], half_height[0], 256, 256);
    AbstractGui.blit(gui_x2, gui_y,  half_width[1], half_height[0], texture_x2, texture_y,  half_width[1], half_height[0], 256, 256);
    AbstractGui.blit(gui_x,  gui_y2, half_width[0], half_height[1], texture_x,  texture_y2, half_width[0], half_height[1], 256, 256);
    AbstractGui.blit(gui_x2, gui_y2, half_width[1], half_height[1], texture_x2, texture_y2, half_width[1], half_height[1], 256, 256);
  }
  
  public static final void crossSplitRender(Dimensions gui, Dimensions texture){
    crossSplitRender(gui.x, gui.y, texture.x, texture.y, gui.width, gui.height, texture.max_width, texture.max_height);
  }

  public static final void crossSplitRender(Dimensions gui, Dimensions texture, int scale_width, int scale_height){
    final int[]     gui_half_width  = get_half_lengths(gui.width);
    final int[]     gui_half_height = get_half_lengths(gui.height);
    final int[] texture_half_width  = get_half_lengths(texture.width);
    final int[] texture_half_height = get_half_lengths(texture.height);
    final int     gui_x2 = gui.x + gui_half_width[0];
    final int     gui_y2 = gui.y + gui_half_height[0];
    final int texture_x2 = texture.x + texture.max_width  - texture_half_width[1];
    final int texture_y2 = texture.y + texture.max_height - texture_half_height[1];
    AbstractGui.blit(gui.x,  gui.y,  gui_half_width[0], gui_half_height[0], texture.x,  texture.y,  texture_half_width[0], texture_half_height[0], scale_width, scale_height);
    AbstractGui.blit(gui_x2, gui.y,  gui_half_width[1], gui_half_height[0], texture_x2, texture.y,  texture_half_width[1], texture_half_height[0], scale_width, scale_height);
    AbstractGui.blit(gui.x,  gui_y2, gui_half_width[0], gui_half_height[1], texture.x,  texture_y2, texture_half_width[0], texture_half_height[1], scale_width, scale_height);
    AbstractGui.blit(gui_x2, gui_y2, gui_half_width[1], gui_half_height[1], texture_x2, texture_y2, texture_half_width[1], texture_half_height[1], scale_width, scale_height);
  }

// ==================================== DETECT MOUSE COORDINATES ========================================

  /** <p>Use this to set the isHovered state of Buttons.
   *  Mouse X and Y are the first two arguments to the render() function.
   *  <p><b>Note:</b> As long as you don't override the render() method, Minecraft already determines the isHovered state for you.
   * @param mouse_x
   * @param mouse_y
   * @param widget
   */
  public static final boolean isInside(final int mouse_x, final int mouse_y, final Widget widget){
    return mouse_x >= widget.x                   && mouse_y >= widget.y &&
           mouse_x <  widget.x+widget.getWidth() && mouse_y <  widget.y+widget.getHeight();
  }

  public static final boolean isInsideItemStack(final int x, final int y, final int mouse_x, final int mouse_y){
    return mouse_x >= x && mouse_x < x+16 && mouse_y >= y && mouse_y < y+16;
  }

  /** Used with custom buttons that must change a value depending on where the player clicked on the button.
   *  The button is divided into evenly sized cells, starting with cell 0 at the top-left corner.
   * @param mouse_x
   * @param mouse_y
   * @param cell_size
   * @param horizontal_cells
   * @param vertical_cells
   */
  public static final int getMouseInsideCell(final int mouse_x, final int mouse_y, final int cell_size, final int horizontal_cells, final int vertical_cells){
    return  (mouse_x / cell_size) +
           ((mouse_y / cell_size) * horizontal_cells);
  }

  /** Used with custom buttons that change a value depending on where on the button the player clicked.
   *  The button is divided into cells, with cell 0 starting at the top-left corner.
   * @param mouse_x
   * @param mouse_y
   * @param horizontal_cell_size
   * @param vertical_cell_size
   * @param horizontal_cells
   * @param vertical_cells
   */
  public static final int getMouseInsideCell(int mouse_x, int mouse_y, int horizontal_cell_size, int vertical_cell_size, int horizontal_cells, int vertical_cells){
    return  (mouse_x / horizontal_cell_size) +
           ((mouse_y / vertical_cell_size) * horizontal_cells);
  }

  /** Used with custom buttons that must change a value depending on where the player clicked on the button.
   *  The button is divided into evenly sized cells, starting with cell 0 at the top-left corner.
   * @param x
   * @param y
   * @param mouse_x
   * @param mouse_y
   * @param cell_size
   * @param horizontal_cells
   * @param vertical_cells
   */
  public static final int getMouseInsideCell(final int x, final int y, final int mouse_x, final int mouse_y, final int cell_size, final int horizontal_cells, final int vertical_cells){
    return  ((mouse_x - x) / cell_size) +
           (((mouse_y - y) / cell_size) * horizontal_cells);
  }

  /** Used with custom buttons that change a value depending on where on the button the player clicked.
   *  The button is divided into cells, with cell 0 starting at the top-left corner.
   * @param x
   * @param y
   * @param mouse_x
   * @param mouse_y
   * @param horizontal_cell_size
   * @param vertical_cell_size
   * @param horizontal_cells
   * @param vertical_cells
   */
  public static final int getMouseInsideCell(int x, int y, int mouse_x, int mouse_y, int horizontal_cell_size, int vertical_cell_size, int horizontal_cells, int vertical_cells){
    return  ((mouse_x - x) / horizontal_cell_size) +
           (((mouse_y - y) / vertical_cell_size) * horizontal_cells);
  }

}
