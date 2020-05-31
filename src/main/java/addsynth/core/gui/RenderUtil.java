package addsynth.core.gui;

import java.lang.reflect.Method;
import com.mojang.blaze3d.platform.GlStateManager;
import addsynth.core.util.JavaUtils;
import addsynth.core.util.color.Color;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

/** I went through all this trouble just to be able to render Items at a varying opacity,
 *  using Reflection.
 * @author ADDSynth
 * @since May 30, 2020
 */
public final class RenderUtil {

  private static final Method setup_gui_transform_method =
    JavaUtils.getMethod(ItemRenderer.class, "func_180452_a", int.class, int.class, boolean.class);

  private static final Method render_model_method =
    JavaUtils.getMethod(ItemRenderer.class, "func_191961_a", IBakedModel.class, int.class, ItemStack.class);


  /** <p>NOTE: This is an exact copy of {@link ItemRenderer#renderItemModelIntoGUI(ItemStack, int, int, IBakedModel)}.
   *     You MUST ensure there aren't any changes whenver the Forge version updates!
   * @param stack
   * @param x
   * @param y
   * @param opacity
   */
  public static final void drawItemStack(ItemRenderer itemRenderer, TextureManager textureManager, ItemStack stack, int x, int y, float opacity){
    if(stack.isEmpty()){
      return;
    }
    if(setup_gui_transform_method == null || render_model_method == null){
      itemRenderer.renderItemIntoGUI(stack, x, y);
      return;
    }
    
    try{
      IBakedModel bakedmodel = itemRenderer.getModelWithOverrides(stack);
      
      GlStateManager.pushMatrix();
      textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableAlphaTest();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      // https://www.khronos.org/opengl/wiki/Blending
      // http://docs.gl/gl4/glBlendFunc
      // http://www.opengl-tutorial.org/intermediate-tutorials/tutorial-10-transparency/
      // https://learnopengl.com/Advanced-OpenGL/Blending
      // http://i.stack.imgur.com/i2IAC.jpg
      // https://andersriggelsen.dk/glblendfunc.php
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, opacity);
      setup_gui_transform_method.invoke(itemRenderer, x, y, bakedmodel.isGui3d());
      bakedmodel = ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
      custom_render_item(itemRenderer, textureManager, stack, bakedmodel, opacity);
      GlStateManager.disableAlphaTest();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableLighting();
      GlStateManager.popMatrix();
      textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
    catch(Exception e){
    }
  }

  /** NOTE: This is an exact replica of {@link ItemRenderer#renderItem(ItemStack, IBakedModel)}.
   *  Must check for ANY changes whenever you update Forge!
   */
  private static final void custom_render_item(ItemRenderer itemRenderer, TextureManager textureManager, ItemStack stack, IBakedModel model, float opacity){
    GlStateManager.pushMatrix();
    GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
    if(model.isBuiltInRenderer()){
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, opacity);
      GlStateManager.enableRescaleNormal();
      stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
    }
    else{
      renderModel(itemRenderer, model, Color.make(255, 255, 255, opacity), stack);
      if(stack.hasEffect()){
        ItemRenderer.renderEffect(textureManager, () -> {
          renderModel(itemRenderer, model, -8372020, ItemStack.EMPTY);
        }, 8);
      }
    }

    GlStateManager.popMatrix();
  }

  private static final void renderModel(ItemRenderer itemRenderer, IBakedModel model, int color, ItemStack stack){
    try{
      render_model_method.invoke(itemRenderer, model, color, stack);
    }
    catch(Exception e){
    }
  }

}
