package addsynth.energy.lib.blocks;

import javax.annotation.Nullable;
import addsynth.core.util.block.BlockShape;
import addsynth.core.util.constants.DirectionConstant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public abstract class Wire extends Block implements IWaterLoggable {

  // http://mcforge.readthedocs.io/en/latest/blocks/states/

  private static final BooleanProperty NORTH = BlockStateProperties.NORTH;
  private static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
  private static final BooleanProperty WEST  = BlockStateProperties.WEST;
  private static final BooleanProperty EAST  = BlockStateProperties.EAST;
  private static final BooleanProperty UP    = BlockStateProperties.UP;
  private static final BooleanProperty DOWN  = BlockStateProperties.DOWN;
  private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final double default_min_wire_size =  5.0 / 16;
  private static final double default_max_wire_size = 11.0 / 16;

  protected final VoxelShape[] shapes;

  public Wire(final Block.Properties properties){
    super(properties);
    shapes = makeShapes();
    this.setDefaultState(this.stateContainer.getBaseState()
       .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false).with(UP, false).with(DOWN, false)
       .with(WATERLOGGED, false));
  }

  /** Override this method in extended classes to assign different size shapes.
   *  The base Wire class automatically calls this to assign the shapes array.
   */
  protected VoxelShape[] makeShapes(){
    return BlockShape.create_six_sided_binary_voxel_shapes(default_min_wire_size, default_max_wire_size);
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  @SuppressWarnings("resource")
  public final BlockState getStateForPlacement(final BlockItemUseContext context){
    final IWorld world = context.getWorld();
    final BlockPos position  = context.getPos();
    final boolean[] valid_sides = get_valid_sides(world, position);
    return getState(getDefaultState(), valid_sides, world, position);
  }

  protected abstract boolean[] get_valid_sides(IBlockReader world, BlockPos pos);

  private static final BlockState getState(final BlockState state, final boolean[] valid_sides, final IWorld world, final BlockPos position){
    final boolean down  = valid_sides[DirectionConstant.DOWN];
    final boolean up    = valid_sides[DirectionConstant.UP];
    final boolean north = valid_sides[DirectionConstant.NORTH];
    final boolean south = valid_sides[DirectionConstant.SOUTH];
    final boolean west  = valid_sides[DirectionConstant.WEST];
    final boolean east  = valid_sides[DirectionConstant.EAST];
    final boolean water = world.getFluidState(position).getFluid() == Fluids.WATER;
    return state.with(DOWN, down).with(UP, up).with(NORTH, north).with(SOUTH, south).with(WEST, west).with(EAST, east).with(WATERLOGGED, water);
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[BlockShape.getIndex(state)];
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[BlockShape.getIndex(state)];
  }

  @Override
  @SuppressWarnings("deprecation")
  public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos){
    if(state.get(WATERLOGGED)){
      world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return getState(state, get_valid_sides(world, currentPos), world, currentPos);
  }

  @Override
  public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos){
    return !state.get(WATERLOGGED);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IFluidState getFluidState(BlockState state){
    return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
  }

  @Override
  protected void fillStateContainer(Builder<Block, BlockState> builder){
    builder.add(NORTH, SOUTH, WEST, EAST, UP, DOWN, WATERLOGGED);
  }

}
