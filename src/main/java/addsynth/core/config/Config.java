package addsynth.core.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public static ForgeConfigSpec.BooleanValue debug_mod_detection;
  public static ForgeConfigSpec.BooleanValue debug_materials_detection;
  public static ForgeConfigSpec.BooleanValue dump_map_colors;

  public static ForgeConfigSpec.BooleanValue show_advanced_config;

  public Config(final ForgeConfigSpec.Builder builder){

    builder.push("Main");

    builder.push("Debug");
    debug_mod_detection = builder.define("Print Mod Detection Results", false);
    dump_map_colors     = builder.define("Dump Map Colors", false);
    builder.pop();

    builder.push("Advanced");
    show_advanced_config = builder.comment(
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the worldgen.cfg file allow you access to internal game values,\n"+
      "and adjusting them will vastly alter gameplay. They are only intended to be used for debug, testing, or\n"+
      "experimental purposes. In order to maintain a standard gameplay experience (the way the author intended)\n"+
      "we encourage you to leave these values at their defaults. (However, modpack authors may want to adjust these\n"+
      "values in order to create a balanced gameplay.)")
      .define("Show Advanced Config in Client Gui", false);
    builder.pop();
    
    builder.pop();
  }

}
