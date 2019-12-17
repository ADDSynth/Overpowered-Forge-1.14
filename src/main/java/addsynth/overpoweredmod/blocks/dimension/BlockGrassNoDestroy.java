package addsynth.overpoweredmod.blocks.dimension;

import addsynth.core.util.MinecraftUtility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class BlockGrassNoDestroy extends Block {

  public BlockGrassNoDestroy(String name){
    super(MinecraftUtility.setUnbreakable(Block.Properties.create(Material.ORGANIC)));
    // MAYBE: Registers.add(this, name, false);
  }

  /*
  @Override
  public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
    if(world.isRemote == false){
      world.setBlockState(pos, Init.custom_grass_block.getDefaultState(), 2);
    }
  }
  */

}
