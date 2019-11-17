package addsynth.overpoweredmod.blocks.basic;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class TrophyBlock extends Block {

  private static final PropertyDirection FACING = PropertyDirection.create("facing", Direction.Plane.HORIZONTAL);

  private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.9375, 0.875);

  public TrophyBlock(String name){
    super(Material.IRON);
    setHardness(3.5f);
    setSoundType(SoundType.METAL);
    setHarvestLevel("pickaxe",2);
    setResistance(10.0f);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, Direction.NORTH));
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  protected BlockStateContainer createBlockState(){
    return new BlockStateContainer(this, FACING);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final IBlockState getStateFromMeta(int meta){
    return getDefaultState().withProperty(FACING, Direction.byIndex(meta));
  }

  @Override
  public final int getMetaFromState(IBlockState state){
    return state.getValue(FACING).getIndex();
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, and hand){
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isOpaqueCube(IBlockState state){
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isFullCube(IBlockState state){
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
    return bounding_box;
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
    return bounding_box;
  }

}
