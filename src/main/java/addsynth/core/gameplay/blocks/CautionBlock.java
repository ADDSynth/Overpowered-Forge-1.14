package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public final class CautionBlock extends Block {

  public CautionBlock(final String name){
    super(Block.Properties.create(Material.ROCK, MaterialColor.YELLOW).sound(SoundType.STONE).hardnessAndResistance(2.0f, Constants.block_resistance));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().group(ADDSynthCore.creative_tab));
  }

}
