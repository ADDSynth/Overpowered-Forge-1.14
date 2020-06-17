package addsynth.core.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiBase<T extends Container> extends ContainerScreen<T> {

  protected static final int text_color = 4210752;
  protected TextureManager textureManager;

  private final ResourceLocation GUI_TEXTURE;
  
  /** This variable can't be used when drawing text, but CAN be used when drawing textures or buttons. */
  protected int guiRight;
  /** center of gui. Used for drawing text. */
  protected final int center_x;
  /** equivalent to xSize - 6. Use for drawing text. */
  protected final int right_edge;

  public GuiBase(final T container, final PlayerInventory player_inventory, final ITextComponent title, final ResourceLocation gui_texture){
    this(-1, -1, container, player_inventory, title, gui_texture);
  }

  public GuiBase(int width, int height, T container, PlayerInventory player_inventory, ITextComponent title, ResourceLocation gui_texture){
    super(container, player_inventory, title);
    GUI_TEXTURE = gui_texture;
    if(width  > 0){ this.xSize = width; }
    if(height > 0){ this.ySize = height; }
    center_x = xSize / 2;
    right_edge = xSize - 6;
  }

  @Override
  @SuppressWarnings("null")
  public void init(){
    super.init();
    textureManager = this.minecraft.getTextureManager();
    guiRight = guiLeft + xSize; // the guiLeft variable isn't set up until we call super.init()!
  }

  @Override
  public void render(final int mouseX, final int mouseY, final float partialTicks){
    this.renderBackground();
    super.render(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
  }

// ========================================================================================================

  /**
   * Well, this must be here because this extends the GuiContainer class, which marks this method as abstract.
   */
  @Override
  protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
  }

  /** Draws entire gui texture. (at default texture width and height of 256x256.) */
  protected void draw_background_texture(){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    textureManager.bindTexture(GUI_TEXTURE);
    this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }

  /** <p>Draws the background texture with custom width and height. Use this if you have a
   *     background texture that is not the default size of 256x256.
   *  <p>Draws texture at z level = 0, which is what it normally draws at anyway.
   * @param texture_width
   * @param texture_height
   */
  protected void draw_custom_background_texture(final int texture_width, int texture_height){
    draw(0, 0, 0, 0, this.xSize, this.ySize, texture_width, texture_height);
  }

  /** Draws another part of the gui texture at the coordinates you specify. */
  protected void draw(final int x, final int y, final int u, final int v, final int width, final int height){
    draw(x, y, u, v, width, height, width, height);
  }

  /** Draws another part of the gui texture at the coordinates you specify. */
  protected void draw(int x, int y, int u, int v, int width, int height, int texture_width, int texture_height){
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    textureManager.bindTexture(GUI_TEXTURE);
    blit(this.guiLeft + x, this.guiTop + y, width, height, u, v, width, height, texture_width, texture_height);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  @deprecated This doesn't work right! Transparency fluctuates as you mouse over other itmes and create Tooltip popups!
   */
  @Deprecated
  protected void draw_transparent(int x, int y, int u, int v, int width, int height, float opacity){
    draw_transparent(x, y, u, v, width, height, width, height, opacity);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  @deprecated This doesn't work right! Transparency fluctuates as you mouse over other itmes and create Tooltip popups!
   */
  @Deprecated
  protected void draw_transparent(int x, int y, int u, int v, int width, int height, int texture_width, int texture_height, float opacity){
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, opacity);
    textureManager.bindTexture(GUI_TEXTURE);
    blit(x, y, width, height, u, v, texture_width, texture_height, 256, 256);
  }

// ========================================================================================================

  protected final void draw_title(){
    draw_text_center(title.getString(), center_x, 6);
  }

  /** This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  protected final void draw_text_left(final String text, final int x, final int y){
    font.drawString(text, x, y, text_color);
  }

  /** Draws center-aligned text at the center of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  protected final void draw_text_center(final String text, final int y){
    font.drawString(text, center_x - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>.
   * @see net.minecraft.client.gui.Gui#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  protected final void draw_text_center(final String text, final int x, final int y){
    font.drawString(text, x - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Draws along the right-edge of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  protected final void draw_text_right(final String text, final int y){
    font.drawString(text, right_edge - font.getStringWidth(text), y, text_color);
  }

  /** This will render the string in a different color if you prefix the string with
   *  <code>TextFormatting.COLOR.toString()</code>. */
  protected final void draw_text_right(final String text, final int x, final int y){
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
  protected final void drawItemStack(final ItemStack stack, final int x, final int y){
    this.itemRenderer.renderItemIntoGUI(stack, x, y);
  }

  /** If the rendered Item isn't lighted correctly, call:<br />
   *  <code>RenderHelper.enableGUIStandardItemLighting();</code><br />
   *  before calling this function!
   * @param stack
   * @param x
   * @param y
   * @param opacity
   */
  protected final void drawItemStack(final ItemStack stack, final int x, final int y, final float opacity){
    RenderUtil.drawItemStack(this.itemRenderer, this.textureManager, stack, x, y, opacity);
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
  protected final void blendItemStacks(ItemStack first_stack, ItemStack second_stack, int x, int y, float blend_factor){
    drawItemStack( first_stack, x, y, 1.0f - blend_factor);
    drawItemStack(second_stack, x, y,        blend_factor);
  }

}
