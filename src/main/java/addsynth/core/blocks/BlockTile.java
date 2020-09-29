package addsynth.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;

// MAYBE: consider skipping BlockContainer, extend Block and implement ITileProvider ourselves.
public abstract class BlockTile extends ContainerBlock {

  public BlockTile(Block.Properties properties){
    super(properties);
  }

  /**
   * Says that the block should use a model to be rendered
   */
  @Override
  public BlockRenderType getRenderType(BlockState state){
    return BlockRenderType.MODEL;
  }

}
