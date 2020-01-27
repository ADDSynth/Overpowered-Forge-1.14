package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import java.util.HashMap;
import addsynth.core.material.MaterialsUtil;
import addsynth.core.util.RecipeUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class OreRefineryRecipes {

  private static final int output_multiplier = 2;
  private static Item[] valid_ores;
  private static final HashMap<Item,ItemStack> recipes = new HashMap<>(200);

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  /**
   * <p>This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   *    ore with the "ores" tag, and it has a Furnace Recipe.
   * <p>This is registered as an Event Responder in {@link addsynth.overpoweredmod.OverpoweredMod} and executed
   *    whenever {@link addsynth.core.material.MaterialsUtil} or {@link addsynth.core.util.RecipeUtil} is updated.
   */
  public static final void refresh_ore_refinery_recipes(){
    if(RecipeUtil.check_furnace_recipes()){
      // Debug.log_setup_info("Begin registering Advanced Ore Refinery recipes..."); DELETE
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
            recipes.put(item, result);
          }
        }
      }
      
      valid_ores = list.toArray(new Item[list.size()]);
      
      // Debug.log_setup_info("Finished registering Advanced Ore Refinery recipes.");
    }
  }

  public static final Item[] get_input_filter(){
    if(valid_ores == null){
      refresh_ore_refinery_recipes();
      if(valid_ores == null){
        OverpoweredMod.log.error(new NullPointerException("OreRefineryRecipes Item[] array input filter is null! There must be "+
                                                          "a problem in the refresh_ore_refinery_recipes() function!"));
        return new Item[0];
      }
    }
    return valid_ores;
  }

  public static final boolean matches(final Item item){
    for(Item check_item : valid_ores){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  /**
   * @param input Item
   * @return a copy of the result ItemStack
   */
  public static final ItemStack get_result(final Item input){
    try{
      if(recipes == null){
        throw new NullPointerException("recipes HashMap is null");
      }
      if(input == null){
        throw new IllegalArgumentException("input is null.");
      }
      if(recipes.containsKey(input)){
        final ItemStack result = recipes.get(input);
        if(result == null){
          throw new NullPointerException("The value for HashMap key "+input.toString()+" returned a null value.");
        }
        return result.copy();
      }
      return null; // normal exit if input is invalid.
    }
    catch(Throwable e){
      e.printStackTrace();
      return null;
    }
  }

}
