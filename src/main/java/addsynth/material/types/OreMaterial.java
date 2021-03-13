package addsynth.material.types;

import addsynth.core.gameplay.items.CoreItem;
import addsynth.material.MiningStrength;
import addsynth.material.blocks.OreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class OreMaterial extends BaseMaterial {

  public final Block ore;

  /** Vanilla Material */
  public OreMaterial(final String name, final Item item, final Block block, final Block ore){
    super(false, name, item, block);
    this.ore = ore;
  }

  /** Null Ore Material */
  protected OreMaterial(final String name, final Item item, final Block block){
    super(true, name, item, block);
    this.ore = null;
  }

  /** Custom Material */
  public OreMaterial(final String name, final MaterialColor color, final MiningStrength strength, final ItemGroup group){
    // Silicon, Urnaium, and Yellorium
    super(true, name, new CoreItem(new Item.Properties(), name), null); // TODO: needs generic block
    this.ore = new OreBlock(name+"_ore", strength, group);
  }

  /** Specific Type Material */
  protected OreMaterial(final String name, final Item item, final Block block, final MiningStrength strength, final ItemGroup group, final int min_experience, final int max_experience){
    super(true, name, item, block);
    this.ore = new OreBlock(name+"_ore", strength, group, min_experience, max_experience);
  }

}
