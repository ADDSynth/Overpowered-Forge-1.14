package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.material.MiningStrength;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class OreBlock extends Block {

  public OreBlock(final String name, final MiningStrength strength){
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(strength.ordinal()));
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    ADDSynthCore.registry.register_block(this, name);
  }

}
