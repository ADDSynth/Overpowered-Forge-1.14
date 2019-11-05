package addsynth.overpoweredmod.blocks.tiles;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.blocks.Wire;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.TileDataCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class DataCable extends Wire {

  private static final double min_wire_size =  5.5 / 16;
  private static final double max_wire_size = 10.5 / 16;

  static {
    if(Machines.portal_frame == null || Machines.portal_control_panel == null ||
       Machines.laser_scanning_unit == null || Machines.fusion_converter == null){
      throw new NullPointerException("The Data Cable instance should be declared after the "+
         "Portal Frame, Portal Control Panel, Fusion Converter, and Laser Scanning Unit variables.");
    }
  }

  // If any of these variables are null it's because you're trying to get variables from the Machines class
  // that haven't been initialized yet. Move the initialization of the DataCable block variable til AFTER
  // the required variables have been initialized.
  private static final Block[] valid_blocks = new Block[] {
    null,
    Machines.portal_frame,
    Machines.portal_control_panel,
    Machines.laser_scanning_unit,
    Machines.fusion_converter,
    Init.iron_frame_block
  };

  public DataCable(final String name){
    super(Material.GOURD, MapColor.CLOTH, min_wire_size, max_wire_size);
    OverpoweredMod.registry.register_block(this, name);
    setHardness(0.1f);
    valid_blocks[0] = this;
  }

  // The world may contain multiple BLOCK Objects, but those are internal, and they all reference the
  // SAME Block Type! That's what we're checking with the == operator below, not if they are the same
  // Object, but if the Block Type that block is using is the same one we register in Init class!
  @Override
  protected final ArrayList<EnumFacing> get_valid_sides(final IBlockAccess world, final BlockPos pos){
    final ArrayList<EnumFacing> valid_sides = new ArrayList<>(6);
    Block block;
    for(EnumFacing side : EnumFacing.values()){
      block = world.getBlockState(pos.offset(side)).getBlock();
      for(Block test_block : valid_blocks){
        if(block == test_block){
          valid_sides.add(side);
          break;
        }
      }
    }
    return valid_sides;
  }

  @Override
  public final TileEntity createNewTileEntity(final World worldIn, final int meta){
    return new TileDataCable();
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
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
    super.breakBlock(world, pos, state);
    BlockNetwork.block_was_broken(network, world, pos, this);
  }

}
