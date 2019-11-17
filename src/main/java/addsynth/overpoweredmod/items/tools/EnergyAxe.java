package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class EnergyAxe extends AxeItem {

  public EnergyAxe(final String name){
    // You have to specify attack damage and speed for axes yourself, otherwise it produces an error trying to set them
    // using an array of predefined values and using your Material index. Which creates an array index out-of-bounds error.
    super(Tools.ENERGY, 14.0f, -3.0f);
    OverpoweredMod.registry.register_item(this, name);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }
}
