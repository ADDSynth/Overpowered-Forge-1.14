package addsynth.overpoweredmod.items;

import addsynth.core.items.ArmorMaterial;
import addsynth.core.items.EquipmentType;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class UnidentifiedItem extends OverpoweredItem {

  public final ArmorMaterial armor_material;
  public final EquipmentType equipment_type;

  public UnidentifiedItem(final ArmorMaterial material, final EquipmentType type){
    super("unidentified_"+material.name+"_"+type.name, OverpoweredMod.tools_creative_tab);
    this.armor_material = material;
    this.equipment_type = type;
  }

  @Override
  public final ITextComponent getDisplayName(final ItemStack stack){
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.ITALIC);
  }

}
