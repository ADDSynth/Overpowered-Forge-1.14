package addsynth.core.gameplay.blocks;

import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ToolType;

public final class TrophyBlock extends Block implements IWaterLoggable {

  private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
  private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final VoxelShape collision_box = VoxelShapes.create(0.125, 0.0, 0.125, 0.875, 0.9375, 0.875);

  public TrophyBlock(String name){
    super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().group(ADDSynthCore.creative_tab));
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(final BlockItemUseContext context){
    final IWorld world = context.getWorld();
    final BlockPos position  = context.getPos();
    return getDefaultState()
      .with(FACING, context.getPlacementHorizontalFacing())
      .with(WATERLOGGED, world.getFluidState(position).getFluid() == Fluids.WATER);
  }

  @Override
  @SuppressWarnings("deprecation")
  public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos){
    if(state.get(WATERLOGGED)){
      world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return state;
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return collision_box;
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return collision_box;
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
  protected void fillStateContainer(final Builder<Block, BlockState> builder){
    builder.add(FACING, WATERLOGGED);
  }

}
