package addsynth.core.util.math;

public final class Movement {

  private static final double item_gravity = 0.04;
  private static final double item_drag    = 0.02;

  public static final double getUpwardsVelocity(int air_time){
    return item_gravity * air_time;
  }

  public static final double getHorizontalVelocity(int time_in_ticks, double distance){
    return distance / time_in_ticks;
  }

}
