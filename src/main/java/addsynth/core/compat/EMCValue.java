package addsynth.core.compat;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.proxy.IEMCProxy;

/** Contains Project E vanilla EMC values reported directly from the mod.<br/>
 *  Use these when creating custom EMC values for your<br/>
 *  items when Project E can't assign them automatically.
 */
public final class EMCValue {

  /** Used for Iron, Tin, Copper, Aluminum, and their compressed plates. */
  public static final long common_metal   =  256;
  /** Used for Steel and Bronze. */
  public static final long strong_metal   =  512;
  /** Used for Silver and Gold. */
  public static final long uncommon_metal = 2048;
  /** Rare metals such as Platinum and Titanium. */
  public static final long rare_metal     = 8192;
  
  public static final long common_metal_block   =  2304;
  public static final long strong_metal_block   =  4608;
  public static final long uncommon_metal_block = 18432;
  public static final long rare_metal_block     = 73728;

  public static final long nether_quartz    =   256;
  public static final long lapis_lazuli     =   864;
  public static final long diamond          =  8192;
  public static final long emerald          = 16384;
  public static final long heart_of_the_sea = 32768;

  public static final long paper          =   32;
  public static final long coal           =  128;
  public static final long glowstone_dust =  384;
  public static final long ender_pearl    = 1024;
  public static final long blaze_rod      = 1536;
  public static final long blaze_powder   =  768;
  public static final long ender_eye      = 1792;
  
  // Dyes are not the same! But most of them are 16.
  public static final long green_dye  =  8;
  public static final long red_dye    = 16;
  public static final long yellow_dye = 16;
  public static final long blue_dye   = 16;
  public static final long black_dye  = 16;
  public static final long purple_dye = 16;
  public static final long orange_dye = 16;
  public static final long white_dye  = 16;
  public static final long cyan_dye   = 12;
  
  public static final long redstone            =   64;
  public static final long redstone_torch      =   68;
  public static final long redstone_repeater   =  203;
  public static final long redstone_comparator =  463;
  public static final long redstone_lamp       = 1792;
  
  public static final long gemstone = diamond;
  public static final long gem_shard = 910; // Math.round(8192f / 9)
  public static final long gem_block = 73728;

  public static final long silicon = 1024; // 4x value of Nether Quartz

  /** Although you can get the EMCProxy at any time, it won't have the EMC values until
   *  after a world is loaded. Therefore, this must be called when a player enters a world. */
  public static final void check_emc_values(){
    final IEMCProxy emcProxy = ProjectEAPI.getEMCProxy();
    // Metals
    checkEMC(emcProxy, Items.IRON_INGOT, common_metal);
    checkEMC(emcProxy, Items.GOLD_INGOT, uncommon_metal);
    // Gems
    checkEMC(emcProxy, Items.QUARTZ,           nether_quartz);
    checkEMC(emcProxy, Items.LAPIS_LAZULI,     lapis_lazuli);
    checkEMC(emcProxy, Items.DIAMOND,          diamond);
    checkEMC(emcProxy, Items.EMERALD,          emerald);
    checkEMC(emcProxy, Items.HEART_OF_THE_SEA, heart_of_the_sea);
    // Basic Items
    checkEMC(emcProxy, Items.PAPER,          paper);
    checkEMC(emcProxy, Items.COAL,           coal);
    checkEMC(emcProxy, Items.GLOWSTONE_DUST, glowstone_dust);
    checkEMC(emcProxy, Items.ENDER_PEARL,    ender_pearl);
    checkEMC(emcProxy, Items.BLAZE_ROD,      blaze_rod);
    checkEMC(emcProxy, Items.BLAZE_POWDER,   blaze_powder);
    checkEMC(emcProxy, Items.ENDER_EYE,      ender_eye);
    // Dyes
    checkEMC(emcProxy, Items.GREEN_DYE,  green_dye);
    checkEMC(emcProxy, Items.RED_DYE,    red_dye);
    checkEMC(emcProxy, Items.YELLOW_DYE, yellow_dye);
    checkEMC(emcProxy, Items.BLUE_DYE,   blue_dye);
    checkEMC(emcProxy, Items.BLACK_DYE,  black_dye);
    checkEMC(emcProxy, Items.PURPLE_DYE, purple_dye);
    checkEMC(emcProxy, Items.ORANGE_DYE, orange_dye);
    checkEMC(emcProxy, Items.WHITE_DYE,  white_dye);
    checkEMC(emcProxy, Items.CYAN_DYE,   cyan_dye);
    // Redstone
    checkEMC(emcProxy, Items.REDSTONE,       redstone);
    checkEMC(emcProxy, Items.REDSTONE_TORCH, redstone_torch);
    checkEMC(emcProxy, Items.REPEATER,       redstone_repeater);
    checkEMC(emcProxy, Items.COMPARATOR,     redstone_comparator);
    checkEMC(emcProxy, Items.REDSTONE_LAMP,  redstone_lamp);
  }

  private static final void checkEMC(IEMCProxy emcProxy, Item item, long value){
    final long emc = emcProxy.getValue(item);
    if(value != emc){
      ADDSynthCore.log.warn(
        StringUtil.build(
          "Project E reports the EMC for ",
          StringUtil.getName(item),
          " is ", Long.toString(emc),
          ", however we have it saved internally as ",
          Long.toString(value),
          ". This value needs to be updated if the developers of Project E changed this value.",
          " If this was intentionally modified by a player or modpack then you can ignore this."
        )
      );
    }
  }

}
