package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class AdvancedOreRefineryCategory  implements IRecipeCategory<OreRefineryRecipe> {

  public static final ResourceLocation id = new ResourceLocation(OverpoweredTechnology.MOD_ID, "advanced_ore_refinery");
  private final ResourceLocation gui_texture = new ResourceLocation(OverpoweredTechnology.MOD_ID, "textures/gui/gui_textures.png");
  private final IDrawable background;
  private final IDrawable icon;

  public AdvancedOreRefineryCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(gui_texture, 0, 16, 74, 18);
    icon = gui_helper.createDrawableIngredient(new ItemStack(Machines.advanced_ore_refinery));
  }

  @Override
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  public Class<? extends OreRefineryRecipe> getRecipeClass(){
    return OreRefineryRecipe.class;
  }

  @Override
  public String getTitle(){
    return "Advanced Ore Refinery";
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
  public void setIngredients(OreRefineryRecipe recipe, IIngredients ingredients){
    ingredients.setInput(VanillaTypes.ITEM, recipe.itemStack);
    ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, OreRefineryRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup gui_item_stacks =  recipeLayout.getItemStacks();
    gui_item_stacks.init(0, true,   0, 0);
    gui_item_stacks.init(1, false, 56, 0);
    gui_item_stacks.set(ingredients);
  }

}
