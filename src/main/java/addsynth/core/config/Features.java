package addsynth.core.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Features {

  public static ForgeConfigSpec.BooleanValue caution_block;
  public static ForgeConfigSpec.BooleanValue music_box;
  public static ForgeConfigSpec.BooleanValue music_sheet;
  public static ForgeConfigSpec.BooleanValue scythes;

  private static final Pair<Features, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Features::new);
  public static final Features INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public Features(final ForgeConfigSpec.Builder builder){

    builder.push("Feature Disable");

    caution_block = builder.define("Caution Block", true);
    music_box     = builder.define("Music Box", true);
    music_sheet   = builder.define("Music Sheet", true);
    scythes       = builder.define("Scythes", true);

    builder.pop();
  }

}
