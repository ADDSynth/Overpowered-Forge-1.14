package addsynth.overpoweredmod.blocks.tiles.portal;

import addsynth.core.util.MinecraftUtility;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class PortalFrame extends MachineBlockTileEntity {

  public PortalFrame(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader worldIn){
    return new TilePortalFrame();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TilePortalFrame tile = MinecraftUtility.getTileEntity(pos, world, TilePortalFrame.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

}
