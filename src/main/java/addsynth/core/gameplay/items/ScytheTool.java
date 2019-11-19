package addsynth.core.gameplay.items;

import java.util.HashSet;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.Ingredient;

public class ScytheTool extends ToolItem {

  public ScytheTool(final String name, final IItemTier material){
    super(1.5f, -3.0f, material, new HashSet<Block>(), new Item.Properties());
    ADDSynthCore.registry.register_item(this, name);
  }

  public static final void registerRecipe(final Item output, final Object input){
    RecipeUtil.register("Scythes", 3, 3, new ItemStack(output, 1), new Object[] {
      Ingredient.EMPTY, input,            input,
      input,            Ingredient.EMPTY, "stickWood",
      Ingredient.EMPTY, Ingredient.EMPTY, "stickWood"});
  }

}
