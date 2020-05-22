package addsynth.core.util;

import java.lang.Math;
import java.util.Collection;
import java.util.Random;
import javax.annotation.Nonnegative;
import net.minecraft.util.math.BlockPos;

public final class MathUtility {

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

  /**
   * In retrospect, BlockPos and Vec3i have their own getDistance() method, but I still think
   * mine runs faster.
   * @param position_1
   * @param position_2
   */
  public static final double get_distance(BlockPos position_1, BlockPos position_2){
    final int x_value = (position_2.getX() - position_1.getX()) * (position_2.getX() - position_1.getX());
    final int y_value = (position_2.getY() - position_1.getY()) * (position_2.getY() - position_1.getY());
    final int z_value = (position_2.getZ() - position_1.getZ()) * (position_2.getZ() - position_1.getZ());
    return Math.sqrt((double)(x_value + y_value + z_value));
  }

  /** Returns the distance between a block and an entity.
   * 
   * @param block_position
   * @param x
   * @param y
   * @param z
   */
  public static final double get_distance(BlockPos block_position, double x, double y, double z){
    return get_distance(block_position.getX() + 0.5, block_position.getY() + 0.5, block_position.getZ() + 0.5, x, y, z);
  }

  public static final double get_distance(double x1, double y1, double z1, double x2, double  y2, double z2){
    return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1)));
  }

  /**
   * This method is very similar to is_inside_sphere() except this runs much faster because we
   * just need to check the distance of each component, instead of the ACTUAL distance together.
   */
  public static final boolean is_inside_cube(final BlockPos center, final int radius, final BlockPos position){
    final int distance_x = Math.abs( center.getX() - position.getX() );
    final int distance_y = Math.abs( center.getY() - position.getY() );
    final int distance_z = Math.abs( center.getZ() - position.getZ() );
    return distance_x <= radius && distance_y <= radius && distance_z <= radius;
  }

  /**
   * 
   * @param center
   * @param radius
   * @param position
   */
  public static final boolean is_inside_sphere(final BlockPos center, final int radius, final BlockPos position){
    return (int)Math.round(get_distance(center,position)) <= radius;
  }

  public static final boolean is_inside_sphere(final BlockPos center, final float radius, final BlockPos position){
    return get_distance(center,position) <= radius;
  }

  public static final BlockPos getCenter(final Collection<BlockPos> list){
    if(list.size() > 0){
      final double[] center = getExactCenter(list);
      return new BlockPos( Math.floor(center[0]), Math.floor(center[1]), Math.floor(center[2]));
    }
    /*
    else{
      throw new IllegalArgumentException("Function getCenter(List<BlockPos>) requires a list that has at least one BlockPos element.");
    }
    */
    return null;
  }

  /**
   * Returns a double type array with 3 indexes (x, y, and z) indicating the center position of the list of Block Positions.
   * @param list
   */
  public static final double[] getExactCenter(final Collection<BlockPos> list){
    final BlockPos[] pos = get_min_max_positions(list);
    return new double[] { (pos[0].getX() + pos[1].getX()) / 2, (pos[0].getY() + pos[1].getY()) / 2, (pos[0].getZ() + pos[1].getZ()) / 2 };
  }

  public static final int RandomRange(final int minimum, final int maximum){ // TODO: random.nextInt() input must be above 0, add code that can handle negative values.
    final Random random = new Random();
    if(minimum < maximum){ return minimum + random.nextInt(maximum - minimum + 1); }
    if(minimum > maximum){ return maximum + random.nextInt(minimum - maximum + 1); }
    return minimum;
  }

  public static final int get_chunk_index(final int coordinate){
    return (int)Math.floor(coordinate / 16); // >> 4 shifting bits is computationally faster.
  }

  public static final int get_chunk_index(final double coordinate){
    return (int)Math.floor(coordinate / 16);
  }

  public static final int get_coordinate_in_chunk(final int coordinate){
    return coordinate % 16; // & 15 would be computationally faster.
  }
  
  public static final int get_coordinate_in_chunk(final float coordinate){
    return (int)Math.floor(coordinate) % 16;
  }
  
  public static final int get_coordinate_in_chunk(final double coordinate){
    return (int)Math.floor(coordinate) % 16;
  }

  /** Returns the minimum position in the list of BlockPositions.
   *  <p>This does not return a BlockPos in the list! It returns the lowest possible position!<br />
   *  For instance, in the following situation:<br />
   *  <code><pre>
   *    X  
   *    X  
   *     XX</pre></code>
   *  This would return the bottom-left space!
   */
  public static final BlockPos get_minimum_position(final Collection<BlockPos> list){
    return get_minimum_position(list.toArray(new BlockPos[list.size()]));
  }

  /** Returns the minimum position in the list of BlockPositions.
   *  <p>This does not return a BlockPos in the list! It returns the lowest possible position!<br />
   *  For instance, in the following situation:<br />
   *  <code><pre>
   *    X  
   *    X  
   *     XX</pre></code>
   *  This would return the bottom-left space!
   */
  public static final BlockPos get_minimum_position(final BlockPos[] list){
    if(list.length > 0){
      int i;
      int min_x = list[0].getX();
      int min_y = list[0].getY();
      int min_z = list[0].getZ();
      for(i = 1; i < list.length; i++){
        if(list[i].getX() < min_x){ min_x = list[i].getX(); }
        if(list[i].getY() < min_y){ min_y = list[i].getY(); }
        if(list[i].getZ() < min_z){ min_z = list[i].getZ(); }
      }
      return new BlockPos(min_x, min_y, min_z);
    }
    throw new IllegalArgumentException("Function "+MathUtility.class.getName()+".get_minimum_position() requires a list that has at least 1 BlockPosition.");
  }

  /** Returns an array of 2 Block Positions, the first being the minimum, the second being the maximum.
   *  <p>This does not return a BlockPos in the list! It returns the lowest and highest possible position!<br />
   *  For instance, in the following situation:<br />
   *  <code><pre>
   *    X  
   *    X  
   *     XX</pre></code>
   *  This would return the bottom-left space!
   */
  public static final BlockPos[] get_min_max_positions(final Collection<BlockPos> list){
    return get_min_max_positions(list.toArray(new BlockPos[list.size()]));
  }

  /** <p>Returns an array of 2 Block Positions, the first being the minimum, the second being the maximum.
   *  <p>This does not return a BlockPos in the list! It returns the lowest and highest possible position!<br />
   *  For instance, in the following situation:<br />
   *  <code><pre>
   *    X  
   *    X  
   *     XX</pre></code>
   *  This would return the bottom-left space!
   */
  public static final BlockPos[] get_min_max_positions(final BlockPos[] list){
    if(list.length > 0){
      int i;
      int min_x = list[0].getX();
      int min_y = list[0].getY();
      int min_z = list[0].getZ();
      int max_x = list[0].getX();
      int max_y = list[0].getY();
      int max_z = list[0].getZ();
      for(i = 1; i < list.length; i++){
        if(list[i].getX() < min_x){ min_x = list[i].getX(); }
        if(list[i].getX() > max_x){ max_x = list[i].getX(); }
        if(list[i].getY() < min_y){ min_y = list[i].getY(); }
        if(list[i].getY() > max_y){ max_y = list[i].getY(); }
        if(list[i].getZ() < min_z){ min_z = list[i].getZ(); }
        if(list[i].getZ() > max_z){ max_z = list[i].getZ(); }
      }
      return new BlockPos[] {new BlockPos(min_x, min_y, min_z), new BlockPos(max_x, max_y, max_z)};
    }
    throw new IllegalArgumentException("Function "+MathUtility.class.getName()+".get_min_max_position() requires a list that has at least one BlockPos element.");
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
