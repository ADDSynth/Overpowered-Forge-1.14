package addsynth.core.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 *  <b>Note:</b>
 *  the {@link Screen#drawScreen(int, int, float)} function (where background, forground, and
 *  buttons is being drawed in) is called in the
 *  {@link net.minecraft.client.renderer.EntityRenderer#updateCameraAndRender} function which
 *  is called every frame in the {@link Minecraft#runGameLoop()} function.<br>
 *  The {@link Screen#updateScreen()} function is called in
 *  {@link Minecraft#runTick()} function, which is called 20 times a second.
 */
public abstract class GuiBase<T extends Container> extends ContainerScreen<T> {

  protected static final int text_color = 4210752;

  private final ResourceLocation GUI_TEXTURE;
  
  /** This variable can't be used when drawing text, but CAN be used when drawing textures or buttons. */
  protected int guiRight;

  public GuiBase(final T container, final PlayerInventory player_inventory, final ITextComponent title, final ResourceLocation gui_texture_location){
    super(container, player_inventory, title);
    GUI_TEXTURE = gui_texture_location;
  }

  // protected final void set_texture_location(final ResourceLocation texture_location){
  //   GUI_TEXTURE = texture_location;
  // }

  @Override
  public void init(){
    super.init();
    guiRight = guiLeft + xSize; // the guiLeft variable isn't set up until we call super.initGui().
    // OPTIMIZE: add a center variable that is automatically calculated as this.xSize / 2;
  }

  @Override
  public void render(final int mouseX, final int mouseY, final float partialTicks){
    this.renderBackground();
    super.render(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
  }

  /**
   * Well, this must be here because this extends the GuiContainer class, which marks this method as abstract.
   */
  @Override
  protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
  }

  protected void draw_background_texture(){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
    this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }

  /**
   * Draws background texture at z level = 0, which is what it normally draws at anyway.
   * @param texture_width
   * @param texture_height
   */
  protected void draw_custom_background_texture(final int texture_width, int texture_height){
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
    blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, texture_width, texture_height);
  }

  protected final void draw_title(){
    draw_text_center(title.getString(), this.xSize / 2, 6);
  }

  protected final void draw_text_left(final String text, final int x, final int y){
    font.drawString(text, x, y, text_color);
  }

  protected final void draw_text_center(final String text, final int y){
    font.drawString(text, (this.xSize / 2) - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.
   * @see net.minecraft.client.gui.Gui#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  protected final void draw_text_center(final String text, final int x, final int y){
    font.drawString(text, x - (font.getStringWidth(text) / 2), y, text_color);
  }

  /** Draws along the right-edge of the gui. */
  protected final void draw_text_right(final String text, final int y){
    font.drawString(text, this.xSize - 6 - font.getStringWidth(text), y, text_color);
  }

  protected final void draw_text_right(final String text, final int x, final int y){
    font.drawString(text, x - font.getStringWidth(text), y, text_color);
  }

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

}
