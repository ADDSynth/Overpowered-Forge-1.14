package addsynth.material.blocks;

import addsynth.material.ADDSynthMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

// UNUSED Material Generic Storage Block
// Based off of the vanilla Coal Block
public final class GenericStorageBlock extends Block {

  public GenericStorageBlock(final String name, final MaterialColor color){
    super(Block.Properties.create(Material.ROCK, color).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE));
    ADDSynthMaterials.registry.register_block(this, name, new Item.Properties().group(ADDSynthMaterials.creative_tab));
  }

}
