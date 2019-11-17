package addsynth.core.gameplay.music_box;

import addsynth.core.items.ItemUtility;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.CoreItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Man did I have trouble with this one. I don't feel like explaining how it works right now.
 * Just run in debug mode and step through the code yourself to see how it works.
 * @see net.minecraft.server.management.PlayerInteractionManager#processRightClickBlock
 * @see net.minecraft.block.BlockTNT#onBlockActivated
 * @see net.minecraft.item.ItemBucket
 * @see net.minecraft.item.ItemEnderEye
 */
public final class MusicSheet extends CoreItem {

  public MusicSheet(String name){
    super(name);
  }

  @Override
  public final ActionResult<ItemStack> onItemRightClick(final World world, final PlayerEntity player, final Hand hand){
    ActionResult<ItemStack> result = null;
    final ItemStack stack = player.getHeldItemMainhand();
    if(world.isRemote == false){
      final RayTraceResult raytrace = this.rayTrace(world, player, false);
      if(raytrace != null){
        if(raytrace.typeOfHit == RayTraceResult.Type.BLOCK){
          if(world.getBlockState(raytrace.getBlockPos()).getBlock() == Core.music_box){
            result = new ActionResult<ItemStack>(ActionResultType.PASS, stack);
          }
        }
      }
      if(result == null){
        if(player.isSneaking()){
          stack.put(null);
          player.sendMessage(new TextComponentString("Music Sheet cleared."));
          result = new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
        }
      }
    }
    return result == null ? new ActionResult<ItemStack>(ActionResultType.PASS, stack) : result;
  }

  @Override // When a player Right-clicks on a block while holding this item.
  public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ){
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
            player.sendMessage(new TextComponentString("Music Sheet pasted to Music Box."));
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
      stack.setTagCompound(nbt);
    }
    else{
      stack.shrink(1);
      final ItemStack music_sheet = new ItemStack(Core.music_sheet,1);
      music_sheet.setTagCompound(nbt);
      ItemUtility.add_to_player_inventory(player, music_sheet);
    }
      
    player.sendMessage(new TextComponentString("Music data copied to Music Sheet."));
    return ActionResultType.SUCCESS;
  }

}
