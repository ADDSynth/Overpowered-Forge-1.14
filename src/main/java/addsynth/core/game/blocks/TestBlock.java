package addsynth.core.game.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Block.Properties.create(Material.ROCK, MaterialColor.SNOW).hardnessAndResistance(0.2f, 6.0f));
    ADDSynthCore.registry.register_block(this, "test_block", new Item.Properties().group(ADDSynthCore.creative_tab));
  }

  public TestBlock(final String name){
    super(Block.Properties.create(Material.ROCK, MaterialColor.SNOW).hardnessAndResistance(0.2f, 6.0f));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().group(ADDSynthCore.creative_tab));
  }

}
