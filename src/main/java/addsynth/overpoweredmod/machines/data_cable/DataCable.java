package addsynth.overpoweredmod.machines.data_cable;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.block.BlockShape;
import addsynth.energy.lib.blocks.Wire;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class DataCable extends Wire {

  private static final double min_wire_size =  5.5 / 16;
  private static final double max_wire_size = 10.5 / 16;

  private static final ArrayList<Block> valid_blocks = new ArrayList<>(6);

  /** Call this during the constructor of blocks to add that block to the list of blocks the
   *  Data Cable can connect to. This way they can be initialized in any order.
   *  @see addsynth.overpoweredmod.machines.portal.frame.PortalFrameBlock
   *  @see addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanelBlock
   *  @see addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit
   *  @see addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverterBlock
   *  @see addsynth.overpoweredmod.blocks.IronFrameBlock
   */
  public static final void addAttachableBlock(final Block block){
    if(valid_blocks.contains(block) == false){
      valid_blocks.add(block);
    }
  }

  public DataCable(final String name){
    super(Block.Properties.create(Material.GOURD, MaterialColor.WOOL).hardnessAndResistance(0.1f, 0.0f));
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
    valid_blocks.add(this);
  }

  @Override
  protected VoxelShape[] makeShapes(){
    return BlockShape.create_six_sided_binary_voxel_shapes(min_wire_size, max_wire_size);
  }

  // The world may contain multiple BLOCK Objects, but those are internal, and they all reference the
  // SAME Block Type! That's what we're checking with the == operator below, not if they are the same
  // Object, but if the Block Type that block is using is the same one we register in Init class!
  @Override
  protected final boolean[] get_valid_sides(final IBlockReader world, final BlockPos pos){
    final boolean[] valid_sides = new boolean[6];
    Block block;
    for(Direction side : Direction.values()){
      block = world.getBlockState(pos.offset(side)).getBlock();
      valid_sides[side.ordinal()] = valid_blocks.contains(block);
    }
    return valid_sides;
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TileDataCable();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

}
