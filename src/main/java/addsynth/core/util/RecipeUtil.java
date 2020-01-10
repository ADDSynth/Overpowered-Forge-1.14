package addsynth.core.util;

import java.util.ArrayList;
import java.util.Collection;
import addsynth.core.ADDSynthCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
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

  private static Collection<IRecipe> furnace_recipes;

  public static final Collection<IRecipe> getFurnaceRecipes(){
    if(furnace_recipes == null){
      update_furnace_recipes();
    }
    return furnace_recipes;
  }

  public static final boolean match(final IRecipe<IInventory> recipe, final ItemStackHandler inventory, final World world){
    return recipe.matches(new Inventory(getItemStacks(inventory)), world);
  }

  public static final ItemStack[] getItemStacks(final ItemStackHandler handler){
    final int max = handler.getSlots();
    final ItemStack[] stacks = new ItemStack[max];
    int i;
    for(i = 0; i < max; i++){
      stacks[i] = handler.getStackInSlot(i);
    }
    return stacks;
  }

  // This event should only fire when Clients connect to Server Worlds.
  @SubscribeEvent
  public static final void onRecipesUpdated(final RecipesUpdatedEvent event){
    recipe_manager = event.getRecipeManager();
    update_furnace_recipes();
  }

  private static final void updateRecipeManager(){
    if(recipe_manager == null){
      recipe_manager = ServerUtils.getServer().getRecipeManager();
    }
  }

  public static final Collection<IRecipe> getRecipesofType(final IRecipeType type){
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
    furnace_recipes = getRecipesofType(IRecipeType.SMELTING);
  }

}
