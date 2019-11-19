package addsynth.overpoweredmod.blocks.basic;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public final class TrophyBlock extends Block {

  private static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

  private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.9375, 0.875);

  public TrophyBlock(String name){
    super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, Direction.NORTH));
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, Hand hand){
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
