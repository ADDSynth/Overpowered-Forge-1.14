package addsynth.overpoweredmod.blocks.unique;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;

public final class NullBlock extends Block {

  public NullBlock(String name){
    super(Block.Properties.create(Material.AIR).variableOpacity().doesNotBlockMovement());
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

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    @Override
    public final boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
        return true;
    }

}
