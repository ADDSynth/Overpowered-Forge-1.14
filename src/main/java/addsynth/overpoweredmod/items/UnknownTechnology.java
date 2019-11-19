package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class UnknownTechnology extends OverpoweredItem {

  public UnknownTechnology(final String name){
    super(name);
  }

  @Override
  public ITextComponent getDisplayName(ItemStack stack){
    return super.getDisplayName(stack).applyTextStyles(TextFormatting.DARK_GRAY, TextFormatting.ITALIC);
  }

}
