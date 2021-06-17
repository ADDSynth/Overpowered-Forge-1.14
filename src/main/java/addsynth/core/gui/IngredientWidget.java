package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
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

  public final ItemStack getItemStack(){
    return stacks[index];
  }

  /** This must be called in the ContainerScreen.renderHoveredToolTip() method.<br>
   *  You must add <code>guiLeft</code> and <code>guiTop</code> to the x and y coordinates. */
  public final void drawTooltip(final Screen screen, final int x, final int y, final int mouse_x, final int mouse_y){
    GuiUtil.drawItemTooltip(screen, stacks[index], x, y, mouse_x, mouse_y);
  }

}
