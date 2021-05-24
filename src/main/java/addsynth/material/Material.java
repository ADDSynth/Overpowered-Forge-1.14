package addsynth.material;

import java.util.ArrayList;
import addsynth.material.types.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Items;

/**
 *  These mineral types are based on the real world, and thus always adhere to certain rules and
 *  properties. So we can define them once in a global setting. It's still up to the user's mod
 *  to decide whether it needs this ore or not.
 */
public final class Material {

  // No need to worry as all of these blocks and items are registered correctly and only once.

  public static final ArrayList<AbstractMaterial> list = new ArrayList<>(200);

  // vanilla
  public static final OreMaterial COAL         = new OreMaterial("coal",     Items.COAL,         Blocks.COAL_BLOCK,    Blocks.COAL_ORE);
  public static final Metal       IRON         = new Metal(      "iron",     Items.IRON_INGOT,   Blocks.IRON_BLOCK,    Blocks.IRON_ORE, Items.IRON_NUGGET);
  public static final Metal       GOLD         = new Metal(      "gold",     Items.GOLD_INGOT,   Blocks.GOLD_BLOCK,    Blocks.GOLD_ORE, Items.GOLD_NUGGET);
  public static final Gem         LAPIS_LAZULI = new Gem(        "lapis",    Items.LAPIS_LAZULI, Blocks.LAPIS_BLOCK,   Blocks.LAPIS_ORE); // OreDictionary Name
  public static final OreMaterial REDSTONE     = new OreMaterial("redstone", Items.REDSTONE,     Blocks.REDSTONE_BLOCK,Blocks.REDSTONE_ORE);
  public static final Gem         DIAMOND      = new Gem(        "diamond",  Items.DIAMOND,      Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE);
  public static final Gem         EMERALD      = new Gem(        "emerald",  Items.EMERALD,      Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE);
  public static final Gem         QUARTZ       = new Gem(        "quartz",   Items.QUARTZ,       Blocks.QUARTZ_BLOCK,  Blocks.NETHER_QUARTZ_ORE);

  // gems
  public static final Gem AMETHYST    = new Gem("amethyst",    MaterialColor.MAGENTA, 3, 7);
  public static final Gem AMBER       = new Gem("amber",       MaterialColor.ADOBE,   3, 7);
  public static final Gem CITRINE     = new Gem("citrine",     MaterialColor.YELLOW,  3, 7);
  public static final Gem MALACHITE   = new Gem("malachite",   MaterialColor.DIAMOND, 3, 7);
  public static final Gem PERIDOT     = new Gem("peridot",     MaterialColor.GRASS,   3, 7);
  public static final Gem RUBY        = new Gem("ruby",        MaterialColor.TNT,     3, 7);
  public static final Gem SAPPHIRE    = new Gem("sapphire",    MaterialColor.WATER,   3, 7);
  public static final Gem TANZANITE   = new Gem("tanzanite",   MaterialColor.PURPLE,  3, 7);
  public static final Gem TOPAZ       = new Gem("topaz",       MaterialColor.ADOBE,   3, 7);
  public static final Gem ROSE_QUARTZ = new Gem("rose_quartz", MaterialColor.PINK,    3, 7);
  
  // MapColor Quartz is slightly darker than Cloth or Snow
  /* Brightness Scale:
    1 SNOW   = new MapColor(8,  16777215);   (White)
    2 CLOTH  = new MapColor(3,  13092807);
    3 IRON   = new MapColor(6,  10987431);
    4 SILVER = new MapColor(22, 10066329);
    5 STONE  = new MapColor(11,  7368816);
    6 GRAY   = new MapColor(21,  5000268);
  */
  
  // common metals
  public static final Metal ALUMINUM = new Metal("aluminum", MaterialColor.ICE,    MiningStrength.STONE);
  public static final Metal COPPER   = new Metal("copper",   MaterialColor.ADOBE,  MiningStrength.STONE);
  public static final Metal LEAD     = new Metal("lead",     MaterialColor.STONE,  MiningStrength.STONE);
  public static final Metal NICKEL   = new Metal("nickel",   MaterialColor.IRON,   MiningStrength.STONE);
  public static final Metal TIN      = new Metal("tin",      MaterialColor.IRON,   MiningStrength.STONE);
  public static final Metal ZINC     = new Metal("zinc",     MaterialColor.IRON,   MiningStrength.STONE);

  // semi-rare metals
  public static final Metal SILVER   = new Metal("silver",   MaterialColor.WOOL,   MiningStrength.IRON);
  public static final Metal COBALT   = new Metal("cobalt",   MaterialColor.BLUE,   MiningStrength.IRON);

  // rare metals
  public static final Metal PLATINUM = new Metal("platinum", MaterialColor.ICE,    MiningStrength.IRON);
  public static final Metal TITANIUM = new Metal("titanium", MaterialColor.SNOW,   MiningStrength.IRON);

  // manufactured metals
  /** Metal alloy of Copper and Zinc. Generally 2 parts Copper, 1 part Zinc.
   *  Used in applications where corrosion resistance and low friction is required, such as door hinges and gears. */
  public static final Metal BRASS    = new ManufacturedMetal("brass",  MaterialColor.YELLOW, MiningStrength.IRON);
  /** Metal alloy of Tin and Copper. Stronger and more durable than Copper alone. */
  public static final Metal BRONZE   = new ManufacturedMetal("bronze", MaterialColor.ADOBE,  MiningStrength.IRON);
  /** Metal alloy of Iron and Nickel. Known for its strong resistance to heat expansion.
   *  Has a simplified Nickel/Iron ratio of 3/5 or 1/2. */
  public static final Metal INVAR    = new ManufacturedMetal("invar",  MaterialColor.SAND,   MiningStrength.IRON);
  /** An advanced version of Iron. Metal alloy of Iron with a very small amount of Carbon. */
  public static final Metal STEEL    = new ManufacturedMetal("steel",  MaterialColor.GRAY,   MiningStrength.IRON);
  
  // other materials
  public static final OreMaterial SILICON   = new OreMaterial("silicon",   MaterialColor.GRAY,   MiningStrength.IRON);
  public static final OreMaterial URANIUM   = new OreMaterial("uranium",   MaterialColor.LIME,   MiningStrength.IRON);
  public static final OreMaterial YELLORIUM = new OreMaterial("yellorium", MaterialColor.YELLOW, MiningStrength.ANY);

}
