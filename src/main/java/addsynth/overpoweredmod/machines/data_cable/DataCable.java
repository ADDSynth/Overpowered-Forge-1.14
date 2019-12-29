package addsynth.overpoweredmod.machines.data_cable;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.blocks.Wire;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class DataCable extends Wire {

  private static final double min_wire_size =  5.5 / 16;
  private static final double max_wire_size = 10.5 / 16;

  static {
    if(Machines.portal_frame == null || Machines.portal_control_panel == null ||
       Machines.fusion_control_unit == null || Machines.fusion_converter == null){
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
    Machines.fusion_control_unit,
    Machines.fusion_converter,
    Init.iron_frame_block
  };

  public DataCable(final String name){
    super(Block.Properties.create(Material.GOURD, MaterialColor.WOOL).hardnessAndResistance(0.1f, 0.0f), min_wire_size, max_wire_size);
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.machines_creative_tab));
    valid_blocks[0] = this;
  }

  // The world may contain multiple BLOCK Objects, but those are internal, and they all reference the
  // SAME Block Type! That's what we're checking with the == operator below, not if they are the same
  // Object, but if the Block Type that block is using is the same one we register in Init class!
  @Override
  protected final ArrayList<Direction> get_valid_sides(final IBlockReader world, final BlockPos pos){
    final ArrayList<Direction> valid_sides = new ArrayList<>(6);
    Block block;
    for(Direction side : Direction.values()){
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
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileDataCable();
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    if(world.isRemote == false){
      final BlockNetwork network = ((IBlockNetworkUser)(world.getTileEntity(pos))).getNetwork();
      network.neighbor_was_changed(pos, neighbor);
    }
  }

  @Override
  public final void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
    super.onReplaced(state, world, pos, newState, isMoving);
    BlockNetwork.block_was_broken(network, world, pos, this);
  }

}
