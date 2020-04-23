package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class OreRefineryRecipe {

  public final Item input;
  public final ItemStack itemStack;
  public final ItemStack output;

  public OreRefineryRecipe(final Item input, final ItemStack output){
    this.input = input;
    this.itemStack = new ItemStack(input, 1);
    this.output = output;
  }
  
}
