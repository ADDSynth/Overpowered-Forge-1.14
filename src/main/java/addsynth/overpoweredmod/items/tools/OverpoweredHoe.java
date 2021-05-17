package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class OverpoweredHoe extends HoeItem {

  public OverpoweredHoe(final String name){
    super(OverpoweredTiers.CELESTIAL, 0.0f, new Item.Properties().group(CreativeTabs.tools_creative_tab));
    OverpoweredTechnology.registry.register_item(this, name);
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
