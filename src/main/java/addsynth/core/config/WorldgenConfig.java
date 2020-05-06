package addsynth.core.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.core.Constants;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 *  @see net.minecraft.world.biome.DefaultBiomeFeatures
 */
public final class WorldgenConfig {

  private static final int DEFAULT_MIN_HEIGHT = 5;
  
  private static final int DEFAULT_GEM_MAX_HEIGHT = 40;
  private static final int DEFAULT_GEM_TRIES = 4;
  
  // should be the same as Iron Ore
  private static final int DEFAULT_COMMON_METAL_MAX_HEIGHT = 128;
  private static final int DEFAULT_COMMON_METAL_TRIES = 15;
  private static final int DEFAULT_COMMON_METAL_ORE_SIZE = 9;
  // 8x  (0 - 120) every 15 levels

  private static final int DEFAULT_SILVER_ORE_MAX_HEIGHT = 64;
  private static final int DEFAULT_SILVER_ORE_TRIES = 4;
  private static final int DEFAULT_SILVER_ORE_SIZE = 9;
  // Gold: 2x (0 - 32) every 16 levels,     Silver: 4x (0 - 64) every 16 levels.
  
  private static final int DEFAULT_RARE_METAL_MAX_HEIGHT = 96;
  private static final int DEFAULT_RARE_METAL_TRIES = 3;
  private static final int DEFAULT_RARE_METAL_ORE_SIZE = 7;
  // Rare: 3x (0 - 96) every 32 levels.
  

  public static ForgeConfigSpec.BooleanValue generate_ruby;
  public static ForgeConfigSpec.IntValue ruby_min_height;
  public static ForgeConfigSpec.IntValue ruby_max_height;
  public static ForgeConfigSpec.IntValue ruby_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_topaz;
  public static ForgeConfigSpec.IntValue topaz_min_height;
  public static ForgeConfigSpec.IntValue topaz_max_height;
  public static ForgeConfigSpec.IntValue topaz_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_citrine;
  public static ForgeConfigSpec.IntValue citrine_min_height;
  public static ForgeConfigSpec.IntValue citrine_max_height;
  public static ForgeConfigSpec.IntValue citrine_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_emerald;
  public static ForgeConfigSpec.IntValue emerald_min_height;
  public static ForgeConfigSpec.IntValue emerald_max_height;
  public static ForgeConfigSpec.IntValue emerald_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_sapphire;
  public static ForgeConfigSpec.IntValue sapphire_min_height;
  public static ForgeConfigSpec.IntValue sapphire_max_height;
  public static ForgeConfigSpec.IntValue sapphire_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_amethyst;
  public static ForgeConfigSpec.IntValue amethyst_min_height;
  public static ForgeConfigSpec.IntValue amethyst_max_height;
  public static ForgeConfigSpec.IntValue amethyst_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_tin;
  public static ForgeConfigSpec.IntValue tin_min_height;
  public static ForgeConfigSpec.IntValue tin_max_height;
  public static ForgeConfigSpec.IntValue tin_spawn_tries;
  public static ForgeConfigSpec.IntValue tin_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_copper;
  public static ForgeConfigSpec.IntValue copper_min_height;
  public static ForgeConfigSpec.IntValue copper_max_height;
  public static ForgeConfigSpec.IntValue copper_spawn_tries;
  public static ForgeConfigSpec.IntValue copper_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_aluminum;
  public static ForgeConfigSpec.IntValue aluminum_min_height;
  public static ForgeConfigSpec.IntValue aluminum_max_height;
  public static ForgeConfigSpec.IntValue aluminum_spawn_tries;
  public static ForgeConfigSpec.IntValue aluminum_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_silver;
  public static ForgeConfigSpec.IntValue silver_min_height;
  public static ForgeConfigSpec.IntValue silver_max_height;
  public static ForgeConfigSpec.IntValue silver_spawn_tries;
  public static ForgeConfigSpec.IntValue silver_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_platinum;
  public static ForgeConfigSpec.IntValue platinum_min_height;
  public static ForgeConfigSpec.IntValue platinum_max_height;
  public static ForgeConfigSpec.IntValue platinum_spawn_tries;
  public static ForgeConfigSpec.IntValue platinum_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_titanium;
  public static ForgeConfigSpec.IntValue titanium_min_height;
  public static ForgeConfigSpec.IntValue titanium_max_height;
  public static ForgeConfigSpec.IntValue titanium_spawn_tries;
  public static ForgeConfigSpec.IntValue titanium_ore_size;

