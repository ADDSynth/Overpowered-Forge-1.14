package addsynth.core.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Features {

  public static ForgeConfigSpec.BooleanValue caution_block;
  public static ForgeConfigSpec.BooleanValue music_box;
  public static ForgeConfigSpec.BooleanValue music_sheet;
  public static ForgeConfigSpec.BooleanValue scythes;
  public static ForgeConfigSpec.BooleanValue team_manager;

  public static ForgeConfigSpec.BooleanValue bronze_trophy;
  public static ForgeConfigSpec.BooleanValue silver_trophy;
  public static ForgeConfigSpec.BooleanValue gold_trophy;
  public static ForgeConfigSpec.BooleanValue platinum_trophy;

  public static final boolean trophies(){
    return bronze_trophy.get() || silver_trophy.get() || gold_trophy.get() || platinum_trophy.get();
  }

  private static final Pair<Features, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Features::new);
  public static final Features INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public Features(final ForgeConfigSpec.Builder builder){

    builder.push("Feature Disable");

    caution_block = builder.define("Caution Block", true);
    music_box     = builder.define("Music Box",     true);
    music_sheet   = builder.define("Music Sheet",   true);
    scythes       = builder.define("Scythes",       true);
    team_manager  = builder.define("Team Manager",  true);

    builder.push("Trophies");
    bronze_trophy   = builder.define("Bronze Trophy",   true);
    silver_trophy   = builder.define("Silver Trophy",   true);
    gold_trophy     = builder.define("Gold Trophy",     true);
    platinum_trophy = builder.define("Platinum Trophy", true);
    builder.pop();
    
    builder.pop();
  }

}
