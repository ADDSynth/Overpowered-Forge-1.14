package addsynth.energy.gameplay.machines.circuit_fabricator.recipe;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.energy.gameplay.EnergyBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public final class CircuitFabricatorRecipe extends AbstractRecipe {

  public CircuitFabricatorRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
    super(id, group, output, input);
    CircuitFabricatorRecipes.INSTANCE.addRecipe(this);
  }

  @Override
  public ItemStack getIcon(){
    return new ItemStack(EnergyBlocks.circuit_fabricator, 1);
  }

  @Override
  public IRecipeSerializer<?> getSerializer(){
    return CircuitFabricatorRecipes.INSTANCE.serializer;
  }

  @Override
  public IRecipeType<?> getType(){
    return CircuitFabricatorRecipes.INSTANCE.type;
  }

}
