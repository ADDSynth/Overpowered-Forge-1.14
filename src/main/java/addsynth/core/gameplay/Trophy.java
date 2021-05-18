package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.blocks.TrophyBlock;
import addsynth.core.gameplay.items.CoreItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public enum Trophy {

  BRONZE("bronze"),
  SILVER("silver"),
  GOLD("gold"),
  PLATINUM("platinum");

  private final TrophyBlock trophy;
  public final BlockItem item_block;
  
  public static final Item trophy_base = new CoreItem("trophy_base");
  public static final TrophyBlock bronze   = BRONZE.trophy;
  public static final TrophyBlock silver   = SILVER.trophy;
  public static final TrophyBlock gold     = GOLD.trophy;
  public static final TrophyBlock platinum = PLATINUM.trophy;

  private Trophy(final String name){
    this.trophy = new TrophyBlock(name+"_trophy");
    this.item_block = ADDSynthCore.registry.getItemBlock(this.trophy);
  }

}
