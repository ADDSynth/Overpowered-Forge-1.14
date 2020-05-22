package addsynth.overpoweredmod.machines.inverter;

import java.util.ArrayList;
import addsynth.overpoweredmod.game.core.Init;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class InverterRecipe {

  public final ItemStack input;
  public final ItemStack result;
  
  private InverterRecipe(final Item input, final Item output){
    this.input = new ItemStack(input, 1);
    this.result = new ItemStack(output, 1);
  }

  public static final ArrayList<InverterRecipe> get_recipes(){
    final ArrayList<InverterRecipe> list = new ArrayList<>(2);
    list.add(new InverterRecipe(Init.energy_crystal, Init.void_crystal));
    list.add(new InverterRecipe(Init.void_crystal, Init.energy_crystal));
    return list;
  }

}
