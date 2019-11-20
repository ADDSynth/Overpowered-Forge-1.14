package addsynth.core.config;

import java.io.File;
import addsynth.core.ADDSynthCore;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.config.ModConfig;

/**
 *  @see net.minecraft.world.gen.ChunkGeneratorSettings.Factory
 */
public final class WorldgenConfig extends ModConfig {

  public static WorldgenConfig instance;

  private static final String RUBY     = "Ruby Ore";
  private static final String TOPAZ    = "Topaz Ore";
  private static final String CITRINE  = "Citrine Ore";
  private static final String EMERALD  = "Emerald Ore";
  private static final String SAPPHIRE = "Sapphire Ore";
  private static final String AMETHYST = "Amethyst Ore";
  private static final String TIN      = "Tin Ore";
  private static final String COPPER   = "Copper Ore";
  private static final String ALUMINUM = "Aluminum Ore";
  private static final String SILVER   = "Silver Ore";
  private static final String PLATINUM = "Platinum Ore";
  private static final String TITANIUM = "Titanium Ore";

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
  

  public static boolean generate_ruby;
  public static int ruby_min_height;
  public static int ruby_max_height;
  public static int ruby_spawn_tries;

  public static boolean generate_topaz;
  public static int topaz_min_height;
  public static int topaz_max_height;
  public static int topaz_spawn_tries;

  public static boolean generate_citrine;
  public static int citrine_min_height;
  public static int citrine_max_height;
  public static int citrine_spawn_tries;

  public static boolean generate_emerald;
  public static int emerald_min_height;
  public static int emerald_max_height;
  public static int emerald_spawn_tries;

  public static boolean generate_sapphire;
  public static int sapphire_min_height;
  public static int sapphire_max_height;
  public static int sapphire_spawn_tries;

  public static boolean generate_amethyst;
  public static int amethyst_min_height;
  public static int amethyst_max_height;
  public static int amethyst_spawn_tries;

  public static boolean generate_tin;
  public static int tin_min_height;
  public static int tin_max_height;
  public static int tin_spawn_tries;
  public static int tin_ore_size;

  public static boolean generate_copper;
  public static int copper_min_height;
  public static int copper_max_height;
  public static int copper_spawn_tries;
  public static int copper_ore_size;

  public static boolean generate_aluminum;
  public static int aluminum_min_height;
  public static int aluminum_max_height;
  public static int aluminum_spawn_tries;
  public static int aluminum_ore_size;

  public static boolean generate_silver;
  public static int silver_min_height;
  public static int silver_max_height;
  public static int silver_spawn_tries;
  public static int silver_ore_size;

  public static boolean generate_platinum;
  public static int platinum_min_height;
  public static int platinum_max_height;
  public static int platinum_spawn_tries;
  public static int platinum_ore_size;

  public static boolean generate_titanium;
  public static int titanium_min_height;
  public static int titanium_max_height;
  public static int titanium_spawn_tries;
  public static int titanium_ore_size;

  public WorldgenConfig(final File file){
    super(file, true);
    load_values();
  }

  public static final void initialize(final File file){
    instance = new WorldgenConfig(file);
  }

