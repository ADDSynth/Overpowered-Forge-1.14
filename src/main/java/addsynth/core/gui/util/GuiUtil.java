package addsynth.core.gui.util;

import java.util.List;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.math.CommonMath;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/** <p>This contains all gui helper functions, used in all my Gui classes.
 *  <p>To use, you must initialize a field variable with a new GuiUtil instance
 *     in your gui's <code>init()</code> method after the call to <code>super.init()</code>.
 * @author ADDSynth
 */
public final class GuiUtil {

  public static final int text_color = 4210752;

  public static final Minecraft      minecraft      = Minecraft.getInstance();
  public static final TextureManager textureManager = minecraft.textureManager;
  public static final FontRenderer   font           = minecraft.fontRenderer;
  public static final ItemRenderer   itemRenderer   = minecraft.getItemRenderer();

  private final ResourceLocation GUI_TEXTURE;
  public final int guiWidth;
  public final int guiHeight;

  /** Left edge of gui. For use in drawing graphics, NOT text! */
  public final int guiLeft;
  /** Right edge of gui. For use in drawing graphics, NOT text! */
  public final int guiRight;
  /** Center of gui. For use in drawing graphics, NOT text! */
  public final int guiCenter;
  /** Top edge of gui. For use in drawing graphics, NOT text! */
  public final int guiTop;


  /** center of gui. Used for drawing text. */
  public final int center_x;
  /** equivalent to xSize - 6. Use for drawing text. */
  public final int right_edge;

  public GuiUtil(final ResourceLocation gui_texture, final int gui_width, final int gui_height){
    GUI_TEXTURE = gui_texture;
    guiWidth    = gui_width;
    guiHeight   = gui_height;
    guiLeft     = (minecraft.mainWindow.getScaledWidth()  - gui_width)  / 2;
    guiTop      = (minecraft.mainWindow.getScaledHeight() - gui_height) / 2;
    guiRight    = guiLeft + gui_width;
    center_x    = gui_width / 2;
    guiCenter   = guiLeft + center_x;
    right_edge  = gui_width - 6;
  }

// ========================================================================================================
  
  /** Draws entire gui texture. (at default texture width and height of 256x256.) */
  public void draw_background_texture(){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    textureManager.bindTexture(GUI_TEXTURE);
    AbstractGui.blit(guiLeft, guiTop, guiWidth, guiHeight, 0, 0, guiWidth, guiHeight, 256, 256);
  }

