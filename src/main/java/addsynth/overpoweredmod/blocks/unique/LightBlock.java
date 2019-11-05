package addsynth.overpoweredmod.blocks.unique;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public final class LightBlock extends Block {

  public LightBlock(final String name){
    super(Material.GLASS, MapColor.QUARTZ);
    setLightLevel(1.0f);
    setHardness(5.0f);
    setHarvestLevel("pickaxe",2);
    setResistance(10.0f);
    OverpoweredMod.registry.register_block(this, name);
  }

}
