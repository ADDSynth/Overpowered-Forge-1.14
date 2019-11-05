package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.blocks.basic.TrophyBlock;
import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public enum Trophy {

  BRONZE("bronze"),
  SILVER("silver"),
  GOLD("gold"),
  PLATINUM("platinum");

  static {
    Debug.log_setup_info("Begin loading Trophy class...");
  }

  private final TrophyBlock trophy;
  public final ItemBlock item_block;
  
  public static final Item trophy_base = new OverpoweredItem("trophy_base");
  public static final TrophyBlock bronze   = BRONZE.trophy;
  public static final TrophyBlock silver   = SILVER.trophy;
  public static final TrophyBlock gold     = GOLD.trophy;
  public static final TrophyBlock platinum = PLATINUM.trophy;

  private Trophy(final String name){
    this.trophy = new TrophyBlock(name+"_trophy");
    this.item_block = OverpoweredMod.registry.getItemBlock(this.trophy);
  }

  static {
    Debug.log_setup_info("Finished loading Trophy class.");
  }

}
