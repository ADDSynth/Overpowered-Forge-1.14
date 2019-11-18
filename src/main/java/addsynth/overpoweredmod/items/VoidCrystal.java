package addsynth.overpoweredmod.items;

import net.minecraft.item.ItemStack;

public final class VoidCrystal extends OverpoweredItem {

  public VoidCrystal(final String name){
    super(name);
  }

  @Override
  public boolean hasEffect(ItemStack stack){
    return true;
  }

}
