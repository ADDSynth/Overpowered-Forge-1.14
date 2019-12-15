package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class NullHoe extends HoeItem {

  public NullHoe(final String name){
    super(OverpoweredTiers.VOID, 0.0f, new Item.Properties().group(OverpoweredMod.tools_creative_tab));
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
  public Rarity getRarity(ItemStack stack){
    return Rarity.EPIC;
  }

}
