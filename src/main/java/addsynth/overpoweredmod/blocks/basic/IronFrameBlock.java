package addsynth.overpoweredmod.blocks.basic;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(final String name){
    super(Block.Properties.create(Material.IRON).hardnessAndResistance(3.5f, 300.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.creative_tab));
  }

}
