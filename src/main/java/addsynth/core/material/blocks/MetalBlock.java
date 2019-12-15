package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

/** All Metal Blocks will have the same MapColor as Vanilla Iron Block,
 *  unless you specify a color yourself.
 */
public final class MetalBlock extends Block {

  public MetalBlock(final String name){
    this(name, MaterialColor.IRON);
  }

  public MetalBlock(final String name, final MaterialColor color){
    super(Block.Properties.create(Material.IRON, color).hardnessAndResistance(5.0f, 6.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.metals_creative_tab));
  }

}
