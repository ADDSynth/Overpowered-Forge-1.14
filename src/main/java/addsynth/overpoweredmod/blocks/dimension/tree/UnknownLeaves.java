package addsynth.overpoweredmod.blocks.dimension.tree;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public final class UnknownLeaves extends Block {

  public UnknownLeaves(final String name){
    super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f, 0.0f).sound(SoundType.PLANT).variableOpacity());
    OverpoweredMod.registry.register_block(this, name);
  }

}
