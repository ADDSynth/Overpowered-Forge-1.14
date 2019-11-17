package addsynth.core.blocks;

import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

// MAYBE: consider skipping BlockContainer, extend Block and implement ITileProvider ourselves.
public abstract class BlockTile extends ContainerBlock {

  public BlockTile(Material material){
    super(material);
  }

  public BlockTile(final Material material, final MaterialColor map_color){
    super(material, map_color);
  }

  /**
   * Says that the block should use a model to be rendered
   */
  @Override
  @SuppressWarnings("deprecation")
  public EnumBlockRenderType getRenderType(IBlockState state){
      return EnumBlockRenderType.MODEL;
  }

}
