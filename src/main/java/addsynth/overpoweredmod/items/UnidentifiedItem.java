package addsynth.overpoweredmod.items;

import addsynth.core.items.ArmorMaterial;
import addsynth.core.items.EquipmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public final class UnidentifiedItem extends OverpoweredItem {

  public final ArmorMaterial armor_material;
  public final EquipmentType equipment_type;

  public UnidentifiedItem(final ArmorMaterial material, final EquipmentType type){
    super("unidentified_"+material.name+"_"+type.name);
    this.armor_material = material;
    this.equipment_type = type;
  }

  @Override
  public final String getItemStackDisplayName(final ItemStack stack){
    return TextFormatting.ITALIC.toString() + super.getItemStackDisplayName(stack);
  }

}
