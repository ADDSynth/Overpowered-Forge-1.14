package addsynth.overpoweredmod.blocks.tiles.laser;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class LaserHousing extends MachineBlockTileEntity {

  public LaserHousing(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 2 Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileLaserHousing();
  }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos, IBlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.LASER_HOUSING, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor){
    if(world.isRemote == false){
      final BlockNetwork network = ((IBlockNetworkUser)(world.getTileEntity(pos))).getNetwork();
      network.neighbor_was_changed(pos, neighbor);
    }
  }

  @Override
  public final void breakBlock(final World world, final BlockPos pos, final IBlockState state){
    // MAYBE I should encorporate this into MachineBlockTileEntity as well?
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
    super.breakBlock(world, pos, state);
    BlockNetwork.block_was_broken(network, world, pos, state.getBlock());
  }

}
