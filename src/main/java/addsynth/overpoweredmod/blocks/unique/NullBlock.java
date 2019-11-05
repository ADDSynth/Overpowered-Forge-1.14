package addsynth.overpoweredmod.blocks.unique;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class NullBlock extends Block {

  public NullBlock(String name){
    super(Material.AIR);
    translucent = true;
    OverpoweredMod.registry.register_block(this, name);
  }

    @Override
    @SideOnly(Side.CLIENT)
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
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    @SuppressWarnings("deprecation")
    public final boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public final boolean isCollidable(){
        return false;
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    @Override
    public final boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
        return true;
    }

}
