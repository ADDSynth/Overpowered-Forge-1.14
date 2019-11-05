package addsynth.overpoweredmod.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public final class EnergySword extends ItemSword {

  public EnergySword(final String name){
    super(Tools.ENERGY);
    setMaxDamage(1000); // Iron Sword = 250, Diamond Sword = 1500
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

  // https://minecraft.gamepedia.com/Attribute
  @Override
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {

    // Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
    Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
    if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
      // Base Attack Damage is 3 (found in attackDamage field initializer in SwordClass),
      // + ToolMaterial Attack Damage, Wood & Gold = 0, Stone = 1, Iron = 2, Diamond = 3
      // + 1 Base attack for when the player doesn't have any tools equipped? I think? I'm not sure how sword damage is calculated.
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 11.0d, 0));
      // Base Attack Speed is 4, Vanilla modifier value is -2.4 with operation 0, which adds it.
      // 4 + -2.4 = 1.6. All swords have a Attack Speed of 1.6, which is number of swings per second.
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9d, 0));
    }
    return multimap;
  }
  
}
