package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public final class GemBlock extends Block {
	
  public GemBlock(final String name, final MaterialColor color){
    super(Material.ROCK, color); // or Glass
    setHardness(5.0f);
    setResistance(10.0f);
    setHarvestLevel("pickaxe",2);
    setSoundType(SoundType.STONE);
    ADDSynthCore.registry.register_block(this, name);
  }

}
