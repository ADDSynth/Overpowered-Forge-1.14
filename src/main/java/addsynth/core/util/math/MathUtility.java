package addsynth.core.util.math;

import java.util.Random;
import javax.annotation.Nonnegative;
import addsynth.core.ADDSynthCore;
import addsynth.core.gui.widgets.scrollbar.Scrollbar;
import net.minecraft.util.math.MathHelper;

public final class MathUtility {

  public static final int getMin(final int ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMin() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMin() requires at least one integer as input."); }
    int min = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] < min){
        min = input[i];
      }
    }
    return min;
  }

  public static final long getMin(final long ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMin() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMin() requires at least one integer as input."); }
    long min = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] < min){
        min = input[i];
      }
    }
    return min;
  }

  public static final int getMax(final int ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMax() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMax() requires at least one integer as input."); }
    int max = input[0];
    for(int i = 1; i < input.length; i++){
      if(input[i] > max){
        max = input[i];
      }
    }
    return max;
  }

  public static final long getMax(final long ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMax() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMax() requires at least one integer as input."); }
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

  @Deprecated
  public static final float clamp(final float number, final float minimum, final float maximum){
    return MathHelper.clamp(number, minimum, maximum);
  }
  
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

  /** This divides a whole number as evenly as possible, with higher numbers closer towards the beginning.
   *  For example, dividing 18 into 5 equal parts will produce [4, 4, 4, 3, 3].
   * @param number
   * @param count
   */
  public static final int[] divide_evenly(@Nonnegative final int number, @Nonnegative final int count){
    if(number < 0 || count < 0){
      throw new IllegalArgumentException("The input parameters to MathUtility.divide_evenly() cannot be negative!");
    }
    if(count == 0){
      throw new ArithmeticException("Error in MathUtility.divide_evenly(). Cannot divide by 0!");
    }
    if(count == 1){
      return new int[] {number};
    }
    final int[] list = new int[count];
    final int equal = number / count; // integer division drops the decimal remainder, which is what we want.
    int remainder = number % count;
    int i;
    for(i = 0; i < count; i++){
      if(remainder > 0){
        list[i] = equal + 1;
        remainder -= 1;
      }
      else{
        list[i] = equal;
      }
    }
    return list;
  }

  /** If you want to extract a number as evenly as possible from a random list of numbers, this function produces
   *  another list of numbers which you can use to extract from the original list. And it uses a Round-Robin
   *  method, so bigger numbers will end up towards the beginning. It's kind of hard to explain, so I'll give
   *  you an example: If you're trying to extract 30 from the list [8, 15, 4, 1, 15], this function will produce
   *  the list [8, 9, 4, 1, 8], which, when extracted from the original list would produce [0, 6, 0, 0, 7].
   * @param number
   * @param list
   */
  public static final int[] divide_evenly(@Nonnegative int number, int[] list){
    final int length = list.length;
    final int[] final_list = new int[length]; // already initialized to all 0

    if(length == 0){
      ADDSynthCore.log.fatal(new IllegalArgumentException(MathUtility.class.getSimpleName()+".divide_evenly() cannot use an empty integer array."));
      return final_list;
    }
    if(number < 0){
      ADDSynthCore.log.fatal(new IllegalArgumentException("Cannot use "+MathUtility.class.getSimpleName()+".divide_evenly() to divide a negative number!"));
      return final_list;
    }
    if(length == 1){
      return new int[]{Math.min(number, Math.max(list[0], 0))};
    }

    int i;
    int count;
    int min_number;

    do{
      // reset
      count = 0;
      
      // get first index, count, and min number
      min_number = getMax(list);
      for(i = 0; i < length; i++){
        if(list[i] > 0){
          count += 1;
          if(list[i] < min_number){
            min_number = list[i];
          }
        }
      }
      
      if(count > 0){
        // transfer
        min_number = Math.min(min_number, number / count);
        if(min_number == 0){
          break;
        }
        for(i = 0; i < length; i++){
          if(list[i] > 0){
            list[i] -= min_number;
            final_list[i] += min_number;
            number -= min_number;
          }
        }
      }
    }
    while(number >= count && count > 0);
    
    if(count == 0){
      return final_list;
    }

    // transfer remaining
    for(i = 0; i < length && number > 0; i++){
      if(list[i] > 0){
        final_list[i] += 1;
        number -= 1;
      }
    }
    return final_list;
  }

  /** If you want to extract a number as evenly as possible from a random list of numbers, this function produces
   *  another list of numbers which you can use to extract from the original list. And it uses a Round-Robin
   *  method, so bigger numbers will end up towards the beginning. It's kind of hard to explain, so I'll give
   *  you an example: If you're trying to extract 30 from the list [8, 15, 4, 1, 15], this function will produce
   *  the list [8, 9, 4, 1, 8], which, when extracted from the original list would produce [0, 6, 0, 0, 7].
   * @param number
   * @param list
   */
  public static final long[] divide_evenly(@Nonnegative long number, long[] list){
    final int length = list.length;
    final long[] final_list = new long[length]; // already initialized to all 0

    if(length == 0){
      ADDSynthCore.log.fatal(new IllegalArgumentException(MathUtility.class.getSimpleName()+".divide_evenly() cannot use an empty integer array."));
      return final_list;
    }
    if(number < 0){
      ADDSynthCore.log.fatal(new IllegalArgumentException("Cannot use "+MathUtility.class.getSimpleName()+".divide_evenly() to divide a negative number!"));
      return final_list;
    }
    if(length == 1){
      return new long[]{Math.min(number, Math.max(list[0], 0))};
    }

    int i;
    int count;
    long min_number;

    do{
      // reset
      count = 0;
      
      // get first index, count, and min number
      min_number = getMax(list);
      for(i = 0; i < length; i++){
        if(list[i] > 0){
          count += 1;
          if(list[i] < min_number){
            min_number = list[i];
          }
        }
      }
      
      if(count > 0){
        // transfer
        min_number = Math.min(min_number, number / count);
        if(min_number == 0){
          break;
        }
        for(i = 0; i < length; i++){
          if(list[i] > 0){
            list[i] -= min_number;
            final_list[i] += min_number;
            number -= min_number;
          }
        }
      }
    }
    while(number >= count && count > 0);
    
    if(count == 0){
      return final_list;
    }

    // transfer remaining
    for(i = 0; i < length && number > 0; i++){
      if(list[i] > 0){
        final_list[i] += 1;
        number -= 1;
      }
    }
    return final_list;
  }

  public static final double get_distance(double x1, double y1, double z1, double x2, double  y2, double z2){
    return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1)));
  }

  /** Returns a random integer between <code>minimum</code> (inclusive) and <code>maximum</code>
   *  (inclusive). This function won't error if you get the minimum and maximum mixed up,
   *  or if they're the same value.
   * @param minimum
   * @param maximum
   */
  public static final int RandomRange(final int minimum, final int maximum){
    final int min = Math.min(minimum, maximum);
    final int max = Math.max(minimum, maximum);
    final int diff = max - min;
    if(diff == 0){ return min; }
    final Random random = new Random();
    return min + random.nextInt(diff + 1);
  }

  public static final int choose(final Random random, final int ... list){
    if(list.length == 0){
      throw new IllegalArgumentException(MathUtility.class.getSimpleName()+".choose() requires a list of at least 1 integer!");
    }
    return list[random.nextInt(list.length)];
  }

  public static final <T> T choose(final Random random, final T[] list){
    if(list.length == 0){
      throw new IllegalArgumentException(MathUtility.class.getSimpleName()+".choose() requires a list of 1 or more objects!");
    }
    return list[random.nextInt(list.length)];
  }

  public static final double round(final double input, final int decimals){
    if(decimals == 0){
      return Math.round(input);
    }
    final double adjustment = Math.pow(10, decimals);
    return Math.round(input * adjustment) / adjustment;
  }

  /** Used in {@link Scrollbar}s. Generates an array of stop positions for the scrollbar. */
  public static final int[] getPositions(int max_number, int positions){
    return getPositions(0, max_number, positions);
  }
  
  /** Used in {@link Scrollbar}s. Generates an array of stop positions for the scrollbar. */
  public static final int[] getPositions(int min_number, int max_number, int positions){
    // handle a position of 1 to prevent divide by zero
    if(positions <= 1){
      return new int[] {min_number};
    }
    int[] recorded_positions = new int[positions];
    double step = ((double)(max_number - min_number)) / (positions-1);
    int i;
    for(i = 0; i < positions; i++){
      recorded_positions[i] = (int)Math.round(((double)(min_number)) + (step*i));
    }
    return recorded_positions;
  }

  /** Used in {@link Scrollbar}s. Uses the scrollbar's current position to determine the index amongst the
   *  stop positions and writes all visible lines according to the index position.
   * @param position
   * @param values
   */
  public static final int getPositionIndex(int position, int[] values){
    if(position <= values[0]){
      return 0;
    }
    if(position >= values[values.length-1]){
      return values.length-1;
    }
    int i = 1;
    while(position >= values[i]){
      i += 1;
    }
    final int left = values[i-1];
    final int right = values[i];
    return getExtreme(left, position, right) == left ? i-1 : i;
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
