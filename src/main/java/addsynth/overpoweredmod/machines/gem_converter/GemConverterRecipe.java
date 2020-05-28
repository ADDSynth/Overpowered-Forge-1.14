package addsynth.overpoweredmod.machines.gem_converter;

import java.util.ArrayList;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.machines.Filters;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public final class GemConverterRecipe {

  public static final ArrayList<GemConverterRecipe> recipes = new ArrayList<>(200);
  
  private static final NonNullList<Ingredient> all_gems = NonNullList.create();
  
  public final NonNullList<Ingredient> ingredients;
  public final ItemStack result;
  
  private GemConverterRecipe(NonNullList<Ingredient> input, final ItemStack output){
    this.ingredients = input;
    this.result = output;
  }
  
  public static final void generate_recipes(){
    recipes.clear();
    generate_all_gems();
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.ruby)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.topaz)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.citrine)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.emerald)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.diamond)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.sapphire)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.amethyst)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Gems.quartz)));
  }

  private static final void generate_all_gems(){
    all_gems.clear();
    all_gems.add(Ingredient.fromItems(Filters.gem_converter));
  }

}
