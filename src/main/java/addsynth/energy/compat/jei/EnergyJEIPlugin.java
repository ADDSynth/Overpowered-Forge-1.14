package addsynth.energy.compat.jei;

import addsynth.core.util.StringUtil;
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
    registration.addRecipes(CompressorRecipes.INSTANCE.recipes, CompressorRecipeCategory.id);
    add_information(registration);
  }

  private static void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.wire),             VanillaTypes.ITEM, StringUtil.translate("gui.addsynth_energy.jei_description.wire"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_storage),   VanillaTypes.ITEM, StringUtil.translate("gui.addsynth_energy.jei_description.energy_storage"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.electric_furnace), VanillaTypes.ITEM, StringUtil.translate("gui.addsynth_energy.jei_description.electric_furnace"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.compressor),       VanillaTypes.ITEM, StringUtil.translate("gui.addsynth_energy.jei_description.compressor"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.universal_energy_machine), VanillaTypes.ITEM, StringUtil.translate("gui.addsynth_energy.jei_description.universal_energy_interface"));
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.compressor), CompressorRecipeCategory.id);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.electric_furnace), VanillaRecipeCategoryUid.FURNACE);
  }

}
