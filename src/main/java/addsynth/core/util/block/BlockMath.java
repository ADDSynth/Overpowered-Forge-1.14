package addsynth.core.util.block;

import java.util.Collection;
import addsynth.core.util.math.MathUtility;
import net.minecraft.util.math.BlockPos;

public final class BlockMath {

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
    return MathUtility.get_distance(block_position.getX() + 0.5, block_position.getY() + 0.5, block_position.getZ() + 0.5, x, y, z);
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
    // ADDSynthCore.log.error(new IllegalArgumentException("Function getCenter(List<BlockPos>) requires a list that has at least one BlockPos element."));
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

  /** Returns the chunk id. */
  public static final int get_chunk_index(final int coordinate){
    return coordinate >> 4;
  }

  /** Returns the chunk id. */
  public static final int get_chunk_index(final double coordinate){
    return (int)Math.floor(coordinate) >> 4;
  }

  /** Returns the coordinate in the chunk, 0-15. */
  public static final int get_coordinate_in_chunk(final int coordinate){
    return coordinate & 15;
  }
  
  /** Returns the coordinate in the chunk, 0-15. */
  public static final int get_coordinate_in_chunk(final float coordinate){
    return (int)Math.floor(coordinate) & 15;
  }
  
  /** Returns the coordinate in the chunk, 0-15. */
  public static final int get_coordinate_in_chunk(final double coordinate){
    return (int)Math.floor(coordinate) & 15;
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
    throw new IllegalArgumentException("Function "+BlockMath.class.getName()+".get_minimum_position() requires a list that has at least 1 BlockPosition.");
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

  public static final boolean is_full_rectangle(final Collection<BlockPos> list){
    return is_full_rectangle(list.toArray(new BlockPos[list.size()]));
  }
  
  public static final boolean is_full_rectangle(final BlockPos[] list){
    boolean rectangle = true;
    final BlockPos[] min_max = get_min_max_positions(list);
    if(min_max == null){
      return false;
    }
    int x;
    int y;
    int z;
    final int min_x = min_max[0].getX();
    final int min_y = min_max[0].getY();
    final int min_z = min_max[0].getZ();
    final int max_x = min_max[1].getX();
    final int max_y = min_max[1].getY();
    final int max_z = min_max[1].getZ();
    final int length_x = max_x - min_x;
    final int length_y = max_y - min_y;
    final int length_z = max_z - min_z;
    final boolean[][][] check = new boolean[length_x][length_y][length_z];
    for(x = 0; x < list.length; x++){
      check[list[x].getX() - min_x][list[x].getY() - min_y][list[x].getZ() - min_z] = true;
    }
    for(z = 0; z < length_z && rectangle; z++){
      for(y = 0; y < length_y && rectangle; y++){
        for(x = 0; x < length_x && rectangle; x++){
          if(check[x][y][z] == false){
            rectangle = false;
          }
        }
      }
    }
    return rectangle;
  }

}
