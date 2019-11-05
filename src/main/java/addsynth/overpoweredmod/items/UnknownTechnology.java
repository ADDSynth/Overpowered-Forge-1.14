package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public final class UnknownTechnology extends OverpoweredItem {

  public UnknownTechnology(final String name){
    super(name);
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack){
    return TextFormatting.DARK_GRAY.toString() + TextFormatting.ITALIC.toString() + super.getItemStackDisplayName(stack);
  }

}
