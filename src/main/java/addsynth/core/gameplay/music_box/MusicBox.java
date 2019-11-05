package addsynth.core.gameplay.music_box;

import addsynth.core.ADDSynthCore;
import addsynth.core.blocks.BlockTile;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.GuiHandler;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class MusicBox extends BlockTile {

  public MusicBox(String name){
    super(Material.GROUND, MapColor.WOOD);
    ADDSynthCore.registry.register_block(this, name);
    setHardness(0.8f);
    setResistance(5.0f);
  }

  @Override
  public final boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
    return false;
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileMusicBox();
  }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing side,float xHit,float yHit,float zHit){
    if(world.isRemote == false){
      final ItemStack heldItem = player.getHeldItemMainhand(); // TEST OPTIMIZE can this code be moved into the Music_Sheet.onUseItemFirst() function? for all versions?
      if(heldItem != null){
        if(heldItem.getItem() == Core.music_sheet){
          return false; // let the music sheet item handle it.
        }
      }
      player.openGui(ADDSynthCore.instance,GuiHandler.MUSIC_BOX, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
