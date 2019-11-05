package addsynth.overpoweredmod.blocks.tiles.laser;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.blocks.BlockTile;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaser;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class LaserCannon extends BlockTile {

  public static final PropertyDirection FACING = PropertyDirection.create("facing");
  
  private static final double min_flat = 3.0 / 16;
  private static final double max_flat = 13.0 / 16;
  private static final double min_extrude = 3.0 / 8;
  private static final double max_extrude = 5.0 / 8;
  private static final AxisAlignedBB bounding_box[] = new AxisAlignedBB[6];

  static {
    bounding_box[EnumFacing.EAST.ordinal() ] = new AxisAlignedBB(0, min_flat, min_flat, max_extrude, max_flat, max_flat);
    bounding_box[EnumFacing.WEST.ordinal() ] = new AxisAlignedBB(min_extrude, min_flat, min_flat, 1, max_flat, max_flat);
    bounding_box[EnumFacing.UP.ordinal()   ] = new AxisAlignedBB(min_flat, 0, min_flat, max_flat, max_extrude, max_flat);
    bounding_box[EnumFacing.DOWN.ordinal() ] = new AxisAlignedBB(min_flat, min_extrude, min_flat, max_flat, 1, max_flat);
    bounding_box[EnumFacing.SOUTH.ordinal()] = new AxisAlignedBB(min_flat, min_flat, 0, max_flat, max_flat, max_extrude);
    bounding_box[EnumFacing.NORTH.ordinal()] = new AxisAlignedBB(min_flat, min_flat, min_extrude, max_flat, max_flat, 1);
  }

  public final int color;

  public LaserCannon(final String name, final int color){
    super(Material.IRON, MapColor.STONE);
    OverpoweredMod.registry.register_block(this, name);
    setHardness(3.5f);
    setSoundType(SoundType.METAL);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    this.color = color;
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    if(color == -1){
      tooltip.add("Fusion Energy");
    }
  }

  @Override
  protected final BlockStateContainer createBlockState(){
    return new BlockStateContainer(this, FACING);
  }

  /**
   * These getStateFromMeta() and getMetaFromState() functions are used for saving the block to world data.
   * They ensure the laser block saves and loads in the proper orientation.
   */
  @Override
  @SuppressWarnings("deprecation")
  public final IBlockState getStateFromMeta(int meta){
    return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
  }

  @Override
  public final int getMetaFromState(IBlockState state){
    return state.getValue(FACING).getIndex();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isOpaqueCube(IBlockState state){
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isFullCube(IBlockState state){
    return false;
  }

  @Override
  public final IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                          float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
    return getDefaultState().withProperty(FACING, facing);
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
    return bounding_box[state.getValue(FACING).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos){
    return bounding_box[state.getValue(FACING).ordinal()];
  }

  /**
   * Check whether this block can be placed on the block in the given direction.
   */
  @Override
  public final boolean canPlaceBlockOnSide(final World world, final BlockPos pos, final EnumFacing direction){
    final Block block = world.getBlockState(pos.offset(direction.getOpposite())).getBlock();
    if(color == -1){
      return block == Machines.laser_scanning_unit;
    }
    return block == Machines.laser_housing;
  }

  @Override
  public final TileEntity createNewTileEntity(World world, int meta){
    return color == -1 ? null : new TileLaser();
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos position_of_neighbor){
    if(worldIn.isRemote == false){
      if(canPlaceBlockOnSide(worldIn, pos, state.getValue(FACING)) == false){
        dropBlockAsItem(worldIn, pos, state, 0);
        worldIn.setBlockToAir(pos);
      }
    }
  }

}
