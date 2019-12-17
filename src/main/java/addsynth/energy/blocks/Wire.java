package addsynth.energy.blocks;

import java.util.ArrayList;
import addsynth.core.blocks.BlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public abstract class Wire extends BlockTile implements IWaterLoggable {

  // http://mcforge.readthedocs.io/en/latest/blocks/states/

  public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
  public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
  public static final BooleanProperty WEST  = BlockStateProperties.WEST;
  public static final BooleanProperty EAST  = BlockStateProperties.EAST;
  public static final BooleanProperty UP    = BlockStateProperties.UP;
  public static final BooleanProperty DOWN  = BlockStateProperties.DOWN;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final double default_min_wire_size =  5.0 / 16;
  private static final double default_max_wire_size = 11.0 / 16;

  /**
   * This is the default bounding boxes for ALL wires. If your wire is of a different size, then you need to
   * "hide" this field by declaring your own field in subclasses with THE SAME NAME, and also declare them
   * as static, because all wires of that type use the same bounding boxes.
   */
  protected static final AxisAlignedBB bounding_box[] = new AxisAlignedBB[7];

  static {
    // Can initialize the bounding_box[] variables here.
  }

  public Wire(final Block.Properties properties){
    this(properties, default_min_wire_size, default_max_wire_size);
  }

  public Wire(final Block.Properties properties, final double min_wire_size, final double max_wire_size){
    super(properties);
    if(bounding_box[0] == null){
      set_bounding_boxes(min_wire_size, max_wire_size);
    }
    this.setDefaultState(this.stateContainer.getBaseState()
       .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false).with(UP, false).with(DOWN, false)
       .with(WATERLOGGED, false));
  }

  private static final void set_bounding_boxes(double min_wire_size, double max_wire_size){
    bounding_box[0] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[1] = new AxisAlignedBB(0, min_wire_size, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[2] = new AxisAlignedBB(min_wire_size, 0, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[3] = new AxisAlignedBB(min_wire_size, min_wire_size, 0, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[4] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, 1, max_wire_size, max_wire_size);
    bounding_box[5] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, 1, max_wire_size);
    bounding_box[6] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, max_wire_size, 1);
  }

  protected abstract ArrayList<Direction> get_valid_sides(IBlockReader world, BlockPos pos);

  /**
   * This is here because the Wire class is derived from the BlockTile class, which is derived from the BlockContainer class,
   * which implements the ITileProvider interface, and must implement the createNewTileEntity() method. However more specific
   * Wire classes that are derived from this one may not need to produce a Tile Entity, so we return null. Override this method
   * and specify a Tile Entity to spawn if you need to.
   */
  @Override
  public TileEntity createNewTileEntity(IBlockReader worldIn){
    return null;
  }

}
