package addsynth.overpoweredmod.game.core;

import addsynth.core.material.Material;
import addsynth.core.material.types.Gem;
import addsynth.overpoweredmod.Debug;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public final class Gems {

  static {
    Debug.log_setup_info("Begin loading Gems class...");
  }

  public static final Gem RUBY     = Material.RUBY;
  public static final Gem TOPAZ    = Material.TOPAZ;
  public static final Gem CITRINE  = Material.CITRINE;
  public static final Gem EMERALD  = Material.EMERALD;
  public static final Gem DIAMOND  = Material.DIAMOND;
  public static final Gem SAPPHIRE = Material.SAPPHIRE;
  public static final Gem AMETHYST = Material.AMETHYST;
  public static final Gem QUARTZ   = Material.QUARTZ;

  public static final Item ruby     = RUBY.gem;
  public static final Item topaz    = TOPAZ.gem;
  public static final Item citrine  = CITRINE.gem;
  public static final Item emerald  = EMERALD.gem;
  public static final Item diamond  = DIAMOND.gem;
  public static final Item sapphire = SAPPHIRE.gem;
  public static final Item amethyst = AMETHYST.gem;
  public static final Item quartz   = QUARTZ.gem;

  public static final Gem[] index = new Gem[] {RUBY, TOPAZ, CITRINE, EMERALD, DIAMOND, SAPPHIRE, AMETHYST, QUARTZ};
  public static final int max_index = index.length;
  public static final Item[]      gem_items       = new Item[max_index];
  public static final BlockItem[] gem_block_items = new BlockItem[max_index];

  // https://stackoverflow.com/questions/443980/why-cant-enums-constructor-access-static-fields
  static {
    int i;
    for(i = 0; i < index.length; i++){
      gem_items[i] = index[i].gem;
      gem_block_items[i] = index[i].block_item;
    }
  }

  static {
    Debug.log_setup_info("Finished loading Gems class.");
  }

}