  private final void load_values(){
    generate_ruby    = get(RUBY, "generate", true).getBoolean();
    ruby_min_height  = worldgen(RUBY, "minimum height", DEFAULT_MIN_HEIGHT);
    ruby_max_height  = worldgen(RUBY, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    ruby_spawn_tries = tries(RUBY, DEFAULT_GEM_TRIES);

    generate_topaz    = get(TOPAZ, "generate", true).getBoolean();
    topaz_min_height  = worldgen(TOPAZ, "minimum height", DEFAULT_MIN_HEIGHT);
    topaz_max_height  = worldgen(TOPAZ, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    topaz_spawn_tries = tries(TOPAZ, DEFAULT_GEM_TRIES);

    generate_citrine    = get(CITRINE, "generate", true).getBoolean();
    citrine_min_height  = worldgen(CITRINE, "minimum height", DEFAULT_MIN_HEIGHT);
    citrine_max_height  = worldgen(CITRINE, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    citrine_spawn_tries = tries(CITRINE, DEFAULT_GEM_TRIES);

    generate_emerald    = get(EMERALD, "generate", true, "Set this to false to revert back to "+
      "Vanilla generation, which only generates\nEmerald Ore in the Extreme Hills biomes.").getBoolean();
    emerald_min_height  = worldgen(EMERALD, "minimum height", DEFAULT_MIN_HEIGHT);
    emerald_max_height  = worldgen(EMERALD, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    emerald_spawn_tries = tries(EMERALD, DEFAULT_GEM_TRIES);

    generate_sapphire    = get(SAPPHIRE, "generate", true).getBoolean();
    sapphire_min_height  = worldgen(SAPPHIRE, "minimum height", DEFAULT_MIN_HEIGHT);
    sapphire_max_height  = worldgen(SAPPHIRE, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    sapphire_spawn_tries = tries(SAPPHIRE, DEFAULT_GEM_TRIES);

    generate_amethyst    = get(AMETHYST, "generate", true).getBoolean();
    amethyst_min_height  = worldgen(AMETHYST, "minimum height", DEFAULT_MIN_HEIGHT);
    amethyst_max_height  = worldgen(AMETHYST, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    amethyst_spawn_tries = tries(AMETHYST, DEFAULT_GEM_TRIES);
    
    generate_tin    = get(TIN, "generate", true).getBoolean();
    tin_min_height  = worldgen(TIN, "minimum height", DEFAULT_MIN_HEIGHT);
    tin_max_height  = worldgen(TIN, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    tin_spawn_tries = tries(TIN, DEFAULT_COMMON_METAL_TRIES);
    tin_ore_size    = size(TIN, DEFAULT_COMMON_METAL_ORE_SIZE);

    generate_copper    = get(COPPER, "generate", true).getBoolean();
    copper_min_height  = worldgen(COPPER, "minimum height", DEFAULT_MIN_HEIGHT);
    copper_max_height  = worldgen(COPPER, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    copper_spawn_tries = tries(COPPER, DEFAULT_COMMON_METAL_TRIES);
    copper_ore_size    = size(COPPER, DEFAULT_COMMON_METAL_ORE_SIZE);

    generate_aluminum    = get(ALUMINUM, "generate", true).getBoolean();
    aluminum_min_height  = worldgen(ALUMINUM, "minimum height", DEFAULT_MIN_HEIGHT);
    aluminum_max_height  = worldgen(ALUMINUM, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    aluminum_spawn_tries = tries(ALUMINUM, DEFAULT_COMMON_METAL_TRIES);
    aluminum_ore_size    = size(ALUMINUM, DEFAULT_COMMON_METAL_ORE_SIZE);

    generate_silver    = get(SILVER, "generate", true).getBoolean();
    silver_min_height  = worldgen(SILVER, "minimum height", DEFAULT_MIN_HEIGHT);
    silver_max_height  = worldgen(SILVER, "maximum height", DEFAULT_SILVER_ORE_MAX_HEIGHT);
    silver_spawn_tries = tries(SILVER, DEFAULT_SILVER_ORE_TRIES);
    silver_ore_size    = size(SILVER, DEFAULT_SILVER_ORE_SIZE);

    generate_platinum    = get(PLATINUM, "generate", true).getBoolean();
    platinum_min_height  = worldgen(PLATINUM, "minimum height", DEFAULT_MIN_HEIGHT);
    platinum_max_height  = worldgen(PLATINUM, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    platinum_spawn_tries = tries(PLATINUM, DEFAULT_RARE_METAL_TRIES);
    platinum_ore_size    = size(PLATINUM, DEFAULT_RARE_METAL_ORE_SIZE);

    generate_titanium    = get(TITANIUM, "generate", true).getBoolean();
    titanium_min_height  = worldgen(TITANIUM, "minimum height", DEFAULT_MIN_HEIGHT);
    titanium_max_height  = worldgen(TITANIUM, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    titanium_spawn_tries = tries(TITANIUM, DEFAULT_RARE_METAL_TRIES);
    titanium_ore_size    = size(TITANIUM, DEFAULT_RARE_METAL_ORE_SIZE);

    if(this.hasChanged()){
      save();
    }
  }

  private final int worldgen(final String category, final String name, final int default_value){
    return get(category, name, default_value, null, 0, 255).getInt();
  }

  private final int tries(final String category, final int default_value){
    return get(category, "tries", default_value, null, 1, 40).getInt(); // same as Vanilla Minecraft custom world gen settings.
  }

  private final int size(final String category, final int default_value){
    return get(category, "size", default_value, null, 1, 20).getInt(); // vanilla custom world settings has this set as 50.
  }

  @SubscribeEvent
  public final void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
    if(event.getModID().equals(ADDSynthCore.MOD_ID)){
      this.load_values();
    }
  }
  
}
