package addsynth.core.gameplay.music_box;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.items.ItemUtil;
import addsynth.core.util.game.MessageUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Man did I have trouble with this one. I don't feel like explaining how it works right now.
 * Just run in debug mode and step through the code yourself to see how it works.
 * @see net.minecraft.server.management.PlayerInteractionManager#processRightClickBlock
 * @see net.minecraft.block.TNTBlock#onBlockActivated
 * @see net.minecraft.item.BucketItem
 * @see net.minecraft.item.EnderEyeItem
 */
public final class MusicSheet extends CoreItem {

  public MusicSheet(final String name){
    super(new Item.Properties().group(ADDSynthCore.creative_tab), name);
  }

  @Override
  public final ActionResult<ItemStack> onItemRightClick(final World world, final PlayerEntity player, final Hand hand){
    ActionResult<ItemStack> result = null;
    final ItemStack stack = player.getHeldItemMainhand();
    if(world.isRemote == false){
      final RayTraceResult raytrace = rayTrace(world, player, FluidMode.NONE);
      if(raytrace != null){
        if(raytrace.getType() == RayTraceResult.Type.BLOCK){
          if(world.getBlockState((new BlockPos(raytrace.getHitVec()))).getBlock() == Core.music_box){
            result = new ActionResult<ItemStack>(ActionResultType.PASS, stack);
          }
        }
      }
      if(result == null){
        if(player.isSneaking()){
          stack.setTag(null);
          MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.clear");
          result = new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
        }
      }
    }
    return result == null ? new ActionResult<ItemStack>(ActionResultType.PASS, stack) : result;
  }

  @Override
  public ActionResultType onItemUse(final ItemUseContext context){
    final PlayerEntity player = context.getPlayer();
    final World world = context.getWorld();
    final BlockPos pos = new BlockPos(context.getHitVec());
    final ItemStack stack = player.getHeldItemMainhand();
    if(world.getBlockState(pos).getBlock() == Core.music_box){
      if(world.isRemote){
        return ActionResultType.FAIL;
      }
      final TileMusicBox tile = (TileMusicBox)world.getTileEntity(pos);
      if(tile != null){
        if(player.isSneaking() == false){
          CompoundNBT nbt = stack.getTag();
          if(nbt != null){
            tile.getMusicGrid().load_from_nbt(nbt);
            tile.update_data();
            MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.paste");
            return ActionResultType.SUCCESS;
          }
        }
        return copy_music_data(stack, player, tile);
      }
    }
    return ActionResultType.PASS;
  }

  private static final ActionResultType copy_music_data(final ItemStack stack, final PlayerEntity player, final TileMusicBox tile){
    final CompoundNBT nbt = new CompoundNBT();
    tile.getMusicGrid().save_to_nbt(nbt);
      
    if(stack.getCount() == 1){
      stack.setTag(nbt);
    }
    else{
      stack.shrink(1);
      final ItemStack music_sheet = new ItemStack(Core.music_sheet,1);
      music_sheet.setTag(nbt);
      ItemUtil.add_to_player_inventory(player, music_sheet);
    }
      
    MessageUtil.send_to_player(player, "gui.addsynthcore.music_sheet.copy");
    return ActionResultType.SUCCESS;
  }

}
