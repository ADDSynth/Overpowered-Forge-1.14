package addsynth.core.config;

import java.io.File;
import addsynth.core.ADDSynthCore;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public final class Features extends ModConfig {

  public static Features instance;

  public static final String MAIN = "Main";

  public Features(final File file){
    super(file, true);
    load_values();
  }

  public static boolean caution_block;
  public static boolean music_box;
  public static boolean music_sheet;
  public static boolean scythes;

  public static final void initialize(final File file){
    instance = new Features(file);
  }

  private final void load_values(){

    caution_block    = get(MAIN, "Caution Block", true).getBoolean();
    music_box        = get(MAIN, "Music Box", true).getBoolean();
    music_sheet      = get(MAIN, "Music Sheet", true).getBoolean();
    scythes          = get(MAIN, "Scythes", true).getBoolean();

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
