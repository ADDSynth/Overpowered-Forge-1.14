package addsynth.overpoweredmod.blocks.basic;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(final String name){
    super(Material.IRON);
    setHardness(3.5f);
    setResistance(500.0f);
    OverpoweredMod.registry.register_block(this, name);
  }

}
