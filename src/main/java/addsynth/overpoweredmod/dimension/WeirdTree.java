package addsynth.overpoweredmod.dimension;

import java.util.Random;
import addsynth.overpoweredmod.game.core.Portal;
// import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public final class WeirdTree extends WorldGenAbstractTree {

  private static final IBlockState WOOD = Portal.unknown_wood.getDefaultState();
  private static final IBlockState LEAVES = Portal.unknown_leaves.getDefaultState();

  public WeirdTree(boolean notify) {
    super(notify);
  }

  @Override
  public final boolean generate(final World world, final Random random, final BlockPos position){
    // Copied from Birch Tree.
    final BlockPos down = position.down();
    final IBlockState ground_blockstate = world.getBlockState(down);
    if(ground_blockstate != null){
      // final boolean is_soil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (BlockSapling)Blocks.SAPLING);
      final boolean is_soil = true;
      if(is_soil){
        final int tree_height = 5;
        if(position.getY() >= 1){
          int x;
          int y;
          int z;
          BlockPos center;
          // leaves
          for(x = position.getX() - 2; x <= position.getX() + 2; x++){
            for(z = position.getZ() - 2; z <= position.getZ() + 2; z++){
              gen_leaf(world, new BlockPos(x,position.getY()+3,z));
            }
          }
          for(x = position.getX() - 2; x <= position.getX() + 2; x++){
            for(z = position.getZ() - 2; z <= position.getZ() + 2; z++){
              gen_leaf(world, new BlockPos(x,position.getY()+4,z));
            }
          }
          for(x = position.getX() - 1; x <= position.getX() + 1; x++){
            for(z = position.getZ() - 1; z <= position.getZ() + 1; z++){
              gen_leaf(world, new BlockPos(x,position.getY()+5,z));
            }
          }
          // logs
          for(y = 0; y < tree_height; y++){
            center = position.up(y);
            this.setBlockAndNotifyAdequately(world, center, WOOD);
          }
        }
        // state.getBlock().onPlatGrow(state, world, down, position);
      }
    }
    return false;
  }

  private final void gen_leaf(final World world, final BlockPos leaf_position){
    final IBlockState air_blockstate = world.getBlockState(leaf_position);
    if(air_blockstate.getBlock().isAir(air_blockstate,world,leaf_position)){
      this.setBlockAndNotifyAdequately(world,leaf_position,LEAVES);
    }
  }

}
