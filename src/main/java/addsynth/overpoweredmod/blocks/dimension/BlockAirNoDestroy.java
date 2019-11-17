package addsynth.overpoweredmod.blocks.dimension;

import net.minecraft.block.AirBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockAirNoDestroy extends AirBlock {

  public BlockAirNoDestroy(String name) {
    super();
    // MAYBE: Registers.add(this, name, false);
  }

  @Override
  public void onPlayerDestroy(World world, BlockPos pos, IBlockState state){
    if(world.isRemote == false){
      // world.setBlockState(pos, Init.custom_air_block.getDefaultState(), 2);
    }
  }

}
