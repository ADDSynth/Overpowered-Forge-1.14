package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class NullHoe extends ItemHoe {

  public NullHoe(final String name){
    super(Tools.VOID);
    OverpoweredMod.registry.register_item(this, name);
  }

  @Override
  public boolean isDamageable(){
    return false;
  }

  @Override
  public boolean hasEffect(ItemStack stack)
  {
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
