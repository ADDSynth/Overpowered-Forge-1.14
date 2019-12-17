package addsynth.overpoweredmod.blocks.tiles.laser;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.blocks.BlockTile;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public final class LaserCannon extends BlockTile {

  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  
  private static final double min_flat = 3.0 / 16;
  private static final double max_flat = 13.0 / 16;
  private static final double min_extrude = 3.0 / 8;
  private static final double max_extrude = 5.0 / 8;
  private static final AxisAlignedBB bounding_box[] = new AxisAlignedBB[6];

  static {
    bounding_box[Direction.EAST.ordinal() ] = new AxisAlignedBB(0, min_flat, min_flat, max_extrude, max_flat, max_flat);
    bounding_box[Direction.WEST.ordinal() ] = new AxisAlignedBB(min_extrude, min_flat, min_flat, 1, max_flat, max_flat);
    bounding_box[Direction.UP.ordinal()   ] = new AxisAlignedBB(min_flat, 0, min_flat, max_flat, max_extrude, max_flat);
    bounding_box[Direction.DOWN.ordinal() ] = new AxisAlignedBB(min_flat, min_extrude, min_flat, max_flat, 1, max_flat);
    bounding_box[Direction.SOUTH.ordinal()] = new AxisAlignedBB(min_flat, min_flat, 0, max_flat, max_flat, max_extrude);
    bounding_box[Direction.NORTH.ordinal()] = new AxisAlignedBB(min_flat, min_flat, min_extrude, max_flat, max_flat, 1);
  }

  public final int color;

  public LaserCannon(final String name, final int color){
    super(Block.Properties.create(Material.IRON, MaterialColor.STONE).sound(SoundType.METAL).hardnessAndResistance(3.5f, 6.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.machines_creative_tab));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    this.color = color;
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    if(color == -1){
      tooltip.add(new StringTextComponent("Fusion Energy"));
    }
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context){
    return this.getDefaultState().with(FACING, context.getFace());
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos){
    final Block block = world.getBlockState(pos.offset(state.get(FACING).getOpposite())).getBlock();
    if(color == -1){
      return block == Machines.laser_scanning_unit;
    }
    return block == Machines.laser_housing;
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader world){
    return color == -1 ? null : new TileLaser();
  }

}
