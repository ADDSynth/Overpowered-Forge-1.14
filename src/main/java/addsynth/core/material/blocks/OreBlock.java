package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.material.MiningStrength;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OreBlock extends Block {

  public OreBlock(final String name, final MiningStrength strength){
    super(Material.ROCK);
    setHardness(3.0f);  // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    if(strength == null){ setHarvestLevel("pickaxe",0); }
    else{ setHarvestLevel("pickaxe",strength.ordinal()); }
    setResistance(10.0f);
    ADDSynthCore.registry.register_block(this, name);
  }

}
