package addsynth.overpoweredmod.machines.laser.cannon;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.Constants;
import addsynth.core.blocks.BlockTile;
import addsynth.core.util.BlockUtil;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public final class LaserCannon extends BlockTile implements IWaterLoggable {

  public static final DirectionProperty FACING = BlockStateProperties.FACING; // Data Cable uses this block property.
  private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  
  public final int color;

  private static final VoxelShape[] shapes = create_laser_cannon_shapes();

  private static final VoxelShape[] create_laser_cannon_shapes(){
    final VoxelShape[] shape = new VoxelShape[6];
    final double[] calc = { 0.0,    1.0/16,  2.0/16,  3.0/16,  4.0/16,  5.0/16,  6.0/16,  7.0/16,
                            8.0/16, 9.0/16, 10.0/16, 11.0/16, 12.0/16, 13.0/16, 14.0/16, 15.0/16, 1.0};
    // NORTH
    VoxelShape box0 = VoxelShapes.create(calc[1], calc[1], calc[14], calc[15], calc[15], calc[16]);
    VoxelShape box1 = VoxelShapes.create(calc[3], calc[3], calc[12], calc[13], calc[13], calc[14]);
    VoxelShape box2 = VoxelShapes.create(calc[4], calc[4], calc[10], calc[12], calc[12], calc[12]);
    VoxelShape box3 = VoxelShapes.create(calc[5], calc[5], calc[8], calc[11], calc[11], calc[10]);
    VoxelShape box4 = VoxelShapes.create(calc[6], calc[6], calc[6], calc[10], calc[10], calc[8]);
    VoxelShape box5 = VoxelShapes.create(calc[7], calc[7], calc[2], calc[9], calc[9], calc[6]);
    shape[Constants.NORTH] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);
    
    // SOUTH
    box0 = VoxelShapes.create(calc[1], calc[1], calc[0], calc[15], calc[15], calc[2]);
    box1 = VoxelShapes.create(calc[3], calc[3], calc[2], calc[13], calc[13], calc[4]);
    box2 = VoxelShapes.create(calc[4], calc[4], calc[4], calc[12], calc[12], calc[6]);
    box3 = VoxelShapes.create(calc[5], calc[5], calc[6], calc[11], calc[11], calc[8]);
    box4 = VoxelShapes.create(calc[6], calc[6], calc[8], calc[10], calc[10], calc[10]);
    box5 = VoxelShapes.create(calc[7], calc[7], calc[10], calc[9], calc[9], calc[14]);
    shape[Constants.SOUTH] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);
    
    // EAST
    box0 = VoxelShapes.create(calc[0], calc[1], calc[1], calc[2], calc[15], calc[15]);
    box1 = VoxelShapes.create(calc[2], calc[3], calc[3], calc[4], calc[13], calc[13]);
    box2 = VoxelShapes.create(calc[4], calc[4], calc[4], calc[6], calc[12], calc[12]);
    box3 = VoxelShapes.create(calc[6], calc[5], calc[5], calc[8], calc[11], calc[11]);
    box4 = VoxelShapes.create(calc[8], calc[6], calc[6], calc[10], calc[10], calc[10]);
    box5 = VoxelShapes.create(calc[10], calc[7], calc[7], calc[14], calc[9], calc[9]);
    shape[Constants.EAST] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);
    
    // WEST
    box0 = VoxelShapes.create(calc[14], calc[1], calc[1], calc[16], calc[15], calc[15]);
    box1 = VoxelShapes.create(calc[12], calc[3], calc[3], calc[14], calc[13], calc[13]);
    box2 = VoxelShapes.create(calc[10], calc[4], calc[4], calc[12], calc[12], calc[12]);
    box3 = VoxelShapes.create(calc[8], calc[5], calc[5], calc[10], calc[11], calc[11]);
    box4 = VoxelShapes.create(calc[6], calc[6], calc[6], calc[8], calc[10], calc[10]);
    box5 = VoxelShapes.create(calc[2], calc[7], calc[7], calc[6], calc[9], calc[9]);
    shape[Constants.WEST] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);
    
    // UP
    box0 = VoxelShapes.create(calc[1], calc[0], calc[1], calc[15], calc[2], calc[15]);
    box1 = VoxelShapes.create(calc[3], calc[2], calc[3], calc[13], calc[4], calc[13]);
    box2 = VoxelShapes.create(calc[4], calc[4], calc[4], calc[12], calc[6], calc[12]);
    box3 = VoxelShapes.create(calc[5], calc[6], calc[5], calc[11], calc[8], calc[11]);
    box4 = VoxelShapes.create(calc[6], calc[8], calc[6], calc[10], calc[10], calc[10]);
    box5 = VoxelShapes.create(calc[7], calc[10], calc[7], calc[9], calc[14], calc[9]);
    shape[Constants.UP] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);
    
    // DOWN
    box0 = VoxelShapes.create(calc[1], calc[14], calc[1], calc[15], calc[16], calc[15]);
    box1 = VoxelShapes.create(calc[3], calc[12], calc[3], calc[13], calc[14], calc[13]);
    box2 = VoxelShapes.create(calc[4], calc[10], calc[4], calc[12], calc[12], calc[12]);
    box3 = VoxelShapes.create(calc[5], calc[8], calc[5], calc[11], calc[10], calc[11]);
    box4 = VoxelShapes.create(calc[6], calc[6], calc[6], calc[10], calc[8], calc[10]);
    box5 = VoxelShapes.create(calc[7], calc[2], calc[7], calc[9], calc[6], calc[9]);
    shape[Constants.DOWN] = BlockUtil.combine(box0, box1, box2, box3, box4, box5);

    return shape;
  }

  public LaserCannon(final String name, final int color){
    super(Block.Properties.create(Material.IRON, MaterialColor.STONE).sound(SoundType.METAL).hardnessAndResistance(3.5f, 6.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.machines_creative_tab));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    this.color = color;
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    if(color == -1){
      tooltip.add(new StringTextComponent("Fusion Energy"));
    }
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context){
    final IWorld world = context.getWorld();
    final BlockPos position  = context.getPos();
    return this.getDefaultState()
      .with(FACING, context.getFace())
      .with(WATERLOGGED, world.getFluidState(position).getFluid() == Fluids.WATER);
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos){
    final Block block = world.getBlockState(pos.offset(state.get(FACING).getOpposite())).getBlock();
    if(color == -1){
      return block == Machines.fusion_control_unit;
    }
    return block == Machines.laser_housing;
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(FACING).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(FACING).ordinal()];
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
  public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos){
    return !state.get(WATERLOGGED);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IFluidState getFluidState(BlockState state){
    return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader world){
    return color == -1 ? null : new TileLaser();
  }

  @Override
  protected void fillStateContainer(Builder<Block, BlockState> builder){
    builder.add(FACING, WATERLOGGED);
  }

}
