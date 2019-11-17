package addsynth.overpoweredmod.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class BlackHoleItem extends BlockItem {

  public BlackHoleItem(final Block block){
    super(block);
  }

  @Override
  public Rarity getRarity(final ItemStack stack){
    return Rarity.EPIC;
  }

}
