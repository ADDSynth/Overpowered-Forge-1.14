package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public final class CautionBlock extends Block {

  public CautionBlock(final String name) {
    super(Material.ROCK, MapColor.YELLOW);
    ADDSynthCore.registry.register_block(this, name);
    setHardness(2.0f);
    setSoundType(SoundType.STONE);
  }

}
