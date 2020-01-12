package addsynth.overpoweredmod.compatability.jei.compressor;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public final class CompressorRecipeCategory implements IRecipeCategory<Object> {

  @Override
  public ResourceLocation getUid() {
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
  public void setRecipe(IRecipeLayout recipeLayout, Object recipe, IIngredients ingredients) {
  }

  @Override
  public Class<Object> getRecipeClass(){
    return null;
  }

  @Override
  public void setIngredients(Object recipe, IIngredients ingredients){
  }

}
