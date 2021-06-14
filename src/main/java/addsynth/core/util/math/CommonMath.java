package addsynth.core.util.math;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.java.JavaUtils;
import net.minecraft.util.math.MathHelper;

/** This class is meant to hold commom math functions, such as Rounds, Clamps, getMin, and getMax.
 *  @author ADDSynth
 */
public final class CommonMath {

  public static final int getMin(final int ... input){
    if(input == null){     throw new NullPointerException(    CommonMath.class.getName()+".getMin() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(CommonMath.class.getName()+".getMin() requires at least one integer as input."); }
    int min = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] < min){
        min = input[i];
      }
    }
    return min;
  }

  public static final long getMin(final long ... input){
    if(input == null){     throw new NullPointerException(    CommonMath.class.getName()+".getMin() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(CommonMath.class.getName()+".getMin() requires at least one integer as input."); }
    long min = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] < min){
        min = input[i];
      }
    }
    return min;
  }

  public static final int getMax(final int ... input){
    if(input == null){     throw new NullPointerException(    CommonMath.class.getName()+".getMax() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(CommonMath.class.getName()+".getMax() requires at least one integer as input."); }
    int max = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] > max){
        max = input[i];
      }
    }
    return max;
  }

  public static final long getMax(final long ... input){
    if(input == null){     throw new NullPointerException(    CommonMath.class.getName()+".getMax() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(CommonMath.class.getName()+".getMax() requires at least one integer as input."); }
    long max = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] > max){
        max = input[i];
      }
    }
    return max;
  }

  public static final short clamp(final short number, final short minimum, final short maximum){
    if(number < minimum){
      return minimum;
    }
    return number > maximum ? maximum : number;
  }
  
  // these clamp functions are here so I can find them, but they're also deprecated
  // so I know to replace them with Vanilla's methods. Please leave them as they are!

  /** @deprecated Look at what this function does. Bypass this and use the vanilla method instead. */
  @Deprecated
  public static final int clamp(final int number, final int minimum, final int maximum){
    return MathHelper.clamp(number, minimum, maximum);
  }

  public static final long clamp(final long number, final long minimum, final long maximum){
    if(number < minimum){
      return minimum;
    }
    return number > maximum ? maximum : number;
  }

  /** @deprecated Look at what this function does. Bypass this and use the vanilla method instead. */
  @Deprecated
  public static final float clamp(final float number, final float minimum, final float maximum){
    return MathHelper.clamp(number, minimum, maximum);
  }
  
  /** @deprecated Look at what this function does. Bypass this and use the vanilla method instead. */
  @Deprecated
  public static final double clamp(final double number, final double minimum, final float maximum){
    return MathHelper.clamp(number, minimum, maximum);
  }

  public static final int get_smallest_index(final short[] array){
    int smallest_index = 0;
    int i;
    for(i = 1; i < array.length; i++){
      if(array[i] < array[smallest_index]){
        smallest_index = i;
      }
    }
    return smallest_index;
  }

  public static final int get_smallest_index(final int[] array){
    int smallest_index = 0;
    int i;
    for(i = 1; i < array.length; i++){
      if(array[i] < array[smallest_index]){
        smallest_index = i;
      }
    }
    return smallest_index;
  }

  public static final int get_smallest_index(final long[] array){
    int smallest_index = 0;
    int i;
    for(i = 1; i < array.length; i++){
      if(array[i] < array[smallest_index]){
        smallest_index = i;
      }
    }
    return smallest_index;
  }

  public static final int get_smallest_index(final float[] array){
    int smallest_index = 0;
    int i;
    for(i = 1; i < array.length; i++){
      if(array[i] < array[smallest_index]){
        smallest_index = i;
      }
    }
    return smallest_index;
  }

  public static final int get_smallest_index(final double[] array){
    int smallest_index = 0;
    int i;
    for(i = 1; i < array.length; i++){
      if(array[i] < array[smallest_index]){
        smallest_index = i;
      }
    }
    return smallest_index;
  }

  /** Rounds input to the specified number of decimal places. */
  public static final double round(final double input, final int decimals){
    if(decimals == 0){
      return Math.round(input);
    }
    final double adjustment = Math.pow(10, decimals);
    return Math.round(input * adjustment) / adjustment;
  }

  public static final double floor(final double input, final int decimals){
    if(decimals == 0){
      return Math.floor(input);
    }
    final double adjustment = Math.pow(10, decimals);
    return Math.floor(input * adjustment) / adjustment;
  }
  
  public static final double ceiling(final double input, final int decimals){
    if(decimals == 0){
      return Math.ceil(input);
    }
    final double adjustment = Math.pow(10, decimals);
    return Math.ceil(input * adjustment) / adjustment;
  }
  
  public static final int RoundNearest(float value, int multiple){
    return Math.round(value / multiple) * multiple;
  }
  
  public static final long RoundNearest(double value, long multiple){
    return Math.round(value / multiple) * multiple;
  }
  
  public static final double RoundNearest(double value, double multiple){
    return Math.round(value / multiple) * multiple;
  }
  
  public static final int FloorNearest(double value, int multiple){
    return JavaUtils.cast_to_int((long)Math.floor(value / multiple) * multiple);
  }
  
  public static final long FloorNearest(double value, long multiple){
    return (long)(Math.floor(value / multiple) * multiple);
  }
  
  public static final double FloorNearest(double value, double multiple){
    return Math.floor(value / multiple) * multiple;
  }
  
  public static final int CeilingNearest(double value, int multiple){
    return JavaUtils.cast_to_int((long)Math.ceil(value / multiple) * multiple);
  }
  
  public static final long CeilingNearest(double value, long multiple){
    return (long)(Math.ceil(value / multiple) * multiple);
  }
  
  public static final double CeilingNearest(double value, double multiple){
    return Math.ceil(value / multiple) * multiple;
  }

  public static final double toPercentage(int top, int bottom, int number_of_decimals, RoundMode mode){
    if(bottom == 0){
      ADDSynthCore.log.error(new ArithmeticException("Divide by 0 Error"));
      return 0.0;
    }
    return toPercentage((double)top / bottom, number_of_decimals, mode);
  }
  
  public static final double toPercentage(double value, int number_of_decimals, RoundMode mode){
    double percent_value = 0.0;
    switch(mode){
    case Round:   percent_value =      round(value * 100, number_of_decimals); break;
    case Floor:   percent_value = Math.floor(value * 100); break;
    case Ceiling: percent_value = Math.ceil(value * 100); break;
    }
    return percent_value;
  }

  /** Checks a value against a left value and a right value and returns whichever value it is closest to.
   *  if it is equally close to both, we return the right value.
   * @param left_value
   * @param value
   * @param right_value
   */
  public static final int getExtreme(int left_value, int value, int right_value){
    final int left_check = Math.abs(left_value - value);
    final int right_check = Math.abs(right_value -value);
    if(left_check < right_check){
      return left_value;
    }
    return right_value;
  }

}
