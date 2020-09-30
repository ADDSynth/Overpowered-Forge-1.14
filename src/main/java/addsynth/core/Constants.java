package addsynth.core;

import addsynth.core.util.color.ColorCode;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public final class Constants {

public static final int world_height = 256;
public static final int sea_level = 63;
public static final int chunk_size = 16;

public static final int ticks_per_second = 20;
public static final int ticks_per_minute = ticks_per_second * 60;
public static final int ticks_per_hour = ticks_per_minute * 60;
public static final int ticks_per_day = ticks_per_hour * 24;

public static final double tick_time_in_seconds = 1.0 / ticks_per_second;
public static final long tick_time_in_milliseconds = 1000 / ticks_per_second;
public static final long tick_time_in_nanoseconds = 1_000_000_000 / ticks_per_second;

public static final int DOWN  = Direction.DOWN.ordinal();
public static final int UP    = Direction.UP.ordinal();
public static final int NORTH = Direction.NORTH.ordinal();
public static final int SOUTH = Direction.SOUTH.ordinal();
public static final int WEST  = Direction.WEST.ordinal();
public static final int EAST  = Direction.EAST.ordinal();

/** Default error string to show, if attempting to get a status message from TileEntities. */
public static final String null_error = ColorCode.ERROR.toString() + "[Null Error]";

@SuppressWarnings("deprecation")
public static final float block_resistance = Blocks.STONE.getExplosionResistance(); //6.0f; // same as Stone
@SuppressWarnings("deprecation")
public static final float infinite_resistance = Blocks.BEDROCK.getExplosionResistance();

public static final int   sword_damage   = 3;
public static final float shovel_damage  = 1.5f;
public static final int   pickaxe_damage = 1;
public static final float axe_damage     = 6.0f;

// for hoes, the speed depends on the Material, so check the speeds set in the Items class and make your best judgement.
public static final float sword_speed   = -2.4f;
public static final float pickaxe_speed = -2.8f;
public static final float shovel_speed  = -3.0f;
public static final float axe_speed     = -3.2f;

public static final float[] light_level = new float[] { // UNUSED Constants.light_level[]
  0.0f, 1.0f / 15, 2.0f / 15, 3.0f / 15, 4.0f / 15, 5.0f / 15, 6.0f / 15, 7.0f / 15,
  8.0f / 15, 9.0f / 15, 10.0f / 15, 11.0f / 15, 12.0f / 15, 13.0f / 15, 14.0f / 15, 1.0f
};

}
