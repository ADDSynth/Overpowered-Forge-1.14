package addsynth.overpoweredmod.game.recipes;

import java.util.ArrayList;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.core.Metals;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 *  Crafting recipes are with the {@link net.minecraft.item.crafting.CraftingManager}. Well I'm using crafting
 *  recipes for a different reason, so I keep them with this Compressor Recipes Manager class.
 */
public final class CompressorRecipes {

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/api/recipe/CompressorRecipes.java
// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/inventory/ContainerIngotCompressor.java
// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/tile/TileEntityIngotCompressor.java

  public static final boolean match(final ItemStack[] input){
   // FIX: Load all overpowered:compressor recipes and compare ingredients.
    for(IRecipe recipe : recipes){
      if(RecipeUtil.match(input, recipe)){
        return true;
      }
    }
    return false;
  }

  /** Returns a COPY of the Recipe Output ItemStack. */
  public static final ItemStack getResult(final ItemStack[] input){
    ItemStack output = ItemStack.EMPTY;
    for(IRecipe recipe : recipes){
      if(RecipeUtil.match(input, recipe)){
        output = recipe.getRecipeOutput().copy();
        break;
      }
    }
    return output;
  }

}
