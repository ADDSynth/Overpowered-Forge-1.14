package addsynth.overpoweredmod.game.recipes;

import java.util.ArrayList;
import addsynth.core.util.RecipeUtil;
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

  private static final ArrayList<IRecipe> recipes = new ArrayList<>(11);

  public static final void register(){
    Debug.log_setup_info("Begin registering Compressor Recipes...");
    recipes.add(RecipeUtil.create(new ItemStack(Metals.BRONZE.ingot,2), "ingotTin", "ingotCopper"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.IRON.plating,1),     "ingotIron"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.GOLD.plating,1),     "ingotGold"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.TIN.plating,1),      "ingotTin"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.ALUMINUM.plating,1), "ingotAluminum"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.COPPER.plating,1),   "ingotCopper"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.STEEL.plating,1),    "ingotSteel"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.BRONZE.plating,1),   "ingotBronze"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.SILVER.plating,1),   "ingotSilver"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.PLATINUM.plating,1), "ingotPlatinum"));
    recipes.add(RecipeUtil.create(new ItemStack(Metals.TITANIUM.plating,1), "ingotTitanium"));
    Debug.log_setup_info("Finished registering Compressor Recipes.");
  }

  public static final boolean match(final ItemStack[] input){
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
