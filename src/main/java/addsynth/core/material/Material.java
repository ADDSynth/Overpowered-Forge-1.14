package addsynth.core.material;

import java.util.ArrayList;
import addsynth.core.material.types.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

/**
 *  These mineral types are based on the real world, and thus always adhere to certain rules and
 *  properties. So we can define them once in a global setting. It's still up to the user's mod
 *  to decide whether it needs this ore or not.
 */
public final class Material {

  // No need to worry as all of these blocks and items are registered correctly and only once.

  public static final ArrayList<AbstractMaterial> list = new ArrayList<>(200);

  // vanilla
  public static final OreMaterial COAL         = new OreMaterial("coal",     Items.COAL,       Blocks.COAL_BLOCK,    Blocks.COAL_ORE);
  public static final Metal       IRON         = new Metal(      "iron",     Items.IRON_INGOT, Blocks.IRON_BLOCK,    Blocks.IRON_ORE, Items.IRON_NUGGET);
  public static final Metal       GOLD         = new Metal(      "gold",     Items.GOLD_INGOT, Blocks.GOLD_BLOCK,    Blocks.GOLD_ORE, Items.GOLD_NUGGET);
  public static final Gem         LAPIS_LAZULI = new Gem(        "lapis",    Items.DYE,        Blocks.LAPIS_BLOCK,   Blocks.LAPIS_ORE); // OreDictionary Name
  public static final OreMaterial REDSTONE     = new OreMaterial("redstone", Items.REDSTONE,   Blocks.REDSTONE_BLOCK,Blocks.REDSTONE_ORE);
  public static final Gem         DIAMOND      = new Gem(        "diamond",  Items.DIAMOND,    Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE);
  public static final Gem         EMERALD      = new Gem(        "emerald",  Items.EMERALD,    Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE);
  public static final Gem         QUARTZ       = new Gem(        "quartz",   Items.QUARTZ,     Blocks.QUARTZ_BLOCK,  Blocks.QUARTZ_ORE);

  // gems
  public static final Gem AMETHYST  = new Gem("amethyst",  MapColor.MAGENTA);
  public static final Gem AMBER     = new Gem("amber",     MapColor.ADOBE);
  public static final Gem CITRINE   = new Gem("citrine",   MapColor.YELLOW);
  public static final Gem MALACHITE = new Gem("malachite", MapColor.DIAMOND);
  public static final Gem PERIDOT   = new Gem("peridot",   MapColor.GRASS);
  public static final Gem RUBY      = new Gem("ruby",      MapColor.TNT);
  public static final Gem SAPPHIRE  = new Gem("sapphire",  MapColor.WATER);
  public static final Gem TANZANITE = new Gem("tanzanite", MapColor.PURPLE);
  public static final Gem TOPAZ     = new Gem("topaz",     MapColor.ADOBE);
  
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
  public static final Metal ALUMINUM = new Metal("aluminum", MapColor.ICE,    MiningStrength.STONE);
  public static final Metal COPPER   = new Metal("copper",   MapColor.ADOBE,  MiningStrength.STONE);
  public static final Metal LEAD     = new Metal("lead",     MapColor.STONE,  MiningStrength.STONE);
  public static final Metal NICKEL   = new Metal("nickel",   MapColor.IRON,   MiningStrength.STONE);
  public static final Metal TIN      = new Metal("tin",      MapColor.IRON,   MiningStrength.STONE);
  public static final Metal ZINC     = new Metal("zinc",     MapColor.IRON,   MiningStrength.STONE);

  // semi-rare metals
  public static final Metal SILVER   = new Metal("silver",   MapColor.CLOTH,  MiningStrength.IRON);
  public static final Metal COBALT   = new Metal("cobalt",   MapColor.BLUE,   MiningStrength.IRON);

  // rare metals
  public static final Metal PLATINUM = new Metal("platinum", MapColor.ICE,    MiningStrength.IRON);
  public static final Metal TITANIUM = new Metal("titanium", MapColor.SNOW,   MiningStrength.IRON);

  // manufactured metals
  public static final Metal BRASS    = new ManufacturedMetal("brass",  MapColor.YELLOW, MiningStrength.IRON);
  public static final Metal BRONZE   = new ManufacturedMetal("bronze", MapColor.ADOBE,  MiningStrength.IRON); // (combined form of tin and copper)
  public static final Metal INVAR    = new ManufacturedMetal("invar",  MapColor.SAND,   MiningStrength.IRON); // (combination of Iron and Nickel)
  public static final Metal STEEL    = new ManufacturedMetal("steel",  MapColor.GRAY,   MiningStrength.IRON); // (advanced version of iron)
  
  // other materials
  public static final OreMaterial SILICON   = new OreMaterial("silicon",   MapColor.GRAY,   OreType.ITEM,  MiningStrength.IRON);
  public static final OreMaterial URANIUM   = new OreMaterial("uranium",   MapColor.LIME,   OreType.BLOCK, MiningStrength.IRON);
  public static final OreMaterial YELLORIUM = new OreMaterial("yellorium", MapColor.YELLOW, OreType.ITEM,  MiningStrength.ANY);

  /** Call this function if you want a material in your mod.  */
  public static final void register(final BaseMaterial material){
  }

  public static final void register(final BaseMaterial material, boolean item, boolean block, boolean ore){
  }

  public static final void register(final OreMaterial material){// MAYBE: Materials have many different item and block types, perhaps I should use a bit mask enum?
  }

  public static final void register(final OreMaterial material, boolean item, boolean block, boolean ore, boolean dust){
  }

  public static final void register(final Gem material){
  }

  public static final void register(final Gem material, boolean gem, boolean shard, boolean block, boolean ore){
  }

  public static final void register(final Metal metal){
  }
  
  public static final void register(final Metal metal, boolean ingot, boolean block, boolean ore, boolean nugget, boolean plate, boolean dust){
  }

}
