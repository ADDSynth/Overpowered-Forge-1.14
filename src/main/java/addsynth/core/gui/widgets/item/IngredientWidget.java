package addsynth.core.gui.widgets.item;

import javax.annotation.Nonnull;
import addsynth.core.gui.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public final class IngredientWidget {

  private int index;
  private ItemStack[] stacks;
  
  public IngredientWidget(){}
  
  public IngredientWidget(final ItemStack[] ingredients){
    this.stacks = ingredients;
  }

  public IngredientWidget(final Ingredient ingredient){
    this.stacks = ingredient.getMatchingStacks();
  }

  public final void update(){
    if(stacks != null){
      index += 1;
      if(index >= stacks.length){
        index = 0;
      }
    }
  }

  public final void setIngredient(@Nonnull ItemStack[] ingredient){
    stacks = ingredient;
    index = 0;
  }

  public final void setIngredient(@Nonnull Ingredient ingredient){
    stacks = ingredient.getMatchingStacks();
    index = 0;
  }

  public final void draw(int x, int y){
    if(stacks != null){
      GuiUtil.drawItemStack(stacks[index], x, y);
    }
  }

  /** This must be called in the ContainerScreen.renderHoveredToolTip() method.<br>
   *  You must add <code>guiLeft</code> and <code>guiTop</code> to the x and y coordinates. */
  public final void drawTooltip(final Screen screen, final int x, final int y, final int mouse_x, final int mouse_y){
    if(stacks != null){
      GuiUtil.drawItemTooltip(screen, stacks[index], x, y, mouse_x, mouse_y);
    }
  }

}
