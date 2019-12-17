package addsynth.overpoweredmod.blocks.tiles.fusion;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class FusionControlLaserBeam extends Block {

  public FusionControlLaserBeam(String name){
    super(Block.Properties.create(Material.FIRE).lightValue(15).variableOpacity().doesNotBlockMovement());
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
     return VoxelShapes.empty();
  }

}
