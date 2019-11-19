package addsynth.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.2f, 6.0f));
    setRegistryName("test_block");
  }

}
