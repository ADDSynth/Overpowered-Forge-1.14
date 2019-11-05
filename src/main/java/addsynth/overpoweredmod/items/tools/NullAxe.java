package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class NullAxe extends ItemAxe {

  public NullAxe(final String name){
    super(Tools.VOID, 14.0f, -3.0f);
    OverpoweredMod.registry.register_item(this, name);
  }

  @Override
  public boolean isDamageable(){
    return false;
  }

  @Override
  public boolean hasEffect(ItemStack stack){
      return true;
  }
  
  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public EnumRarity getForgeRarity(ItemStack stack){
    return EnumRarity.EPIC;
  }

}
