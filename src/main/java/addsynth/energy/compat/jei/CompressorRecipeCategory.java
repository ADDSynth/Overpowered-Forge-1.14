package addsynth.energy.compat.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class CompressorRecipeCategory implements IRecipeCategory<CompressorRecipe> {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthEnergy.MOD_ID, "compressor");
  private final ResourceLocation compressor_gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/gui_textures.png");
  private final IDrawable background;
  private final IDrawable icon;
  // private final LoadingCache<CompressorRecipe, CompressorRecipeDisplayData> cached_display_data;

  public CompressorRecipeCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(compressor_gui_texture, 112, 0, 91, 18);
    icon = gui_helper.createDrawableIngredient(new ItemStack(EnergyBlocks.compressor));
  }

  @Override
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  public String getTitle(){
    return "Compressor";
  }

  @Override
  public IDrawable getBackground(){
    return background;
  }

  @Override
  public IDrawable getIcon(){
    return icon;
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, CompressorRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup gui_item_stacks = recipeLayout.getItemStacks();
    
    gui_item_stacks.init(0, true,   0, 0);
    gui_item_stacks.init(1, true,  18, 0);
    gui_item_stacks.init(2, false, 73, 0);
    
    gui_item_stacks.set(ingredients);
  }

  @Override
  public Class<CompressorRecipe> getRecipeClass(){
    return CompressorRecipe.class;
  }

  @Override
  public void setIngredients(CompressorRecipe recipe, IIngredients ingredients){
    ingredients.setInputIngredients(recipe.getIngredients());
    ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
  }

}
