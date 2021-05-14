package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public final class IngredientWidget {

  private int index;
  private final ItemStack[] stacks;
  
  public IngredientWidget(final ItemStack[] ingredients){
    this.stacks = ingredients;
  }

  public IngredientWidget(final Ingredient ingredient){
    this.stacks = ingredient.getMatchingStacks();
  }

  public final void update(){
    index += 1;
    if(index >= stacks.length){
      index = 0;
    }
  }

  public final void draw(int x, int y){
    GuiUtil.drawItemStack(stacks[index], x, y);
  }

}
