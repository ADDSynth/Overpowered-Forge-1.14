package addsynth.core.util.time;

import addsynth.core.util.math.CommonMath;
import net.minecraft.world.World;

public final class WorldTime {

  public static final long minecraft_day_in_ticks   = 24_000; // 20 real-life minutes.
  public static final long minecraft_day_in_seconds =  1_200;
  public static final long day_length_in_ticks      = 12_000;
  public static final long day_length_in_seconds    =    600;
  public static final long night_length_in_ticks    = 10_000;
  public static final long night_length_in_seconds  =    500;
  public static final long morning  =  1000; // I have no idea why the /time command sets morning as 1000.
  public static final long noon     =  6000;
  /** During 12000 to 13000, the light level goes from 15 to 0. */
  public static final long night    = 13000;
  public static final long midnight = 18000;
  /** During 23000 to 0, the sky light level goes from 0 to 15. */
  public static final long sunrise  = 23000;
  public static final long next_day = minecraft_day_in_ticks + morning;
  
  public static final long getTimeOfDay(final World world){
    return world.getDayTime() % minecraft_day_in_ticks;
  }

  public static final long getNextDay(final World world){
    final long world_time = world.getDayTime();
    if(world_time % minecraft_day_in_ticks > morning){
      return CommonMath.FloorNearest(world_time, minecraft_day_in_ticks) + next_day;
    }
    return CommonMath.FloorNearest(world_time, minecraft_day_in_ticks) + morning;
  }
  
  public static final long getNextNight(final World world){
    final long world_time = world.getDayTime();
    if(world_time % minecraft_day_in_ticks > night){
      return CommonMath.FloorNearest(world_time, minecraft_day_in_ticks) + minecraft_day_in_ticks + night;
    }
    return CommonMath.FloorNearest(world_time, minecraft_day_in_ticks) + night;
  }

}
