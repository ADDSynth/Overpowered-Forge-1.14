package addsynth.overpoweredmod.machines.identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import addsynth.core.game.items.ItemUtil;
import addsynth.core.game.items.enchantment.EnchantPair;
import addsynth.core.game.items.enchantment.EnchantmentUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ArmorEffects {

  // You're getting free armor from killing mobs! Don't enchant armor with Enchantments that extend
  //   the life of the armor, such as Unbreaking or Mending!

  public static final int max_levels = 4;
  
  @SuppressWarnings("incomplete-switch")
  public static final void enchant(final ItemStack stack){
    if(ItemUtil.itemStackExists(stack)){
      final Item item = stack.getItem();
      if(item instanceof ArmorItem){
        switch(((ArmorItem)item).getEquipmentSlot()){
        case HEAD: enchant_helmet(stack); break;
        case CHEST: enchant_armor(stack); break;
        case LEGS:  enchant_armor(stack); break;
        case FEET:  enchant_boots(stack); break;
        }
        return;
      }
      OverpoweredTechnology.log.error("ItemStack used as input for "+ArmorEffects.class.getName()+".enchant() is not an armor Item.");
    }
  }

  private static final void enchant_helmet(final ItemStack stack){
    final Random random = new Random();
    switch(random.nextInt(max_levels)){
    case 0:
      final EnchantPair[] enchantments = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,1),
        new EnchantPair(Enchantments.BLAST_PROTECTION,1),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,1),
        new EnchantPair(Enchantments.FIRE_PROTECTION,1)
      };
      add_enchantments(stack, enchantments, 1);
      break;
    case 1:
      final EnchantPair[] level_3 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,2),
        new EnchantPair(Enchantments.BLAST_PROTECTION,2),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,2),
        new EnchantPair(Enchantments.FIRE_PROTECTION,2),
        new EnchantPair(Enchantments.THORNS,1),
        new EnchantPair(Enchantments.RESPIRATION,1)
      };
      add_enchantments(stack, level_3, 1);
      break;
    case 2:
      final EnchantPair[] level_4 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,3),
        new EnchantPair(Enchantments.BLAST_PROTECTION,3),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,3),
        new EnchantPair(Enchantments.FIRE_PROTECTION,3),
        new EnchantPair(Enchantments.THORNS,2),
        new EnchantPair(Enchantments.RESPIRATION,2)
      };
      add_enchantments(stack, level_4, random.nextInt(2)+1);
      break;
    case 3:
      final EnchantPair[] level_5 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,4),
        new EnchantPair(Enchantments.BLAST_PROTECTION,4),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,4),
        new EnchantPair(Enchantments.FIRE_PROTECTION,4),
        new EnchantPair(Enchantments.THORNS,3),
        new EnchantPair(Enchantments.RESPIRATION,3),
        new EnchantPair(Enchantments.AQUA_AFFINITY,1)
      };
      add_enchantments(stack, level_5, 2);
      break;
    }
  }
  
  private static final void enchant_armor(final ItemStack stack){
    final Random random = new Random();
    switch(random.nextInt(max_levels)){
    case 0:
      final EnchantPair[] enchantments = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,1),
        new EnchantPair(Enchantments.BLAST_PROTECTION,1),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,1),
        new EnchantPair(Enchantments.FIRE_PROTECTION,1)
      };
      add_enchantments(stack, enchantments, 1);
      break;
    case 1:
      final EnchantPair[] level_3 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,2),
        new EnchantPair(Enchantments.BLAST_PROTECTION,2),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,2),
        new EnchantPair(Enchantments.FIRE_PROTECTION,2),
        new EnchantPair(Enchantments.THORNS,1)
      };
      add_enchantments(stack, level_3, 1);
      break;
    case 2:
      final EnchantPair[] level_4 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,3),
        new EnchantPair(Enchantments.BLAST_PROTECTION,3),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,3),
        new EnchantPair(Enchantments.FIRE_PROTECTION,3),
        new EnchantPair(Enchantments.THORNS,2)
      };
      add_enchantments(stack, level_4, random.nextInt(2)+1);
      break;
    case 3:
      final EnchantPair[] level_5 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,4),
        new EnchantPair(Enchantments.BLAST_PROTECTION,4),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,4),
        new EnchantPair(Enchantments.FIRE_PROTECTION,4),
        new EnchantPair(Enchantments.THORNS,3)
      };
      add_enchantments(stack, level_5, 2);
      break;
    }
  }
  
  private static final void enchant_boots(final ItemStack stack){
    final Random random = new Random();
    switch(random.nextInt(max_levels)){
    case 0:
      final EnchantPair[] enchantments = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,1),
        new EnchantPair(Enchantments.BLAST_PROTECTION,1),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,1),
        new EnchantPair(Enchantments.FIRE_PROTECTION,1),
        new EnchantPair(Enchantments.FEATHER_FALLING,1)
      };
      add_enchantments(stack, enchantments, 1);
      break;
    case 1:
      final EnchantPair[] level_3 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,2),
        new EnchantPair(Enchantments.BLAST_PROTECTION,2),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,2),
        new EnchantPair(Enchantments.FIRE_PROTECTION,2),
        new EnchantPair(Enchantments.THORNS,1),
        new EnchantPair(Enchantments.FEATHER_FALLING,2)
      };
      add_enchantments(stack, level_3, 1);
      break;
    case 2:
      final EnchantPair[] level_4 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,3),
        new EnchantPair(Enchantments.BLAST_PROTECTION,3),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,3),
        new EnchantPair(Enchantments.FIRE_PROTECTION,3),
        new EnchantPair(Enchantments.THORNS,2),
        new EnchantPair(Enchantments.FEATHER_FALLING,3),
        new EnchantPair(Enchantments.DEPTH_STRIDER,1)
      };
      add_enchantments(stack, level_4, random.nextInt(2)+1);
      break;
    case 3:
      final EnchantPair[] level_5 = new EnchantPair[] {
        new EnchantPair(Enchantments.PROTECTION,4),
        new EnchantPair(Enchantments.BLAST_PROTECTION,4),
        new EnchantPair(Enchantments.PROJECTILE_PROTECTION,4),
        new EnchantPair(Enchantments.FIRE_PROTECTION,4),
        new EnchantPair(Enchantments.THORNS,3),
        new EnchantPair(Enchantments.FEATHER_FALLING,4),
        new EnchantPair(Enchantments.DEPTH_STRIDER,3),
        new EnchantPair(Enchantments.FROST_WALKER,1)
      };
      add_enchantments(stack, level_5, 2);
      break;
    }
  }

  private static final void add_enchantments(final ItemStack stack, final EnchantPair[] enchantments, final int number_of_enchantments){
    final ArrayList<EnchantPair> list = new ArrayList<>(Arrays.asList(enchantments));
    final Random random = new Random();
    int i = 0;
    int random_index;
    EnchantPair enchantment;
    while(i < number_of_enchantments){
      random_index = random.nextInt(list.size());
      enchantment = list.get(random_index);
      if(EnchantmentUtil.check_conflicts(enchantment.enchantment, stack) == false){
        stack.addEnchantment(enchantment.enchantment, random.nextInt(enchantment.level)+1);
        i += 1;
      }
      list.remove(random_index);
      if(list.size() == 0){
        break;
      }
    }
  }

}
