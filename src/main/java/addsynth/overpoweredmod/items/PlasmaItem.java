package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class PlasmaItem extends OverpoweredItem {

  public PlasmaItem(final String name){
    super(name);
  }

  @Override
  public ITextComponent getDisplayName(ItemStack stack){
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.AQUA);
  }

}
