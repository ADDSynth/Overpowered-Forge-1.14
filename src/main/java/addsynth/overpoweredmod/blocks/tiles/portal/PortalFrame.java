package addsynth.overpoweredmod.blocks.tiles.portal;

import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class PortalFrame extends MachineBlockTileEntity {

  public PortalFrame(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta){
    return new TilePortalFrame();
  }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing side,float xHit,float yHit,float zHit){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.PORTAL_FRAME, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
