package addsynth.overpoweredmod.compatability.jei.compressor;

import java.util.List;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public final class CompressorRecipeCategory implements IRecipeCategory {

  @Override
  public String getUid() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public IDrawable getBackground() {
    return null;
  }

  @Override
  public IDrawable getIcon() {
    return null;
  }

  @Override
  public void drawExtras(Minecraft minecraft) {
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
  }

  @Override
  public List getTooltipStrings(int mouseX, int mouseY) {
    return null;
  }

  @Override
  public String getModName(){
    return null;
  }

}
