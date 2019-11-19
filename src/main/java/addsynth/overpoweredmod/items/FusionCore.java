package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class FusionCore extends OverpoweredItem {

  public FusionCore(final String name){
    super(name);
  }

  @Override
  public ITextComponent getDisplayName(ItemStack stack){
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.GOLD);
  }

}
