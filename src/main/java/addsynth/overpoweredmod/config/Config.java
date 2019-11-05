package addsynth.overpoweredmod.config;

import java.io.File;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.blocks.unique.BlackHole;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Config extends Configuration {

  public static Config instance;

  private static final String IDS           = "IDs";
  private static final String CREATIVE_TABS = "Creative Tabs";
  private static final String LASERS        = "Lasers";
  private static final String BLACK_HOLE    = "Black Hole";
  private static final String DROPS         = "Unidentified Mob Drops";
  private static final String OVERWORLD_MOBS = DROPS + ".Overworld Mobs";
  private static final String NETHER_MOBS    = DROPS + ".Nether Mobs";
  private static final String END_MOBS       = DROPS + ".End Mobs";
  private static final String ADVANCED       = "Advanced";

  private static final byte DEFAULT_BLACK_HOLE_RADIUS = 70;
  
  public static int unknown_dimension_id;
  public static int weird_biome_id;

  public static int default_laser_distance;
  public static boolean lasers_emit_light;
  public static int laser_light_level;
  public static boolean lasers_set_entities_on_fire;
  public static boolean laser_damage_depends_on_world_difficulty;
  
  public static final byte LASER_DAMAGE_PEACEFUL_DIFFICULTY = 2;
  public static final byte LASER_DAMAGE_EASY_DIFFICULTY     = 4; // 2 hearts
  public static final byte LASER_DAMAGE_NORMAL_DIFFICULTY   = 6; // 3 hearts
  public static final byte LASER_DAMAGE_HARD_DIFFICULTY     = 10; // 5 hearts
  
  public static boolean randomize_black_hole_radius;
  public static boolean black_hole_radius_depends_on_world_difficulty;
  public static int black_hole_radius;
  public static int minimum_black_hole_radius;
  public static int maximum_black_hole_radius;
  public static boolean alert_players_of_black_hole;
  public static boolean black_holes_erase_bedrock;
  public static int[] black_hole_dimension_blacklist;

  public static final byte BLACK_HOLE_PEACEFUL_DIFFICULTY_RADIUS = 10;
  public static final byte BLACK_HOLE_EASY_DIFFICULTY_RADIUS     = 30;
  public static final byte BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS   = 50;
  public static final byte BLACK_HOLE_HARD_DIFFICULTY_RADIUS     = 70;
  
  public static boolean drop_for_zombie;
  public static boolean drop_for_zombie_villager;
  public static boolean drop_for_husk;
  public static boolean drop_for_creeper;
  public static boolean drop_for_skeleton;
  public static boolean drop_for_spider;
  public static boolean drop_for_cave_spider;
  public static boolean drop_for_zombie_pigman;
  public static boolean drop_for_blaze;
  public static boolean drop_for_ghast;
  public static boolean drop_for_witch;
  public static boolean drop_for_magma_cube;
  public static boolean drop_for_enderman;
  public static boolean drop_for_wither_skeleton;
  public static boolean drop_for_stray;
  public static boolean drop_for_guardian;
  public static boolean drop_for_elder_guardian;
  public static boolean drop_for_shulker;
  public static boolean drop_for_vex;
  public static boolean drop_for_evocation_illager;
  public static boolean drop_for_vindication_illager;

  public static boolean show_advanced_config;

  public Config(File file){
    super(file, true);
    load_values();
  }

  public static final void initialize(File file){
    Debug.log_setup_info("Begin initializing Main Config...");
    instance = new Config(file);
    Debug.log_setup_info("Finished initializing Main Config.");
  }

  private final void load_values(){

    // IDs
    unknown_dimension_id = get(IDS, "Unknown Dimension ID", -20189).getInt();
    // weird_biome_id       = get("Unknion Biome ID", IDS, 3072).getInt();

    // Creative Tabs
    Features.creative_tab_gems     = get(CREATIVE_TABS, "Enable Gems Creative Tab", false).getBoolean();
    Features.creative_tab_machines = get(CREATIVE_TABS, "Enable Machines Creative Tab", false).getBoolean();
    Features.creative_tab_tools    = get(CREATIVE_TABS, "Enable Tools Creative Tab", false).getBoolean();
    Features.creative_tab_metals   = get(CREATIVE_TABS, "Enable Metals Creative Tab", false).getBoolean();
  
    // Laser Config
    default_laser_distance      = getInt("Default Laser Distance", LASERS, 30, 0, 1000, "");
    lasers_emit_light           = getBoolean("Lasers Beams emit light", LASERS, true, "You can turn this off if you feel like Laser Beams are giving you too much lag.");
    laser_light_level           = getInt("Laser Light Amount", LASERS, 15, 0, 15, "The Light level that Laser Beams emit.");
    lasers_set_entities_on_fire = getBoolean("Lasers Set Entities On Fire", LASERS, true, "If set to true, Living Entities that are inside Laser Beams will receive fire damage.");
    laser_damage_depends_on_world_difficulty = getBoolean("Laser Damage depends on World Difficulty", LASERS, true, "");
    
    // Black Hole Config
    randomize_black_hole_radius    = getBoolean("Randomize Black Hole Radius", BLACK_HOLE, false,
      "If set to true, Black Holes will set their radius to a random integer between the Min and Max values.\n"+
      "If 'Black Hole Radius depends on World Difficulty' is set to true, then Black Holes will deviate from\n"+
      "the predefined value chosen for that difficulty.");
    black_hole_radius_depends_on_world_difficulty = getBoolean("Black Hole Radius depends on World Difficulty", BLACK_HOLE, false, "");
    black_hole_radius              = getInt("Black Hole radius", BLACK_HOLE, DEFAULT_BLACK_HOLE_RADIUS, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS, "Defines Black Hole destroy radius. For slow computers, set this to a lower value.");
    minimum_black_hole_radius      = getInt("Minimum Black Hole radius", BLACK_HOLE, 20, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS, "This value is used if 'Randomize Black Hole radius' is set to true.");
    maximum_black_hole_radius      = getInt("Maximum Black Hole radius", BLACK_HOLE, DEFAULT_BLACK_HOLE_RADIUS, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS, "This value is used if 'Randomize Black Hole radius' is set to true.");
    alert_players_of_black_hole    = getBoolean("Alert Players of Black Hole Occurrence", BLACK_HOLE, true, "If set to true, displays a chat message for all players in that world when a player sets down a Black Hole.");
    black_holes_erase_bedrock      = getBoolean("Black Holes erase Bedrock", BLACK_HOLE, false, "Warning: If used in Survival-Only worlds, you will not be able to replace the Bedrock.");
    black_hole_dimension_blacklist = get(BLACK_HOLE, "Black Hole Dimension ID Blacklist", new int[0],
      "Specify a list of Dimension IDs you don't want the Black Hole to destroy. Placing a Black Hole\n"+
      "in these dimensions will not do anything. By default, the Black Hole is allowed in all dimensions.").getIntList();
  
    drop_for_zombie          = get(OVERWORLD_MOBS, "Drop for Zombie",          true).getBoolean();
    drop_for_zombie_villager = get(OVERWORLD_MOBS, "Drop for Zombie Villager", true).getBoolean();
    drop_for_husk            = get(OVERWORLD_MOBS, "Drop for Husk",            true).getBoolean();
    drop_for_skeleton        = get(OVERWORLD_MOBS, "Drop for Skeleton",        true).getBoolean();
    drop_for_stray           = get(OVERWORLD_MOBS, "Drop for Stray",           true).getBoolean();
    drop_for_spider          = get(OVERWORLD_MOBS, "Drop for Spider",          true).getBoolean();
    drop_for_cave_spider     = get(OVERWORLD_MOBS, "Drop for Cave Spider",     true).getBoolean();
    drop_for_creeper         = get(OVERWORLD_MOBS, "Drop for Creeper",         true).getBoolean();
    drop_for_witch           = get(OVERWORLD_MOBS, "Drop for Witch",           true).getBoolean();
    drop_for_guardian        = get(OVERWORLD_MOBS, "Drop for Guardian",        true).getBoolean();
    drop_for_elder_guardian  = get(OVERWORLD_MOBS, "Drop for Elder Guardian",  true).getBoolean();
    drop_for_vex             = get(OVERWORLD_MOBS, "Drop for Vex",             true).getBoolean();
    drop_for_evocation_illager = get(OVERWORLD_MOBS, "Drop for Evocation Illager", true).getBoolean();
    drop_for_vindication_illager = get(OVERWORLD_MOBS, "Drop for Vindication Illager", true).getBoolean();
    drop_for_zombie_pigman   = get(NETHER_MOBS,    "Drop for Zombie Pigman",   true).getBoolean();
    drop_for_magma_cube      = get(NETHER_MOBS,    "Drop for Magma Cube",      true).getBoolean();
    drop_for_ghast           = get(NETHER_MOBS,    "Drop for Ghast",           true).getBoolean();
    drop_for_blaze           = get(NETHER_MOBS,    "Drop for Blaze",           true).getBoolean();
    drop_for_wither_skeleton = get(NETHER_MOBS,    "Drop for Wither Skeleton", true).getBoolean();
    drop_for_enderman        = get(END_MOBS,       "Drop for Enderman",        true).getBoolean();
    drop_for_shulker         = get(END_MOBS,       "Drop for Shulker",         true).getBoolean();
    
    show_advanced_config = getBoolean("Show Advanced Config in Client Gui", ADVANCED, false,
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the values.cfg file allow you access to internal game values,\n"+
      "and adjusting them will vastly alter gameplay. They are only intended to be used for debug, testing, or\n"+
      "experimental purposes. In order to maintain a standard gameplay experience (the way the author intended)\n"+
      "we encourage you to leave these values at their defaults. (However, modpack authors may want to adjust these\n"+
      "values in order to create a balanced gameplay.)");
    
    if(this.hasChanged()){
      save();
    }
  }

  @SubscribeEvent
  public final void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
    if(event.getModID().equals(OverpoweredMod.MOD_ID)){
      this.load_values();
    }
  }
  
}
