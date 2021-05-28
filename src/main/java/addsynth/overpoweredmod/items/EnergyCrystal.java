package addsynth.overpoweredmod.items;

import java.text.NumberFormat;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.overpoweredmod.config.MachineValues;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public final class EnergyCrystal extends OverpoweredItem {

  public EnergyCrystal(String name){
    super(name);
  }

  @Override
  public final boolean hasEffect(ItemStack stack){
    return true;
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent(TextFormatting.AQUA+NumberFormat.getIntegerInstance().format(MachineValues.energy_crystal_energy.get()) + " Energy"));
  }

}
