package addsynth.overpoweredmod.blocks.tiles.fusion;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

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
  @Nullable
  @SuppressWarnings("deprecation")
  public final AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
    return NULL_AABB;
  }

}
