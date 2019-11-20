package addsynth.core.config;

import java.io.File;
import addsynth.core.ADDSynthCore;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.config.ModConfig;

public final class Config extends ModConfig {

  public static Config instance;

  private static final String DEBUG = "Debug";
  private static final String ADVANCED = "Advanced";

  public static boolean debug_mod_detection;
  public static boolean debug_materials_detection;
  public static boolean dump_map_colors;

  public static boolean show_advanced_config;

  public Config(final File file){
    super(file, true);
    load_values();
  }

  public static final void initialize(final File file){
    instance = new Config(file);
  }

  private final void load_values(){

    debug_mod_detection       = get(DEBUG, "Print Mod Detection Results", false).getBoolean();
    dump_map_colors           = get(DEBUG, "Dump Map Colors", false).getBoolean();

    show_advanced_config = getBoolean("Show Advanced Config in Client Gui", ADVANCED, false,
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the worldgen.cfg file allow you access to internal game values,\n"+
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
    if(event.getModID().equals(ADDSynthCore.MOD_ID)){
      this.load_values();
    }
  }

}
