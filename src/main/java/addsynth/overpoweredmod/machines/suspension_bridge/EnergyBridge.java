package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.constants.Constants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Lens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class EnergyBridge extends RotatedPillarBlock {

  private static final VoxelShape[] shapes = {
    VoxelShapes.create(7.0 / 16, 0, 0, 9.0 / 16, 1.0, 1.0), // X = flat going forward and back, missing left and right
    VoxelShapes.create(0, 14.0 / 16, 0, 1.0, 1.0, 1.0),
    VoxelShapes.create(0, 0, 7.0 / 16, 1.0, 1.0, 9.0 / 16)  // Z = flat going left and right, missing front and back
  };

  public EnergyBridge(final String name, final Lens lens){
    super(Block.Properties.create(
      new Material(lens.color, false, true, true, false, false, false, false, PushReaction.BLOCK)
    ).lightValue(11).hardnessAndResistance(-1.0f, Constants.infinite_resistance).variableOpacity().noDrops());
    OverpoweredTechnology.registry.register_block(this, name);
  }

  public final BlockState getRotated(final Direction.Axis axis){
    return getDefaultState().with(AXIS, axis);
  }

  @Override
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    // TODO: Fix but not a priority. This successfully renders energy bridges correctly,
    //       but only in the flat version, does not work for vertical energy bridges.
    if(adjacentBlockState.getBlock() instanceof EnergyBridge){
      final Direction.Axis       axis = state.get(AXIS);
      final Direction.Axis other_axis = adjacentBlockState.get(AXIS);
      
      // horizontal orientation
      if(axis == Direction.Axis.Y){
        if(side.getAxis() == Direction.Axis.Y){
          return false;
        }
        return other_axis == Direction.Axis.Y;
      }
      
      // vertical orientation
      if(axis == Direction.Axis.X){
        if(side.getAxis() == Direction.Axis.X){ return false;}
        if(side.getAxis() == Direction.Axis.Z){ return other_axis == Direction.Axis.X; }
        if(side == Direction.UP){               return other_axis == Direction.Axis.X; }
        if(side == Direction.DOWN){             return other_axis != Direction.Axis.Z; }
      }
      
      if(axis == Direction.Axis.Z){
        if(side.getAxis() == Direction.Axis.Z){ return false; }
        if(side.getAxis() == Direction.Axis.X){ return other_axis == Direction.Axis.Z; }
        if(side == Direction.UP){               return other_axis == Direction.Axis.Z; }
        if(side == Direction.DOWN){             return other_axis != Direction.Axis.X; }
      }
    }
    // other block entirely
    return super.isSideInvisible(state, adjacentBlockState, side);
  }

}
