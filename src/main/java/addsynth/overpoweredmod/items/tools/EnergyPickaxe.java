package addsynth.overpoweredmod.items.tools;

import addsynth.core.Constants;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;

public class EnergyPickaxe extends PickaxeItem {

  public EnergyPickaxe(final String name){
    super(OverpoweredTiers.ENERGY_PICKAXE, Constants.pickaxe_damage, Constants.pickaxe_speed,
      new Item.Properties().group(OverpoweredMod.tools_creative_tab));
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
