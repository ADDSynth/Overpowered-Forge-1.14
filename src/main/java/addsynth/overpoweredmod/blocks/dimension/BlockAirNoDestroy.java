package addsynth.overpoweredmod.blocks.dimension;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public final class BlockAirNoDestroy extends AirBlock {

  public BlockAirNoDestroy(String name) {
    super(Block.Properties.create(Material.AIR));
    // MAYBE: Registers.add(this, name, false);
  }

  @Override
  public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state){
    if(world.isRemote() == false){
      // world.setBlockState(pos, Init.custom_air_block.getDefaultState(), 2);
    }
  }

}
