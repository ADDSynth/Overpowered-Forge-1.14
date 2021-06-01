package addsynth.overpoweredmod.items;

import java.text.NumberFormat;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.overpoweredmod.config.MachineValues;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public final class EnergyCrystalShards extends OverpoweredItem {

  public EnergyCrystalShards(String name){
    super(name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    final String energy = NumberFormat.getIntegerInstance().format(MachineValues.energy_crystal_shards_energy.get());
    tooltip.add(new TranslationTextComponent("gui.addsynth_energy.tooltip.energy", energy).applyTextStyle(TextFormatting.AQUA));
  }

}
