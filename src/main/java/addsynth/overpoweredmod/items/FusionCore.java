package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public final class FusionCore extends OverpoweredItem {

  public FusionCore(final String name){
    super(name);
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack){
    return TextFormatting.GOLD.toString() + super.getItemStackDisplayName(stack);
  }

}
