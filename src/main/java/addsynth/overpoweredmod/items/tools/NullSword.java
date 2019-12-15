package addsynth.overpoweredmod.items.tools;

import addsynth.core.Constants;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;

public class NullSword extends SwordItem {

  public NullSword(final String name){
    super(OverpoweredTiers.VOID, Constants.sword_damage, Constants.sword_speed, new Item.Properties().group(OverpoweredMod.tools_creative_tab));
    OverpoweredMod.registry.register_item(this, name);
  }

  @Override
  public boolean isDamageable() {
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

/*
  @Override
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
    Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
    if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 11.0d, 0));
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9d, 0));
    }
    return multimap;
  }
*/

}
