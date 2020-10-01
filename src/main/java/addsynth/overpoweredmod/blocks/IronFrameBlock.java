package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(final String name){
    super(Block.Properties.create(Material.IRON, MaterialColor.WOOL).hardnessAndResistance(3.5f, 300.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
  }

}
