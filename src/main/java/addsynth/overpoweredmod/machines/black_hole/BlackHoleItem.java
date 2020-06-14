package addsynth.overpoweredmod.machines.black_hole;

import addsynth.core.util.MessageUtil;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

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
      MessageUtil.send_to_player(player, "gui.overpowered.black_hole.not_allowed_in_this_dimension");
    }
    return ActionResultType.FAIL;
  }

}
