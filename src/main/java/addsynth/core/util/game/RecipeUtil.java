package addsynth.core.util.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import addsynth.core.ADDSynthCore;
import addsynth.core.inventory.InventoryUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = Bus.FORGE)
public final class RecipeUtil {

  private static RecipeManager recipe_manager;

  private static HashMap<Item, ItemStack> furnace_recipes;

  public static final Item[] getFurnaceIngredients(){
    check_furnace_recipes();
    if(furnace_recipes == null){ return null;}
    final Set<Item> furnace_input = furnace_recipes.keySet();
    return furnace_input.toArray(new Item[furnace_input.size()]);
  }

  public static final boolean isFurnaceIngredient(final Item item){
    if(check_furnace_recipes()){
      for(final Item item2 : furnace_recipes.keySet()){
        if(item == item2){
          return true;
        }
      }
    }
    return false;
  }

  public static final ItemStack getFurnaceResult(final ItemStack stack){
    return getFurnaceRecipeResult(stack.getItem());
  }

  public static final ItemStack getFurnaceRecipeResult(final Item item){
    if(check_furnace_recipes()){
      for(final Entry<Item, ItemStack> entry : furnace_recipes.entrySet()){
        if(entry.getKey() == item){
          return entry.getValue();
        }
      }
    }
    return ItemStack.EMPTY;
  }

  /** This only returns whether furnace recipes are loaded. For a more robust check,
   *  call the check_furnace_recipes() function.
   */
  public static final boolean furnace_recipes_loaded(){
    if(furnace_recipes == null){ return false; }
    if(furnace_recipes.size() == 0){ return false; }
    return true;
  }

  /** Checks if Furnace Recipes are loaded, and attempts to load them if they aren't.
   *  Returns true if they are loaded.
   */
  public static final boolean check_furnace_recipes(){
    if(furnace_recipes_loaded() == true){ return true;}
    update_furnace_recipes();
    if(furnace_recipes_loaded() == false){
      ADDSynthCore.log.error(new RuntimeException(
        "Attempted to access Furnace Recipes at an inappropiate time. Recipes should automatically update when "+
        "recipes are reloaded, such as when joining worlds."));
      Thread.dumpStack();
      return false;
    }
    return true;
  }

  public static final boolean match(final IRecipe<IInventory> recipe, final ItemStackHandler inventory, final World world){
    return recipe.matches(InventoryUtil.toInventory(inventory), world);
  }

  private static final ArrayList<Runnable> responders = new ArrayList<>();

  /** Register functions to call when Recipes are reloaded. Your functions are probably used to
   *  generate internal recipes for your machines. These depend on Item Tags.
   *  The Reload Recipes event ALWAYS occurrs after Tags are reloaed.
   * @param executable
   */
  public static final void registerResponder(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.warn("That function is already registered as an event responder.");
      // Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  private static final void dispatchEvent(){
    for(final Runnable responder : responders){
      responder.run();
    }
  }

  // This event should only fire when Clients connect to Server Worlds.
  @SubscribeEvent
  public static final void onRecipesUpdated(final RecipesUpdatedEvent event){
    ADDSynthCore.log.info("Recipes were reloaded. Sending update events...");
  
    recipe_manager = event.getRecipeManager();
    update_furnace_recipes();
    dispatchEvent();

    ADDSynthCore.log.info("Done responding to Recipe reload.");
  }

  /** Attempts to get an instance of the RecipeManager from the server. */
  @SuppressWarnings({ "null", "deprecation" })
  private static final void updateRecipeManager(){
    if(recipe_manager == null){
      recipe_manager = ServerUtils.getServer().getRecipeManager();
    }
  }

  public static final ArrayList<IRecipe> getRecipesofType(final IRecipeType type){
    updateRecipeManager();
    final ArrayList<IRecipe> list = new ArrayList<>(200);
    for(IRecipe recipe : recipe_manager.getRecipes()){
      if(recipe.getType() == type){
        list.add(recipe);
      }
    }
    return list;
  }

  private static final void update_furnace_recipes(){
    if(furnace_recipes == null){
      furnace_recipes = new HashMap<>(300);
    }
    else{
      furnace_recipes.clear();
    }
    FurnaceRecipe furnace_recipe;
    Ingredient ingredient;
    ItemStack result;
    for(final IRecipe recipe : getRecipesofType(IRecipeType.SMELTING)){
      if(recipe instanceof FurnaceRecipe){ // only for safety reasons
        furnace_recipe = (FurnaceRecipe)recipe;
        ingredient = furnace_recipe.getIngredients().get(0); // furnace recipes only have 1 ingredient
        result = furnace_recipe.getRecipeOutput();
        for(final ItemStack stack : ingredient.getMatchingStacks()){
          furnace_recipes.put(stack.getItem(), result);
        }
      }
      else{
        ADDSynthCore.log.warn("RecipeUtil.update_furnace_recipes() found a Recipe of type SMELTING but it is not a Furnace Recipe?");
      }
    }
  }

}
