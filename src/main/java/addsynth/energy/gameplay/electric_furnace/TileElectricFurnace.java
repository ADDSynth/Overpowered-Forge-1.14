package addsynth.energy.gameplay.electric_furnace;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.util.RecipeUtil;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public final class TileElectricFurnace extends PassiveMachine implements INamedContainerProvider {

  public static final Item[] furnace_input = get_furnace_input();
  private ItemStack result;

  public TileElectricFurnace(){
    super(Tiles.ELECTRIC_FURNACE, 1, furnace_input, 1, new CustomEnergyStorage(Values.electric_furnace_required_energy.get()), Values.electric_furnace_work_time.get());
  }

  @Override
  protected final void test_condition(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    final ItemStack output = output_inventory.getStackInSlot(0);
    result = input.isEmpty() ? null : getFurnaceRecipeResult(input, world);
    can_run = (input != ItemStack.EMPTY && input.getCount() > 0) && (output == ItemStack.EMPTY || output_inventory.can_add(0, result));
  }

  @Override
  protected final void performWork(){
    input_inventory.decrease(0);
    output_inventory.insertItem(0, result.copy(), false);
  }

  /** Gets all of the input Items from Furnace recipes to use as an Item Filter. */
  private static final Item[] get_furnace_input(){ // MAYBE: eventually calculate this at a more stable time?
    final ArrayList<Item> items = new ArrayList<>(500);
    for(final IRecipe<?> recipe : RecipeUtil.getFurnaceRecipes()){
      for(final Ingredient ingredient : recipe.getIngredients()){
        for(final ItemStack stack : ingredient.getMatchingStacks()){
          items.add(stack.getItem());
        }
      }
    }
    return items.toArray(new Item[items.size()]);
  }

  private static final ItemStack getFurnaceRecipeResult(final ItemStack stack, final World world){
    for(final IRecipe<IInventory> recipe : RecipeUtil.getFurnaceRecipes()){
      if(recipe.matches(new Inventory(stack), world)){
        return recipe.getRecipeOutput();
      }
    }
    return null;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerElectricFurnace(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
