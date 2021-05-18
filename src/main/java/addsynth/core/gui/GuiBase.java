package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/** Extend from this class if you just want your own gui screen. */
public abstract class GuiBase extends Screen {

  protected final GuiUtil guiUtil;

  protected GuiBase(final int width, final int height, final ITextComponent title, final ResourceLocation gui_texture){
    super(title);
    guiUtil = new GuiUtil(gui_texture, width, height);
  }

  @Override
  public void render(int mouse_x, int mouse_y, float partialTicks){
    // REPLICA: This is a replica of ContainerScreen.render() but without drawing any ItemStacks. Make sure it matches whenever we update Forge.
    super.renderBackground();
    drawGuiBackgroundLayer(partialTicks, mouse_x, mouse_y);
    // net.minecraftforge.common.MinecraftForge.EVENT_BUS.pose(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, mouse_x, mouse_y));
    GlStateManager.disableRescaleNormal();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableLighting();
    GlStateManager.disableDepthTest();
    super.render(mouse_x, mouse_y, partialTicks);
    GlStateManager.pushMatrix();
    GlStateManager.translatef((float)guiUtil.guiLeft, (float)guiUtil.guiTop, 0.0f);
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    GlStateManager.enableRescaleNormal();
    
    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0f, 240.0f);
    drawGuiForegroundLayer(mouse_x, mouse_y);
    
    GlStateManager.popMatrix();
    GlStateManager.enableLighting();
    GlStateManager.enableDepthTest();
    RenderHelper.enableStandardItemLighting();
  }

  @Override
  public final boolean isPauseScreen(){
    return false;
  }

  protected void drawGuiBackgroundLayer(float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_background_texture();
  }
  
  protected void drawGuiForegroundLayer(int mouse_x, int mouse_y){
  }

}
