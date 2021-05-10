package addsynth.core.recipe.shapeless;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** Nearly all of this code was copied from {@link net.minecraft.item.crafting.ShapelessRecipe} */
public abstract class AbstractRecipe implements IRecipe<IInventory> {

   private final ResourceLocation id;
   private final String group;
   private final ItemStack result;
   private final NonNullList<Ingredient> recipeItems;

  public AbstractRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input){
    this.id = id;
    this.group = group;
    this.result = output;
    this.recipeItems = input;
    // this.isSimple = input.stream().allMatch(Ingredient::isSimple);
  }

  @Override
  public String getGroup(){
    return group;
  }

  @Override
  public NonNullList<Ingredient> getIngredients(){
    return recipeItems; // what are the consequences of returning the EXACT list object? it can be manipulated from an external source.
  }

  @Override
  public boolean matches(IInventory inv, World world){
    final RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
    int items_in_inventory = 0;
    int j;

    for(j = 0; j < inv.getSizeInventory(); j++){
      final ItemStack itemstack = inv.getStackInSlot(j);
      if(itemstack.isEmpty() == false){
        items_in_inventory += 1;
        recipeitemhelper.func_221264_a(itemstack, 1);
      }
    }

    return items_in_inventory == this.recipeItems.size() && recipeitemhelper.canCraft(this, (IntList)null);
  }

  @Override
  public boolean canFit(int width, int height){
    return width * height >= this.recipeItems.size();
  }

  /** @return A copy of the output ItemStack. */
  @Override
  public ItemStack getCraftingResult(IInventory inv){
    return result.copy();
  }

  /** @return EXACT object. DO NOT EDIT! */
  @Override
  public ItemStack getRecipeOutput(){
    return result;
  }

  @Override
  public ResourceLocation getId(){
    return id;
  }

}