  /** Draws the background texture with custom scaled width and height. Use this
   *  if you have a background texture that is not the default size of 256x256.
   * @param texture_width
   * @param texture_height
   */
  public void draw_custom_background_texture(final int texture_width, int texture_height){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    textureManager.bindTexture(GUI_TEXTURE);
    AbstractGui.blit(guiLeft, guiTop, guiWidth, guiHeight, 0, 0, guiWidth, guiHeight, texture_width, texture_height);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  This draws the texture portion the same size that is drawn on the gui.
   *  Assumes a default texture size of 256x256. */
  public void draw(final int x, final int y, final int u, final int v, final int width, final int height){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    textureManager.bindTexture(GUI_TEXTURE);
    AbstractGui.blit(guiLeft + x, guiTop + y, width, height, u, v, width, height, 256, 256);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  If you need more control over how it's drawn, you might as well use the
   *  vanilla blit() function. */
  public void draw(int x, int y, int u, int v, int width, int height, int texture_width, int texture_height){
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    textureManager.bindTexture(GUI_TEXTURE);
    AbstractGui.blit(guiLeft + x, guiTop + y, width, height, u, v, texture_width, texture_height, 256, 256);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  @deprecated This doesn't work right! Transparency fluctuates as you mouse over other itmes and create Tooltip popups!
   */
  @Deprecated
  public void draw_transparent(int x, int y, int u, int v, int width, int height, float opacity){
    draw_transparent(x, y, u, v, width, height, width, height, opacity);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  @deprecated This doesn't work right! Transparency fluctuates as you mouse over other itmes and create Tooltip popups!
   */
  @Deprecated
  public void draw_transparent(int x, int y, int u, int v, int width, int height, int texture_width, int texture_height, float opacity){
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, opacity);
    textureManager.bindTexture(GUI_TEXTURE);
    AbstractGui.blit(x, y, width, height, u, v, texture_width, texture_height, 256, 256);
  }

// ========================================================================================================
  
  public final void draw_title(final ITextComponent title){
    draw_text_center(title.getString(), center_x, 6);
  }

  public static final int getMaxStringWidth(final String ... text){
    final int[] width = new int[text.length];
    int i;
    for(i = 0; i < text.length; i++){
      width[i] = font.getStringWidth(text[i]);
    }
    return CommonMath.getMax(width);
  }

  /** This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  public static final void draw_text_left(final String text, final int x, final int y){
    font.drawString(text, x, y, text_color);
  }

  /** Draws center-aligned text at the center of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  public final void draw_text_center(final String text, final int y){
    font.drawString(text, center_x - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>.
   * @see net.minecraft.client.gui.AbstractGui#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  public final static void draw_text_center(final String text, final int x, final int y){
    font.drawString(text, x - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Draws along the right-edge of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  public final void draw_text_right(final String text, final int y){
    font.drawString(text, right_edge - font.getStringWidth(text), y, text_color);
  }

  /** This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  public static final void draw_text_right(final String text, final int x, final int y){
    font.drawString(text, x - font.getStringWidth(text), y, text_color);
  }

// ========================================================================================================

  /** If the rendered Item isn't lighted correctly, call:<br />
   *  <code>RenderHelper.enableGUIStandardItemLighting();</code><br />
   *  before calling this function!
   * @param stack
   * @param x
   * @param y
   */
  public static final void drawItemStack(final ItemStack stack, final int x, final int y){
    itemRenderer.renderItemIntoGUI(stack, x, y);
  }

  /** If the rendered Item isn't lighted correctly, call:<br />
   *  <code>RenderHelper.enableGUIStandardItemLighting();</code><br />
   *  before calling this function!
   * @param stack
   * @param x
   * @param y
   * @param opacity
   */
  public static final void drawItemStack(final ItemStack stack, final int x, final int y, final float opacity){
    RenderUtil.drawItemStack(itemRenderer, textureManager, stack, x, y, opacity);
  }

  /** <p>Use this to draw 2 ItemStacks but at a linear opacity between the two.
   *  <p>This function draws the first ItemStack first, then the second. With a
   *     <code>blend_factor</code> of <code>0.0f</code>, the first stack is at 100% opacity,
   *     while the second stack is invisible. With the <code>blend_factor</code> at
   *     <code>1.0f</code>, the first stack is invisible and the second stack is fully drawn.
   * @param first_stack
   * @param second_stack
   * @param x
   * @param y
   * @param blend_factor
   */
  public static final void blendItemStacks(ItemStack first_stack, ItemStack second_stack, int x, int y, float blend_factor){
    drawItemStack( first_stack, x, y, 1.0f - blend_factor);
    drawItemStack(second_stack, x, y,        blend_factor);
  }

  /** This must be called in the {@link ContainerScreen#renderHoveredToolTip(int, int)} method.<br>
   *  The X and Y coordinates must have the <code>guiLeft</code> and <code>guiTop</code> values added. */
  // REPLICA of Screen.renderTooltip(ItemStack, mouse_x, mouse_y);
  public static final void drawItemTooltip(Screen screen, ItemStack itemStack, int x, int y, int mouse_x, int mouse_y){
    if(WidgetUtil.isInsideItemStack(x, y, mouse_x, mouse_y)){
      @SuppressWarnings("resource")
      final FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
      final List<String> text = screen.getTooltipFromItem(itemStack);
      net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(itemStack);
      screen.renderTooltip(text, mouse_x, mouse_y, font != null ? font : GuiUtil.font);
      net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
    }
  }

}
