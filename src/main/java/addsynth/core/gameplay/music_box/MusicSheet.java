package addsynth.core.gameplay.music_box;

import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.util.game.MessageUtil;
import addsynth.core.util.player.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Man did I have trouble with this one. I don't feel like explaining how it works right now.
 * Just run in debug mode and step through the code yourself to see how it works.
 * @see net.minecraft.block.TNTBlock#onBlockActivated
 * @see net.minecraft.item.BucketItem
 * @see net.minecraft.item.EnderEyeItem
 */
@SuppressWarnings("resource")
public final class MusicSheet extends CoreItem {

  // See how vanilla handles the Ender Eye, an item that is used on blocks and by istelf.

  public MusicSheet(final String name){
    super(name);
  }

  @Override
  public final ActionResult<ItemStack> onItemRightClick(final World world, final PlayerEntity player, final Hand hand){
    final ItemStack stack = player.getHeldItemMainhand();
    final RayTraceResult raytrace = rayTrace(world, player, FluidMode.NONE);

    // if we're hitting block
    if(raytrace.getType() == RayTraceResult.Type.BLOCK){
      if(world.getBlockState(((BlockRayTraceResult)raytrace).getPos()).getBlock() == Core.music_box){
        return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
      }
    }
    
    player.setActiveHand(hand);
    if(world.isRemote == false){
      if(player.isSneaking()){
        stack.setTag(null);
        MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.clear");
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
      }
    }

    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
  }

  @Override
  public final ActionResultType onItemUse(final ItemUseContext context){
    final World world = context.getWorld();
    final BlockPos pos = context.getPos();
    if(world.getBlockState(pos).getBlock() == Core.music_box){
      if(world.isRemote){
        return ActionResultType.SUCCESS;
      }
      final PlayerEntity player = context.getPlayer();
      final ItemStack stack = context.getItem();
      final TileMusicBox tile = (TileMusicBox)world.getTileEntity(pos);
      if(tile != null && player != null){
        if(player.isSneaking() == false){
          final CompoundNBT nbt = stack.getTag();
          if(nbt != null){
            tile.getMusicGrid().load_from_nbt(nbt);
            tile.update_data();
            MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.paste");
            return ActionResultType.SUCCESS;
          }
        }
        // is sneaking
        copy_music_data(stack, player, tile);
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.PASS;
  }

  private static final void copy_music_data(final ItemStack stack, final PlayerEntity player, final TileMusicBox tile){
    final CompoundNBT nbt = new CompoundNBT();
    tile.getMusicGrid().save_to_nbt(nbt);
      
    if(stack.getCount() == 1){
      stack.setTag(nbt);
    }
    else{
      stack.shrink(1);
      final ItemStack music_sheet = new ItemStack(Core.music_sheet, 1);
      music_sheet.setTag(nbt);
      PlayerUtil.add_to_player_inventory(player, music_sheet);
    }
      
    MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.copy");
  }

}
