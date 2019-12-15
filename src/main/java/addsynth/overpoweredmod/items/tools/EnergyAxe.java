package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class EnergyAxe extends AxeItem {

  public EnergyAxe(final String name){
    super(OverpoweredTiers.ENERGY, 14.0f, -3.0f, new Item.Properties().group(OverpoweredMod.tools_creative_tab));
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
