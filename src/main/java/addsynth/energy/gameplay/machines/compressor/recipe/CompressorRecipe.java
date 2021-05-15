package addsynth.energy.gameplay.machines.compressor.recipe;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.energy.gameplay.EnergyBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public final class CompressorRecipe extends AbstractRecipe {

  public CompressorRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input){
    super(id, group, output, input);
    CompressorRecipes.INSTANCE.addRecipe(this);
  }

  @Override
  public ItemStack getIcon(){
    return new ItemStack(EnergyBlocks.compressor, 1);
  }

  @Override
  public IRecipeSerializer<?> getSerializer(){
    return CompressorRecipes.INSTANCE.serializer;
  }

  @Override
  public IRecipeType<?> getType(){
    return CompressorRecipes.INSTANCE.type;
  }

}