  private static final Pair<WorldgenConfig, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(WorldgenConfig::new);
  public static final WorldgenConfig INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public WorldgenConfig(final ForgeConfigSpec.Builder builder){
    builder.push("Worldgen");
    
    builder.push("Ruby Ore");
    generate_ruby    = builder.define("generate", true);
    ruby_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    ruby_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    ruby_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Topaz Ore");
    generate_topaz    = builder.define("generate", true);
    topaz_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    topaz_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    topaz_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Citrine Ore");
    generate_citrine    = builder.define("generate", true);
    citrine_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    citrine_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    citrine_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Emerald Ore");
    generate_emerald    = builder.comment(
      "Set this to false to revert back to Vanilla generation, which only generates\n"+
      "Emerald Ore in the Extreme Hills biomes.").define("generate", true);
    emerald_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    emerald_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    emerald_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Sapphire Ore");
    generate_sapphire    = builder.define("generate", true);
    sapphire_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    sapphire_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    sapphire_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Amethyst Ore");
    generate_amethyst    = builder.define("generate", true);
    amethyst_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    amethyst_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    amethyst_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();
    
    builder.push("Tin Ore");
    generate_tin    = builder.define("generate", true);
    tin_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    tin_max_height  = worldgen(builder, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    tin_spawn_tries = tries(builder, DEFAULT_COMMON_METAL_TRIES);
    tin_ore_size    = size(builder, DEFAULT_COMMON_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Copper Ore");
    generate_copper    = builder.define("generate", true);
    copper_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    copper_max_height  = worldgen(builder, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    copper_spawn_tries = tries(builder, DEFAULT_COMMON_METAL_TRIES);
    copper_ore_size    = size(builder, DEFAULT_COMMON_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Aluminum Ore");
    generate_aluminum    = builder.define("generate", true);
    aluminum_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    aluminum_max_height  = worldgen(builder, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    aluminum_spawn_tries = tries(builder, DEFAULT_COMMON_METAL_TRIES);
    aluminum_ore_size    = size(builder, DEFAULT_COMMON_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Silver Ore");
    generate_silver    = builder.define("generate", true);
    silver_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    silver_max_height  = worldgen(builder, "maximum height", DEFAULT_SILVER_ORE_MAX_HEIGHT);
    silver_spawn_tries = tries(builder, DEFAULT_SILVER_ORE_TRIES);
    silver_ore_size    = size(builder, DEFAULT_SILVER_ORE_SIZE);
    builder.pop();

    builder.push("Platinum Ore");
    generate_platinum    = builder.define("generate", true);
    platinum_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    platinum_max_height  = worldgen(builder, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    platinum_spawn_tries = tries(builder, DEFAULT_RARE_METAL_TRIES);
    platinum_ore_size    = size(builder, DEFAULT_RARE_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Titanium Ore");
    generate_titanium    = builder.define("generate", true);
    titanium_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    titanium_max_height  = worldgen(builder, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    titanium_spawn_tries = tries(builder, DEFAULT_RARE_METAL_TRIES);
    titanium_ore_size    = size(builder, DEFAULT_RARE_METAL_ORE_SIZE);
    builder.pop();

    builder.pop();
  }

  private static final ForgeConfigSpec.IntValue worldgen(final ForgeConfigSpec.Builder builder, final String name, final int default_value){
    return builder.defineInRange(name, default_value, 0, Constants.world_height - 1);
  }

  private static final ForgeConfigSpec.IntValue tries(final ForgeConfigSpec.Builder builder, final int default_value){
    return builder.defineInRange("tries", default_value, 1, 40); // same as Vanilla Minecraft custom world gen settings.
  }

  private static final ForgeConfigSpec.IntValue size(final ForgeConfigSpec.Builder builder, final int default_value){
    return builder.defineInRange("size", default_value, 1, 20); // vanilla custom world settings has this set as 50.
  }

}
