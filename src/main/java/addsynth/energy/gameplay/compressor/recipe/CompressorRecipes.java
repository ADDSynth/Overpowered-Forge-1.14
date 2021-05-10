package addsynth.energy.gameplay.compressor.recipe;

import addsynth.core.recipe.shapeless.RecipeCollection;
import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;

public final class CompressorRecipes {

  public static final class CompressorRecipeType implements IRecipeType<CompressorRecipe> {
  }

  public static final RecipeCollection<CompressorRecipe> INSTANCE = new RecipeCollection<CompressorRecipe>(
    new CompressorRecipeType(), new ShapelessRecipeSerializer<CompressorRecipe>(CompressorRecipe.class, 1));

}
