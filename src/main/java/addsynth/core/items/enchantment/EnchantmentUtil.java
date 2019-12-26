package addsynth.core.items.enchantment;

import java.util.Arrays;
import java.util.List;
import addsynth.core.util.JavaUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.ListNBT;

public final class EnchantmentUtil {

  public static final Enchantment[] common_armor_enchantments = new Enchantment[] {
    Enchantments.UNBREAKING,              // 3 levels
    Enchantments.PROTECTION,              // 4 levels
    Enchantments.PROJECTILE_PROTECTION,   // 4 levels
    Enchantments.BLAST_PROTECTION,        // 4 levels, reduces damage and knockback from explosions.
    Enchantments.FIRE_PROTECTION,         // 4 levels, protects against fire-based damage.
    Enchantments.THORNS,                  // 3 levels, damages your attacker, regardless if it was melee or ranged.
    Enchantments.MENDING                  // 1 level,  repairs durability with experience.
  };

  public static final Enchantment[] helmet_enchantments = new Enchantment[] {
    Enchantments.AQUA_AFFINITY,           // only 1 level, enables underwater mining.
    Enchantments.RESPIRATION              // 3 levels, increases underwater breathing time.
  };

  public static final Enchantment[] boot_enchantments = new Enchantment[] {
    Enchantments.DEPTH_STRIDER,           // 3 levels, increases underwater speed.
    Enchantments.FEATHER_FALLING,         // 4 levels, decreases fall damage.
    Enchantments.FROST_WALKER             // 2 levels, freezes water so you can walk on it.
  };

  @SuppressWarnings("incomplete-switch")
  public static final List<Enchantment> get_enchantments_for_item(final ItemStack stack){
    Enchantment[] enchantment_list = new Enchantment[0];
    if(stack != null){
      final Item item = stack.getItem();
      if(item instanceof ArmorItem){
        switch(((ArmorItem)item).getEquipmentSlot()){
        case HEAD:  enchantment_list = JavaUtils.combine_arrays(common_armor_enchantments, helmet_enchantments); break;
        case CHEST: enchantment_list = common_armor_enchantments; break;
        case LEGS:  enchantment_list = common_armor_enchantments; break;
        case FEET:  enchantment_list = JavaUtils.combine_arrays(common_armor_enchantments, boot_enchantments); break;
      }
      if(item instanceof SwordItem){}
      if(item instanceof ShovelItem){}
      if(item instanceof PickaxeItem){}
      if(item instanceof AxeItem){}
      if(item instanceof HoeItem){}
      if(item instanceof ShearsItem){}
      if(item instanceof FishingRodItem){}
      if(item instanceof BowItem){}
      }
    }
    return Arrays.asList(enchantment_list);
  }

  /** @see ItemStack#addEnchantment(Enchantment, int) */
  public static final boolean does_item_have_enchantment(final ItemStack stack, final Enchantment[] enchantments){
    if(stack.isEnchanted()){
      final ListNBT enchantment_tag_list = stack.getEnchantmentTagList();
      int i;
      String id;
      for(i = 0; i < enchantment_tag_list.size(); i++){
        id = enchantment_tag_list.getCompound(i).getString("id");
        for(Enchantment enchantment : enchantments){
          // FIX
          if(enchantment.getRegistryName() != null){
            if(id.equals(enchantment.getRegistryName().getPath())){
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public static final boolean does_item_have_enchantment(final ItemStack stack, final Enchantment enchantment){
    return does_item_have_enchantment(stack, new Enchantment[] {enchantment});
  }

  /** This will check an ItemStack if it already has an enchantment that conflicts with the one you're
   *  trying to apply. Certain Enchantments are deemed "mutually exclusive" by Mojang.
   * @param enchantment
   * @param stack
   * @return
   */
  public static final boolean check_conflicts(final Enchantment enchantment, final ItemStack stack){
    if(enchantment == Enchantments.FROST_WALKER){
      return does_item_have_enchantment(stack, Enchantments.DEPTH_STRIDER);
    }
    if(enchantment == Enchantments.DEPTH_STRIDER){
      return does_item_have_enchantment(stack, Enchantments.FROST_WALKER);
    }
    if(enchantment == Enchantments.PROTECTION){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.BLAST_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.FIRE_PROTECTION});
    }
    if(enchantment == Enchantments.PROJECTILE_PROTECTION){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.PROTECTION, Enchantments.BLAST_PROTECTION, Enchantments.FIRE_PROTECTION});
    }
    if(enchantment == Enchantments.BLAST_PROTECTION){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.FIRE_PROTECTION});
    }
    if(enchantment == Enchantments.FIRE_PROTECTION){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.BLAST_PROTECTION});
    }
    if(enchantment == Enchantments.MENDING){
      return does_item_have_enchantment(stack, Enchantments.INFINITY);
    }
    if(enchantment == Enchantments.INFINITY){
      return does_item_have_enchantment(stack, Enchantments.MENDING);
    }
    if(enchantment == Enchantments.FORTUNE){
      return does_item_have_enchantment(stack, Enchantments.SILK_TOUCH);
    }
    if(enchantment == Enchantments.SILK_TOUCH){
      return does_item_have_enchantment(stack, Enchantments.FORTUNE);
    }
    if(enchantment == Enchantments.SHARPNESS){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS});
    }
    if(enchantment == Enchantments.SMITE){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS});
    }
    if(enchantment == Enchantments.BANE_OF_ARTHROPODS){
      return does_item_have_enchantment(stack, new Enchantment[] {Enchantments.SHARPNESS, Enchantments.SMITE});
    }
    return false;
  }

}
