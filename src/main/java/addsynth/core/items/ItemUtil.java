package addsynth.core.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public final class ItemUtil {

  public static final boolean itemStackExists(final ItemStack stack){
    if(stack == null){   return false; }
    if(stack.isEmpty()){ return false; }
    return true;
  }

  public static final boolean isItemStackEmpty(final ItemStack stack){
    return stack == null ? true : stack.isEmpty();
  }

  /**
   * Used in Items to get the {@link CompoundNBT} of ItemStacks.
   * Used in functions like <code>onItemUse()</code> or <code>onItemRightClick()</code>.
   * Then call {@link ItemStack#setTag(CompoundNBT)} to save any changes.
   * @param stack
   * @return NBTTag if one exists, otherwise a new CompoundNBT
   */
  public static final CompoundNBT getItemStackNBT(final ItemStack stack) throws NullPointerException {
    CompoundNBT nbt = stack.getTag();
    return nbt == null ? new CompoundNBT() : nbt;
  }

  public static final void add_to_player_inventory(final PlayerEntity player, final ItemStack stack){
    if(player.inventory.addItemStackToInventory(stack) == false){
      player.dropItem(stack, false);
    }
  }

  public static final Item get_armor(final ArmorMaterial armor_material, final EquipmentType equipment_type){
    Item armor = null;
    switch(armor_material){
    case LEATHER:
      switch(equipment_type){
      case HELMET:     armor = Items.LEATHER_HELMET; break;
      case CHESTPLATE: armor = Items.LEATHER_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.LEATHER_LEGGINGS; break;
      case BOOTS:      armor = Items.LEATHER_BOOTS; break;
      }
      break;
    case CHAINMAIL:
      switch(equipment_type){
      case HELMET:     armor = Items.CHAINMAIL_HELMET; break;
      case CHESTPLATE: armor = Items.CHAINMAIL_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.CHAINMAIL_LEGGINGS; break;
      case BOOTS:      armor = Items.CHAINMAIL_BOOTS; break;
      }
      break;
    case IRON:
      switch(equipment_type){
      case HELMET:     armor = Items.IRON_HELMET; break;
      case CHESTPLATE: armor = Items.IRON_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.IRON_LEGGINGS; break;
      case BOOTS:      armor = Items.IRON_BOOTS; break;
      }
      break;
    case GOLD:
      switch(equipment_type){
      case HELMET:     armor = Items.GOLDEN_HELMET; break;
      case CHESTPLATE: armor = Items.GOLDEN_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.GOLDEN_LEGGINGS; break;
      case BOOTS:      armor = Items.GOLDEN_BOOTS; break;
      }
      break;
    case DIAMOND:
      switch(equipment_type){
      case HELMET:     armor = Items.DIAMOND_HELMET; break;
      case CHESTPLATE: armor = Items.DIAMOND_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.DIAMOND_LEGGINGS; break;
      case BOOTS:      armor = Items.DIAMOND_BOOTS; break;
      }
      break;
    }
    return armor;
  }

}
