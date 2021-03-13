package addsynth.material.blocks;

import addsynth.material.ADDSynthMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public final class GemBlock extends Block {
	
  public GemBlock(final String name, final MaterialColor color){
    super(Block.Properties.create(Material.ROCK, color).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE));
    ADDSynthMaterials.registry.register_block(this, name, new Item.Properties().group(ADDSynthMaterials.creative_tab));
  }

}
