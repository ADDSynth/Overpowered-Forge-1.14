package addsynth.overpoweredmod.machines.black_hole;

import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public final class BlackHoleItem extends BlockItem {

  public BlackHoleItem(final Block block){
    super(block, new Item.Properties().group(CreativeTabs.creative_tab).rarity(Rarity.EPIC));
  }

  @Override
  public final ActionResultType tryPlace(final BlockItemUseContext context){
    if(TileBlackHole.is_black_hole_allowed(context.getWorld())){
      return super.tryPlace(context);
    }
    final PlayerEntity player = context.getPlayer();
    if(player != null){
      player.sendMessage(new StringTextComponent("Black Holes are not allowed in this Dimension."));
    }
    return ActionResultType.FAIL;
  }

}
