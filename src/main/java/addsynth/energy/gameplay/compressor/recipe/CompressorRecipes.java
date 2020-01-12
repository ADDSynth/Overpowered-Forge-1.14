package addsynth.energy.gameplay.compressor.recipe;

import java.util.ArrayList;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

public final class CompressorRecipes {

  public static final IRecipeType type = new CompressorRecipeType();
  public static final CompressorRecipeSerializer serializer = new CompressorRecipeSerializer();

  public static final ArrayList<CompressorRecipe> recipes = new ArrayList<>();

  public static final void addRecipe(final CompressorRecipe recipe){
    if(recipes.contains(recipe) == false){
      recipes.add(recipe);
    }
  }

  public static final boolean match(final ItemStack[] input, final World world){
    final Inventory inventory = new Inventory(input);
    for(final CompressorRecipe recipe : recipes){
      if(recipe.matches(inventory, world)){
        return true;
      }
    }
    return false;
  }

  public static final ItemStack getResult(final ItemStack[] input, final World world){
    final Inventory inventory = new Inventory(input);
    for(final CompressorRecipe recipe : recipes){
      if(recipe.matches(inventory, world)){
        return recipe.getRecipeOutput().copy();
      }
    }
    return null;
  }

}
