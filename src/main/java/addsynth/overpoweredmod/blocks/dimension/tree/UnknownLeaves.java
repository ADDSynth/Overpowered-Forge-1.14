package addsynth.overpoweredmod.blocks.dimension.tree;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public final class UnknownLeaves extends Block {

  public UnknownLeaves(final String name){
    super(Material.LEAVES);
    setHardness(0.2F);
    setLightOpacity(1);
    setSoundType(SoundType.PLANT);
    OverpoweredMod.registry.register_block(this, name);
  }

}
