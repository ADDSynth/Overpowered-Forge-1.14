package addsynth.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Material.ROCK);
    final String name = "test_block";
    setHardness(0.2f);
    setTranslationKey(name);
    setRegistryName(name);
  }

}
