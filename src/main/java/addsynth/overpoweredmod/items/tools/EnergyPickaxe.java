package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class EnergyPickaxe extends ItemPickaxe {

  public EnergyPickaxe(final String name){
    super(Tools.ENERGY);
    // Going all the way back to the Item class, Material sets all things like harvest level, durability, enchantability...
    // but once that material is assigned to an ItemPickaxe class, it sets the data to protected variables, which we can
    // access in derived classes.
    this.efficiency = 16.0f; // pickaxes get special treatment :)
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
