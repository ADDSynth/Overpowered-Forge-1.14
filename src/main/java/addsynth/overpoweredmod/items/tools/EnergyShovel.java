package addsynth.overpoweredmod.items.tools;

import addsynth.core.Constants;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;

public final class EnergyShovel extends ShovelItem {

  public EnergyShovel(final String name){
    super(OverpoweredTiers.ENERGY, Constants.axe_damage, Constants.axe_speed, new Item.Properties().group(CreativeTabs.tools_creative_tab));
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
