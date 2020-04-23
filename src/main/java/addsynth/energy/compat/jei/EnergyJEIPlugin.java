package addsynth.energy.compat.jei;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public final class EnergyJEIPlugin implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthEnergy.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new CompressorRecipeCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(CompressorRecipes.recipes, CompressorRecipeCategory.id);
    add_information(registration);
  }

  private static void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.wire),             VanillaTypes.ITEM, "Use Energy Wire to connect all your machines together to transfer energy.");
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_storage),   VanillaTypes.ITEM, "This will store any excess energy that you generate, up to 1 million units of Energy.");
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.electric_furnace), VanillaTypes.ITEM, "This machine works just like a regular Furnace except it runs off of electricity. 1,000 Energy will perform 1 smelting operation.");
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.compressor),       VanillaTypes.ITEM, "The Compressor is used to compress metal ingots into metal plates.");
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.universal_energy_machine), VanillaTypes.ITEM, "This machine can interface with other Energy APIs in order to transfer energy to/from other mods.");
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.compressor), CompressorRecipeCategory.id);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.electric_furnace), VanillaRecipeCategoryUid.FURNACE);
  }

}
