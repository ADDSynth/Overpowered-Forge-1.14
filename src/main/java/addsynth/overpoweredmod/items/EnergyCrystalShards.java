package addsynth.overpoweredmod.items;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.overpoweredmod.config.MachineValues;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public final class EnergyCrystalShards extends OverpoweredItem {

  public EnergyCrystalShards(String name){
    super(name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent(MachineValues.energy_crystal_shards_energy.get().toString() + " Energy"));
  }

}
