package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;

public class EnergyPickaxe extends PickaxeItem {

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
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }
}
