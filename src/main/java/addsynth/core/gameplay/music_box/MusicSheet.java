package addsynth.core.gameplay.music_box;

import addsynth.core.items.ItemUtility;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.CoreItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
  public final ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand){
    ActionResult<ItemStack> result = null;
    final ItemStack stack = player.getHeldItemMainhand();
    if(world.isRemote == false){
      final RayTraceResult raytrace = this.rayTrace(world, player, false);
      if(raytrace != null){
        if(raytrace.typeOfHit == RayTraceResult.Type.BLOCK){
          if(world.getBlockState(raytrace.getBlockPos()).getBlock() == Core.music_box){
            result = new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
          }
        }
      }
      if(result == null){
        if(player.isSneaking()){
          stack.setTagCompound(null);
          player.sendMessage(new TextComponentString("Music Sheet cleared."));
          result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
      }
    }
    return result == null ? new ActionResult<ItemStack>(EnumActionResult.PASS, stack) : result;
  }

  @Override // When a player Right-clicks on a block while holding this item.
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
    final ItemStack stack = player.getHeldItemMainhand();
    if(world.getBlockState(pos).getBlock() == Core.music_box){
      if(world.isRemote){
        return EnumActionResult.FAIL;
      }
      final TileMusicBox tile = (TileMusicBox)world.getTileEntity(pos);
      if(tile != null){
        if(player.isSneaking() == false){
          NBTTagCompound nbt = stack.getTagCompound();
          if(nbt != null){
            tile.getMusicGrid().load_from_nbt(nbt);
            tile.update_data();
            player.sendMessage(new TextComponentString("Music Sheet pasted to Music Box."));
            return EnumActionResult.SUCCESS;
          }
        }
        return copy_music_data(stack, player, tile);
      }
    }
    return EnumActionResult.PASS;
  }

  private static final EnumActionResult copy_music_data(final ItemStack stack, final EntityPlayer player, final TileMusicBox tile){
    final NBTTagCompound nbt = new NBTTagCompound();
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
    return EnumActionResult.SUCCESS;
  }

}
