package addsynth.overpoweredmod.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import addsynth.overpoweredmod.blocks.BlackHole;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  // 100% Thanks to this guy:
  // https://www.minecraftforge.net/forum/topic/72021-1142-how-does-config-work-in-forge-for-1142/
  // https://github.com/Cadiboo/Example-Mod/commit/43db50e176d758ade2338764d7e2fe1b63aae7dd#diff-cb1dc14ae764daf38ef877fabbe0d72aR37

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  private static final byte DEFAULT_BLACK_HOLE_RADIUS = 70;
  
  public static ForgeConfigSpec.ConfigValue<Integer> unknown_dimension_id;
  public static ForgeConfigSpec.ConfigValue<Integer> weird_biome_id;

  public static ForgeConfigSpec.BooleanValue creative_tab_gems;
  public static ForgeConfigSpec.BooleanValue creative_tab_machines;
  public static ForgeConfigSpec.BooleanValue creative_tab_tools;
  public static ForgeConfigSpec.BooleanValue creative_tab_metals;

  public static ForgeConfigSpec.ConfigValue<Integer> default_laser_distance;
  public static ForgeConfigSpec.BooleanValue lasers_emit_light;
  public static ForgeConfigSpec.ConfigValue<Integer> laser_light_level;
  public static ForgeConfigSpec.BooleanValue lasers_set_entities_on_fire;
  public static ForgeConfigSpec.BooleanValue laser_damage_depends_on_world_difficulty;
  
  public static final byte LASER_DAMAGE_PEACEFUL_DIFFICULTY = 2;
  public static final byte LASER_DAMAGE_EASY_DIFFICULTY     = 4; // 2 hearts
  public static final byte LASER_DAMAGE_NORMAL_DIFFICULTY   = 6; // 3 hearts
  public static final byte LASER_DAMAGE_HARD_DIFFICULTY     = 10; // 5 hearts
  
  public static ForgeConfigSpec.BooleanValue randomize_black_hole_radius;
  public static ForgeConfigSpec.BooleanValue black_hole_radius_depends_on_world_difficulty;
  public static ForgeConfigSpec.ConfigValue<Integer> black_hole_radius;
  public static ForgeConfigSpec.ConfigValue<Integer> minimum_black_hole_radius;
  public static ForgeConfigSpec.ConfigValue<Integer> maximum_black_hole_radius;
  public static ForgeConfigSpec.BooleanValue alert_players_of_black_hole;
  public static ForgeConfigSpec.BooleanValue black_holes_erase_bedrock;
  public static ForgeConfigSpec.ConfigValue<List<Integer>> black_hole_dimension_blacklist;

  public static final int BLACK_HOLE_PEACEFUL_DIFFICULTY_RADIUS = 10;
  public static final int BLACK_HOLE_EASY_DIFFICULTY_RADIUS     = 30;
  public static final int BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS   = 50;
  public static final int BLACK_HOLE_HARD_DIFFICULTY_RADIUS     = 70;
  
  public static ForgeConfigSpec.BooleanValue drop_for_zombie;
  public static ForgeConfigSpec.BooleanValue drop_for_zombie_villager;
  public static ForgeConfigSpec.BooleanValue drop_for_husk;
  public static ForgeConfigSpec.BooleanValue drop_for_creeper;
  public static ForgeConfigSpec.BooleanValue drop_for_skeleton;
  public static ForgeConfigSpec.BooleanValue drop_for_spider;
  public static ForgeConfigSpec.BooleanValue drop_for_cave_spider;
  public static ForgeConfigSpec.BooleanValue drop_for_zombie_pigman;
  public static ForgeConfigSpec.BooleanValue drop_for_blaze;
  public static ForgeConfigSpec.BooleanValue drop_for_ghast;
  public static ForgeConfigSpec.BooleanValue drop_for_witch;
  public static ForgeConfigSpec.BooleanValue drop_for_magma_cube;
  public static ForgeConfigSpec.BooleanValue drop_for_enderman;
  public static ForgeConfigSpec.BooleanValue drop_for_wither_skeleton;
  public static ForgeConfigSpec.BooleanValue drop_for_stray;
  public static ForgeConfigSpec.BooleanValue drop_for_guardian;
  public static ForgeConfigSpec.BooleanValue drop_for_elder_guardian;
  public static ForgeConfigSpec.BooleanValue drop_for_shulker;
  public static ForgeConfigSpec.BooleanValue drop_for_vex;
  public static ForgeConfigSpec.BooleanValue drop_for_evocation_illager;
  public static ForgeConfigSpec.BooleanValue drop_for_vindication_illager;

  public static ForgeConfigSpec.BooleanValue show_advanced_config;

  public Config(final ForgeConfigSpec.Builder builder){

    builder.push("Main");

    // IDs
    builder.push("IDs");
    unknown_dimension_id = builder.worldRestart().define("Unknown Dimension ID", -20189);
    // weird_biome_id       = get("Unknion Biome ID", IDS, 3072).getInt();
    builder.pop();

    // Creative Tabs
    builder.push("Creative Tabs");
    creative_tab_gems     = builder.define("Enable Gems Creative Tab", false);
    creative_tab_machines = builder.define("Enable Machines Creative Tab", false);
    creative_tab_tools    = builder.define("Enable Tools Creative Tab", false);
    creative_tab_metals   = builder.define("Enable Metals Creative Tab", false);
    builder.pop();
  
    // Laser Config
    builder.push("Lasers");
    default_laser_distance      = builder.defineInRange("Default Laser Distance", 30, 0, 1000);
    lasers_emit_light           = builder.comment("You can turn this off if you feel like Laser Beams are giving you too much lag.")
                                         .define("Lasers Beams emit light", true);
    laser_light_level           = builder.comment("The Light level that Laser Beams emit.")
                                         .defineInRange("Laser Light Amount", 15, 0, 15);
    lasers_set_entities_on_fire = builder.comment("If set to true, Living Entities that are inside Laser Beams will receive fire damage.")
                                         .define("Lasers Set Entities On Fire", true);
    laser_damage_depends_on_world_difficulty = builder.define("Laser Damage depends on World Difficulty", true); // PRIORITY: Will I still need to set the comment to a null or empty string?
    builder.pop();
    
    // Black Hole Config
    builder.push("Black Hole");
    randomize_black_hole_radius    = builder.comment(
      "If set to true, Black Holes will set their radius to a random integer between the Min and Max values.\n"+
      "If 'Black Hole Radius depends on World Difficulty' is set to true, then Black Holes will deviate from\n"+
      "the predefined value chosen for that difficulty.")
                                            .define("Randomize Black Hole Radius", false);
    black_hole_radius_depends_on_world_difficulty = builder.define("Black Hole Radius depends on World Difficulty", false);
    black_hole_radius              = builder.comment("Defines Black Hole destroy radius. For slow computers, set this to a lower value.")
                                            .defineInRange("Black Hole radius", DEFAULT_BLACK_HOLE_RADIUS, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS);
    minimum_black_hole_radius      = builder.comment("This value is used if 'Randomize Black Hole radius' is set to true.") // FIX in all versions radius is not capitalized.
                                            .defineInRange("Minimum Black Hole radius", 20, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS);
    maximum_black_hole_radius      = builder.comment("This value is used if 'Randomize Black Hole radius' is set to true.")
                                            .defineInRange("Maximum Black Hole radius", DEFAULT_BLACK_HOLE_RADIUS, BlackHole.MIN_RADIUS, BlackHole.MAX_RADIUS);
    alert_players_of_black_hole    = builder.comment("If set to true, displays a chat message for all players in that world when a player sets down a Black Hole.")
                                            .define("Alert Players of Black Hole Occurrence", true);
    black_holes_erase_bedrock      = builder.comment("Warning: If used in Survival-Only worlds, you will not be able to replace the Bedrock.")
                                            .define("Black Holes erase Bedrock", false);
    black_hole_dimension_blacklist = builder.comment(
      "Specify a list of Dimension IDs you don't want the Black Hole to destroy. Placing a Black Hole\n"+
      "in these dimensions will not do anything. By default, the Black Hole is allowed in all dimensions.")
                                            .define("Black Hole Dimension ID Blacklist", new ArrayList<>());
    builder.pop();

    builder.push("Unidentified Mob Drops");
      builder.push("Overworld Mobs");
        drop_for_zombie          = builder.define("Drop for Zombie",          true);
        drop_for_zombie_villager = builder.define("Drop for Zombie Villager", true);
        drop_for_husk            = builder.define("Drop for Husk",            true);
        drop_for_skeleton        = builder.define("Drop for Skeleton",        true);
        drop_for_stray           = builder.define("Drop for Stray",           true);
        drop_for_spider          = builder.define("Drop for Spider",          true);
        drop_for_cave_spider     = builder.define("Drop for Cave Spider",     true);
        drop_for_creeper         = builder.define("Drop for Creeper",         true);
        drop_for_witch           = builder.define("Drop for Witch",           true);
        drop_for_guardian        = builder.define("Drop for Guardian",        true);
        drop_for_elder_guardian  = builder.define("Drop for Elder Guardian",  true);
        drop_for_vex             = builder.define("Drop for Vex",             true);
        drop_for_evocation_illager = builder.define("Drop for Evocation Illager", true);
        drop_for_vindication_illager = builder.define("Drop for Vindication Illager", true);
      builder.pop();
      builder.push("Nether Mobs");
        drop_for_zombie_pigman   = builder.define("Drop for Zombie Pigman",   true);
        drop_for_magma_cube      = builder.define("Drop for Magma Cube",      true);
        drop_for_ghast           = builder.define("Drop for Ghast",           true);
        drop_for_blaze           = builder.define("Drop for Blaze",           true);
        drop_for_wither_skeleton = builder.define("Drop for Wither Skeleton", true);
      builder.pop();
      builder.push("End Mobs");
        drop_for_enderman        = builder.define("Drop for Enderman",        true);
        drop_for_shulker         = builder.define("Drop for Shulker",         true);
      builder.pop();
    builder.pop();
    
    builder.push("Advanced");
    show_advanced_config = builder.comment(
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the values.cfg file allow you access to internal game values,\n"+
      "and adjusting them will vastly alter gameplay. They are only intended to be used for debug, testing, or\n"+
      "experimental purposes. In order to maintain a standard gameplay experience (the way the author intended)\n"+
      "we encourage you to leave these values at their defaults. (However, modpack authors may want to adjust these\n"+
      "values in order to create a balanced gameplay.)")
                                  .define("Show Advanced Config in Client Gui", false);
    builder.pop();

    builder.pop();
  }

}
