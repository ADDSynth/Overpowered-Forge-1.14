package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public final class EnergyShovel extends ItemSpade {

  public EnergyShovel(final String name){
    super(Tools.ENERGY);
    OverpoweredMod.registry.register_item(this, name);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public EnumRarity getForgeRarity(ItemStack stack){
    return EnumRarity.RARE;
  }
}
