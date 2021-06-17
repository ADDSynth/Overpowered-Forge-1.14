package addsynth.core.gui.widgets;

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
  public static final void verticalSplitRender(Dimensions gui, Dimensions texture){
    verticalSplitRender(gui, texture, 256, 256);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into top and bottom parts. */
  public static final void verticalSplitRender(Dimensions gui, Dimensions texture, int scale_width, int scale_height){
    final int gui_half_height = Math.min(gui.height, gui.max_height) / 2;
    final int texture_half_height = Math.min(texture.height, texture.max_height) / 2;
    AbstractGui.blit(gui.x, gui.y,                   gui.width, gui_half_height, texture.x, texture.y,                                            texture.width, texture_half_height, scale_width, scale_height);
    AbstractGui.blit(gui.x, gui.y + gui_half_height, gui.width, gui_half_height, texture.x, texture.y + texture.max_height - texture_half_height, texture.width, texture_half_height, scale_width, scale_height);
  }

  /** Used to render a larger texture into a smaller space, by splitting the texture into left and right parts. */
  public static final void horizontalSplitRender(Dimensions gui, Dimensions texture){
    horizontalSplitRender(gui, texture, 256, 256);
  }
  
  /** Used to render a larger texture into a smaller space, by splitting the texture into left and right parts. */
  public static final void horizontalSplitRender(Dimensions gui, Dimensions texture, int scale_width, int scale_height){
    final int gui_half_width = Math.min(gui.width, gui.max_width) / 2;
    final int texture_half_width = Math.min(texture.width, texture.max_width) / 2;
    AbstractGui.blit(gui.x,                  gui.y, gui_half_width, gui.height, texture.x,                                          texture.y, texture_half_width, texture.height, scale_width, scale_height);
    AbstractGui.blit(gui.x + gui_half_width, gui.y, gui_half_width, gui.height, texture.x + texture.max_width - texture_half_width, texture.y, texture_half_width, texture.height, scale_width, scale_height);
  }
  
  public static final void crossSplitRender(Dimensions gui, Dimensions texture){
    // TODO: If we need this again, copy it from AdjustableButton.
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
