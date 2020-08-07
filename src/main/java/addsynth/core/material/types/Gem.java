package addsynth.core.material.types;

import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.blocks.GemBlock;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public final class Gem extends OreMaterial {

  public final Item gem;
  public final Item shard;

  /** Custom Gem Material */
  public Gem(final String unlocalized_name, final MaterialColor color, final int min_experience, final int max_experience){
    super(unlocalized_name,
      new CoreItem(unlocalized_name, true),
      new GemBlock(unlocalized_name+"_block", color),
      MiningStrength.IRON, CreativeTabs.gems_creative_tab, min_experience, max_experience);
    this.gem = this.item;
    this.shard = new CoreItem(unlocalized_name+"_shard", true);
  }

  /** Vanilla Material */
  public Gem(final String unlocalized_name, final Item gem, final Block block, final Block ore){
    super(unlocalized_name, gem, block, ore);
    this.gem = this.item;
    this.shard = new CoreItem(unlocalized_name+"_shard", true);
  }

}
