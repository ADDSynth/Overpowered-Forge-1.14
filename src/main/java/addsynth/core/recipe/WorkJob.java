package addsynth.core.recipe;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public final class WorkJob {

  private final ItemStack[] ingredients;
  private final ItemStack result;
  
  public WorkJob(final ItemStack[] input, final ItemStack result){
    this.ingredients = input;
    this.result = result;
  }
  
  // TODO: eventually upgrade this to use a recipe argument to decrease the items if the recipe requires more than 1 of the same item.
  public static final WorkJob subtractFromInventory(final ItemStack[] inventory, final ItemStack result){
    final ArrayList<ItemStack> temp_array = new ArrayList<>(inventory.length);
    for(final ItemStack stack : inventory){
      if(stack.isEmpty() == false){
        temp_array.add(stack);
      }
    }
    final int size = temp_array.size();
    int i;
    final ItemStack[] ingredients = new ItemStack[size];
    ItemStack slot;
    for(i = 0; i < size; i++){
      slot = temp_array.get(i);
      ingredients[i] = slot.copy();
      ingredients[i].setCount(1);
      slot.shrink(1);
    }
    return new WorkJob(ingredients, result);
  }

  public final ItemStack[] getIngredients(){
    return ingredients;
  }
  
  public final ItemStack getResult(){
    return result.copy();
  }

}
