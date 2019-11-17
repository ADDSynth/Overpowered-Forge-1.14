package addsynth.overpoweredmod.blocks.unique;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public final class LightBlock extends Block {

  public LightBlock(final String name){
    super(Material.GLASS, MaterialColor.QUARTZ);
    setLightLevel(1.0f);
    setHardness(5.0f);
    setHarvestLevel("pickaxe",2);
    setResistance(10.0f);
    OverpoweredMod.registry.register_block(this, name);
  }

}
