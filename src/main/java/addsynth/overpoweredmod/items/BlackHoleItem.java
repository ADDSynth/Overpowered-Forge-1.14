package addsynth.overpoweredmod.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public final class BlackHoleItem extends ItemBlock {

  public BlackHoleItem(final Block block){
    super(block);
  }

  @Override
  public EnumRarity getForgeRarity(final ItemStack stack){
    return EnumRarity.EPIC;
  }

}
