package addsynth.core.gameplay.music_box;

import addsynth.core.ADDSynthCore;
import addsynth.core.blocks.BlockTile;
import addsynth.core.gameplay.Core;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class MusicBox extends BlockTile {

  public MusicBox(String name){
    super(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.8f));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().group(ADDSynthCore.creative_tab));
  }

  @Override
  public final int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side){
    return 0;
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader worldIn) {
    return new TileMusicBox();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
    if(PlayerUtil.isPlayerHoldingItem(player, Core.music_sheet)){
      return false; // let the music sheet item handle it.
    }
      final TileMusicBox tile = MinecraftUtility.getTileEntity(pos, world, TileMusicBox.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

}
