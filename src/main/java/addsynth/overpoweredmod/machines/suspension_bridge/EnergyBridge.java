package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.Constants;
import addsynth.overpoweredmod.OverpoweredMod;
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
    VoxelShapes.create(7.0 / 16, 0, 0, 9.0 / 16, 1.0, 1.0),
    VoxelShapes.create(0, 14.0 / 16, 0, 1.0, 1.0, 1.0),
    VoxelShapes.create(0, 0, 7.0 / 16, 1.0, 1.0, 9.0 / 16)
  };

  public EnergyBridge(final String name, final Lens lens){
    super(Block.Properties.create(
      new Material(lens.color, false, true, true, false, false, false, false, PushReaction.BLOCK)
    ).lightValue(11).hardnessAndResistance(-1.0f, Constants.infinite_resistance).variableOpacity().noDrops());
    OverpoweredMod.registry.register_block(this, name);
  }

  public final BlockState getRotated(final Direction.Axis axis){
    return getDefaultState().with(AXIS, axis);
  }

  @Override
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

}
