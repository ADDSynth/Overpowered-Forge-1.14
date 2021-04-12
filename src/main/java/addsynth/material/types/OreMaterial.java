package addsynth.material.types;

import addsynth.material.MaterialItem;
import addsynth.material.MiningStrength;
import addsynth.material.blocks.OreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public class OreMaterial extends BaseMaterial {

  public final Block ore;

  /** Null Ore Material */
  public OreMaterial(final String name){
    super(name);
    this.ore = null;
  }

  /** Vanilla Material */
  public OreMaterial(final String name, final Item item, final Block block, final Block ore){
    super(false, name, item, block);
    this.ore = ore;
  }

  /** Manufactured Metal Material */
  protected OreMaterial(final String name, final Item item, final Block block){
    super(true, name, item, block);
    this.ore = null;
  }

  /** Custom Material */
  public OreMaterial(final String name, final MaterialColor color, final MiningStrength strength){
    // Silicon, Uranium, and Yellorium
    super(true, name, new MaterialItem(name), null); // TODO: needs generic block
    this.ore = new OreBlock(name+"_ore", strength);
  }

  /** Specific Type Material */
  protected OreMaterial(final String name, final Item item, final Block block, final MiningStrength strength, final int min_experience, final int max_experience){
    super(true, name, item, block);
    this.ore = new OreBlock(name+"_ore", strength, min_experience, max_experience);
  }

}
