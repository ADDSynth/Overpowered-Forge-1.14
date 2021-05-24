package addsynth.material.types;

import javax.annotation.Nonnull;
import addsynth.material.ADDSynthMaterials;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BaseMaterial extends AbstractMaterial {

  public final Item item;
  public final Block block;
  public final BlockItem block_item;

  /** Call this, only if you're unsure of the properties of the material.
   *  This acts as a placeholder until you can properly define all of the properties.
   *  In the meantime, this material will still be checked by name in certain instances.
   * @param name
   */
  public BaseMaterial(final String name){
    super(true, name);
    this.item = null;
    this.block = null;
    this.block_item = null;
  }

  public BaseMaterial(final boolean custom, final String name, final Item item){
    super(custom, name);
    this.item = item;
    this.block = null;
    this.block_item = null;
  }

  /** A block has more characteristics than an item. If you need a Storage Block for your
   *  material, you should define it yourself.
   * @param custom
   * @param name
   * @param item
   * @param block
   */
  public BaseMaterial(final boolean custom, final String name, final Item item, @Nonnull final Block block){
    super(custom, name);
    this.item = item;
    this.block = block;
    this.block_item = ADDSynthMaterials.registry.getItemBlock(this.block);
  }

}
