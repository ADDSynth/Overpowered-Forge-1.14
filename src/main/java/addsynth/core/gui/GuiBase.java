package addsynth.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 *  <b>Note:</b>
 *  the {@link GuiScreen#drawScreen(int, int, float)} function (where background, forground, and
 *  buttons is being drawed in) is called in the
 *  {@link net.minecraft.client.renderer.EntityRenderer#updateCameraAndRender} function which
 *  is called every frame in the {@link Minecraft#runGameLoop()} function.<br>
 *  The {@link GuiScreen#updateScreen()} function is called in
 *  {@link Minecraft#runTick()} function, which is called 20 times a second.
 */
public class GuiBase extends GuiContainer {

  protected static final int text_color = 4210752;

  private final ResourceLocation GUI_TEXTURE;
  private final String title;
  
  /** This variable can't be used when drawing text, but CAN be used when drawing textures or buttons. */
  protected int guiRight;

  public GuiBase(final Container container, final TileEntity tile, final ResourceLocation gui_texture_location){
    super(container);
    GUI_TEXTURE = gui_texture_location;
    this.title = tile.getBlockType().getLocalizedName();
  }

  // protected final void set_texture_location(final ResourceLocation texture_location){
  //   GUI_TEXTURE = texture_location;
  // }

  @Override
  public void initGui(){
    super.initGui();
    guiRight = guiLeft + xSize; // the guiLeft variable isn't set up until we call super.initGui().
    // OPTIMIZE: add a center variable that is automatically calculated as this.xSize / 2;
  }

  @Override
  public final void drawScreen(final int mouseX, final int mouseY, final float partialTicks){
    this.drawDefaultBackground();
    super.drawScreen(mouseX, mouseY, partialTicks);
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
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }

  /**
   * Draws background texture at z level = 0, which is what it normally draws at anyway.
   * @param texture_width
   * @param texture_height
   */
  protected void draw_custom_background_texture(final int texture_width, int texture_height){
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
    drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, texture_width, texture_height);
  }

  protected final void draw_title(){
    draw_text_center(title, this.xSize / 2, 6);
  }

  protected final void draw_text_left(final String text, final int x, final int y){
    fontRenderer.drawString(text, x, y, text_color);
  }

  protected final void draw_text_center(final String text, final int y){
    fontRenderer.drawString(text, (this.xSize / 2) - (fontRenderer.getStringWidth(text) / 2), y, text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.
   * @see net.minecraft.client.gui.Gui#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  protected final void draw_text_center(final String text, final int x, final int y){
    fontRenderer.drawString(text, x - (fontRenderer.getStringWidth(text) / 2), y, text_color);
  }

  protected final void draw_text_right(final String text, final int y){
    fontRenderer.drawString(text, this.xSize - 6 - fontRenderer.getStringWidth(text), y, text_color);
  }

  protected final void draw_text_right(final String text, final int x, final int y){
    fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text), y, text_color);
  }

}
