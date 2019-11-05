package addsynth.overpoweredmod.blocks.tiles.fusion;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class FusionControlLaserBeam extends Block {

  public FusionControlLaserBeam(String name){
    super(Material.FIRE);
    setLightLevel(1.0f);
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

}
