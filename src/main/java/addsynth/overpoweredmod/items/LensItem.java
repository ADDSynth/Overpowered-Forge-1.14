package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public final class LensItem extends OverpoweredItem {

  private final TextFormatting color_code;
  // https://minecraft.gamepedia.com/Formatting_codes

  public LensItem(final String name, final TextFormatting format_code){
    super(name);
    color_code = format_code;
  }

  @Override
  public String getItemStackDisplayName(final ItemStack stack){
    return color_code + super.getItemStackDisplayName(stack);
  }

}
