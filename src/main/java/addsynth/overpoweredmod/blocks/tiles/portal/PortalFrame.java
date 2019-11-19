package addsynth.overpoweredmod.blocks.tiles.portal;

import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

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
      player.openGui(OverpoweredMod.instance,GuiHandler.PORTAL_FRAME, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
