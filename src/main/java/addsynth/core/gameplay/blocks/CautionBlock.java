package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public final class CautionBlock extends Block {

  public CautionBlock(final String name) {
    super(Material.ROCK, MaterialColor.YELLOW);
    ADDSynthCore.registry.register_block(this, name);
    setHardness(2.0f);
    setSoundType(SoundType.STONE);
  }

}
