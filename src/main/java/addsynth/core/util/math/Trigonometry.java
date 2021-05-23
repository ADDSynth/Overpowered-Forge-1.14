package addsynth.core.util.math;

public final class Trigonometry {

  public static final double getXComponent(final double angle){
    return Math.cos(angle * 2 * Math.PI);
  }

  public static final double getXComponent(final double angle, final double length){
    return Math.cos(angle * 2 * Math.PI) * length;
  }

  public static final double getYComponent(final double angle){
    return Math.sin(angle * 2 * Math.PI);
  }

  public static final double getYComponent(final double angle, final double length){
    return Math.sin(angle * 2 * Math.PI) * length;
  }

}
