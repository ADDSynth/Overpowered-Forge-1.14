package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class VoidCrystal extends OverpoweredItem {

  public VoidCrystal(final String name){
    super(name);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack){
    return true;
  }

}
