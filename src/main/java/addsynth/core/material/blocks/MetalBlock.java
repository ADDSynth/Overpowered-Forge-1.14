package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;

/** All Metal Blocks will have the same MapColor as Vanilla Iron Block,
 *  unless you specify a color yourself.
 */
public final class MetalBlock extends Block {

  public MetalBlock(final String name){
    this(name, MapColor.IRON);
  }

  public MetalBlock(final String name, final MapColor color){
    super(Material.IRON, color);
    setHardness(5.0f);
    setResistance(10.0f);
    ADDSynthCore.registry.register_block(this, name);
  }

}
