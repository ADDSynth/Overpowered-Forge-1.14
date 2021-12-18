package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import addsynth.core.items.ItemUtil;
import addsynth.core.recipe.RecipeUtil;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class OreRefineryRecipes {

  private static final int output_multiplier = 2;
  private static Item[] valid_ores;
  public static final ArrayList<OreRefineryRecipe> recipes = new ArrayList<>(200);

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  /**
   * <p>This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   *    ore with the "ores" tag, and it has a Furnace Recipe.
   * <p>This is registered as an Event Responder in {@link OverpoweredTechnology} and executed
   *    whenever {@link MaterialsUtil} or {@link RecipeUtil} is updated.
   */
  public static final void refresh_ore_refinery_recipes(){
    if(RecipeUtil.check_furnace_recipes()){
      recipes.clear();
      final ArrayList<Item> list = new ArrayList<Item>(100);
      ItemStack result_check;
      for(final Item item : MaterialsUtil.getOres()){
        if(RecipeUtil.isFurnaceIngredient(item)){
          result_check = RecipeUtil.getFurnaceRecipeResult(item);
          if(result_check.isEmpty() == false){
            list.add(item);
            final ItemStack result = result_check.copy();
            result.setCount(result.getCount()*output_multiplier);
            recipes.add(new OreRefineryRecipe(item, result));
          }
        }
      }
      
      valid_ores = list.toArray(new Item[list.size()]);
    }
  }

  public static final Item[] get_input_filter(){
    if(valid_ores == null){
      refresh_ore_refinery_recipes();
      if(valid_ores == null){
        OverpoweredTechnology.log.error(
          new NullPointerException(
            OreRefineryRecipes.class.getSimpleName()+" Item[] array input filter is null! There must be "+
            "a problem in the refresh_ore_refinery_recipes() function!"
          )
        );
        return new Item[0];
      }
    }
    return valid_ores;
  }

  @Deprecated // REMOVE in 2026
  public static final boolean matches(final Item item){
    for(Item check_item : valid_ores){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  /** Finds matching input and returns result ItemStack. */
  public static final ItemStack get_result(final ItemStack input){
    if(recipes == null){
      OverpoweredTechnology.log.error(new NullPointerException("Ore Refinery recipes list is null."));
      return ItemStack.EMPTY;
    }
    if(ItemUtil.itemStackExists(input)){
      final Item input_item = input.getItem();
      for(OreRefineryRecipe recipe : recipes){
        if(recipe.input == input_item){
          return recipe.output.copy();
        }
      }
    }
    return ItemStack.EMPTY;
  }

}
