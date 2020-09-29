package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class NullBlock extends Block {

  public NullBlock(final String name){
    super(Block.Properties.create(Material.AIR).variableOpacity().doesNotBlockMovement());
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return VoxelShapes.empty();
  }

  @Override
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  // The Null Block is also translucent, so logically it should choose NOT to
  // render sides adjacent to other Null Blocks, but I kind of like this aesthetic.
  // See Portal Energy Block, Energy Storage Container, Laser Beam, and Energy Bridge
  // blocks for the implementation.
  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    return super.isSideInvisible(state, adjacentBlockState, side);
  }

}
