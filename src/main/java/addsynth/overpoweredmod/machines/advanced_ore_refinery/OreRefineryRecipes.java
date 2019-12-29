package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import java.util.HashMap;
import addsynth.core.material.MaterialsUtil;
import addsynth.overpoweredmod.Debug;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class OreRefineryRecipes {

  private static final int result_output = 2;
  private static Item[] valid_ores;
  private static final HashMap<Item,ItemStack> recipes = new HashMap<>(100);

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  /**
   * This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   * ore with the "ores" tag, and it has a Furnace Recipe.
   */
  public static final void register(){
    Debug.log_setup_info("Begin registering Advanced Ore Refinery recipes...");
  
    final ArrayList<Item> list = new ArrayList<Item>(100);
    for(final Item item : MaterialsUtil.getOres()){
      // TODO: Check if Ore Block has a Furnace recipe.
      if(false){
        /*
        if(result_check != null){
          list.add(ore.getItem());
          final ItemStack result = result_check.copy();
          result.setCount(result.getCount()*result_output);
          recipes.put(ore.getItem(), result);
        }
        */
      }
    }
    
    valid_ores = list.toArray(new Item[list.size()]);
    
    Debug.log_setup_info("Finished registering Advanced Ore Refinery recipes.");
  }

  public static final Item[] get_input(){
    if(valid_ores == null){
      throw new NullPointerException("OreRefineryRecipes Item[] array input filter is null! It is created "+
                                     "in the register() function. Was OreRefineryRecipes.register() not called?");
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
