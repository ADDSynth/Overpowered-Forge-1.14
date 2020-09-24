package addsynth.core.util.math;

import java.lang.Math;
import java.util.Random;
import javax.annotation.Nonnegative;
import net.minecraft.util.math.MathHelper;

public final class MathUtility {

  public static final int getMin(final int ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMin() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMin() requires at least one integer as input."); }
    int min = input[0];
    if(input.length > 1){
      for(final int value : input){
        if(value < min){
          min = value;
        }
      }
    }
    return min;
  }

  public static final int getMax(final int ... input){
    if(input == null){     throw new NullPointerException(    MathUtility.class.getName()+".getMax() input was null!"); }
    if(input.length == 0){ throw new IllegalArgumentException(MathUtility.class.getName()+".getMax() requires at least one integer as input."); }
    int max = input[0];
    if(input.length > 1){
      for(final int value : input){
        if(value > max){
          max = value;
        }
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
   * @param input
   */
  public static final int[] divide_evenly(@Nonnegative int number, final int[] input){
    if(input.length == 0){
      throw new IllegalArgumentException("MathUtility.divide_evenly() cannot use an empty integer array.");
    }
    if(number < 0){
      throw new IllegalArgumentException("Cannot use MathUtility.divide_evenly() to divide a negative number!");
    }

    final int[] list = input.clone();
    int count = 0;
    int min_number = list[0];
    for(final int i : list){
      if(i < 0){
        throw new IllegalArgumentException("Numbers in the integer input list for MathUtility.divide_evenly() cannot be a negative!");
      }
      count += i;
      if(i < min_number){
        min_number = i;
      }
    }
    if(count <= number){
      return list;
    }

    int i;
    count = list.length;
    final int[] final_list = new int[count];
    min_number = Math.min(min_number, number / count);
    if(min_number > 0){
      number -= (count * min_number);
      for(i = 0; i < count; i++){
        final_list[i] = min_number;
        list[i] -= min_number;
      }
    }
    while(number > 0){
      for(i = 0; i < count && number > 0; i++){
        if(list[i] > 0){
          list[i] -= 1;
          final_list[i] += 1;
          number -= 1;
        }
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
   * @param input
   */
  public static final long[] divide_evenly(@Nonnegative long number, final long[] input){
    if(input.length == 0){
      throw new IllegalArgumentException("MathUtility.divide_evenly() cannot use an empty integer array.");
    }
    if(number < 0){
      throw new IllegalArgumentException("Cannot use MathUtility.divide_evenly() to divide a negative number!");
    }

    final long[] list = input.clone();
    long count = 0;
    long min_number = list[0];
    for(final long i : list){
      if(i < 0){
        throw new IllegalArgumentException("Numbers in the integer input list for MathUtility.divide_evenly() cannot be a negative!");
      }
      count += i;
      if(i < min_number){
        min_number = i;
      }
    }
    if(count <= number){
      return list;
    }

    int i;
    count = list.length;
    final long[] final_list = new long[list.length];
    min_number = Math.min(min_number, number / count);
    if(min_number > 0){
      number -= (count * min_number);
      for(i = 0; i < count; i++){
        final_list[i] = min_number;
        list[i] -= min_number;
      }
    }
    while(number > 0){
      for(i = 0; i < count && number > 0; i++){
        if(list[i] > 0){
          list[i] -= 1;
          final_list[i] += 1;
          number -= 1;
        }
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

}
