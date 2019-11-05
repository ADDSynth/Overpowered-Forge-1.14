package addsynth.overpoweredmod.compatability.baubles;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.items.OverpoweredItem;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface="baubles.api.IBauble", modid=Compatability.BAUBLES.modid)
public final class DimensionAnchor extends OverpoweredItem implements IBauble {

  public DimensionAnchor(final String name){
    super(name);
  }

  @Override
  @Optional.Method(modid=Compatability.BAUBLES.modid)
  public BaubleType getBaubleType(ItemStack itemstack){
    return BaubleType.CHARM;
  }

}
