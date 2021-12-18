package addsynth.core.util.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
   *  Adds 0.5 to the BlockPos to set the point in the center of the block.
   * @param block_position
   * @param x
   * @param y
   * @param z
   */
  public static final double get_distance(BlockPos block_position, double x, double y, double z){
    return MathUtility.get_distance(block_position.getX() + 0.5, block_position.getY() + 0.5, block_position.getZ() + 0.5, x, y, z);
  }

  /** Gets the horizontal distance between 2 Block Positions, ignoring their y levels. */
  public static final double getHorizontalDistance(final BlockPos position_1, final BlockPos position_2){
    final int x_value = (position_2.getX() - position_1.getX()) * (position_2.getX() - position_1.getX());
    final int z_value = (position_2.getZ() - position_1.getZ()) * (position_2.getZ() - position_1.getZ());
    return Math.sqrt((double)(x_value + z_value));
  }

  /** Gets horizontal distance between a block and an entity.
   *  Adds 0.5 to the BlockPos to set the point in the center of the block.
   * @param block_position
   * @param x
   * @param z
   */
  public static final double getHorizontalDistance(BlockPos block_position, double x, double z){
    return MathUtility.get_distance(block_position.getX() + 0.5, block_position.getZ() + 0.5, x, z);
  }

  /** Returns whether an Entity's position is within the radius of the BlockPos.
   *  Adds 0.5 to the BlockPos to set the point in the center of the block.
   * @param position
   * @param x
   * @param y
   * @param z
   * @param distance
   */
  public static final boolean isWithin(BlockPos position, double x, double y, double z, double distance){
    return get_distance(position, x, y, z) <= distance;
  }

  /** Returns whether an Entity's horizontal position is within range of the BlockPos.
   *  Adds 0.5 to the BlockPos to set the point in the center of the block.
   * @param position
   * @param x
   * @param z
   * @param distance
   */
  public static final boolean isWithinHorizontal(BlockPos position, double x, double z, double distance){
    return getHorizontalDistance(position, x, z) <= distance;
  }

  /** @deprecated Look at what this function does. Bypass this and use the vanilla method instead. */
  @Deprecated
  public static final boolean isWithin(final BlockPos position1, final BlockPos position2, final double distance){
    return position1.withinDistance(position2, distance);
  }

  /** Returns whether the two Block Positions are horizontally in range of each other, ignoring their y levels. */
  public static final boolean isWithinHorizontal(final BlockPos position1, final BlockPos position2, final double distance){
    return getHorizontalDistance(position1, position2) <= distance;
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

  /** Returns the chunk id. Coordinates 512x512 is chunk 32x32. */
  public static final int get_chunk_index(final int coordinate){
    return coordinate >> 4;
  }

  /** Returns the chunk id. Coordinates 512x512 is chunk 32x32. */
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

  public static final int get_first_block_in_chunk(final int coordinate){
    return (coordinate >> 4) << 4;
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

  public static final class BlockDistanceComparator implements Comparator<BlockPos> {
    private final BlockPos origin;
    public BlockDistanceComparator(final BlockPos origin){
      this.origin = origin;
    }
    @Override
    public int compare(BlockPos pos1, BlockPos pos2){
      final double length1 = get_distance(origin, pos1);
      final double length2 = get_distance(origin, pos2);
      if(length1 < length2){ return -1; }
      if(length1 > length2){ return 1; }
      return 0;
    }
  }
  // OPTIMIZE: by using a class or MapEntry that pairs the BlockPos with the distance, then sort the pairs by distance.

  /*
  public static final class BlockHorizontalDistanceComparator implements Comparator<BlockPos> {
    private final BlockPos origin;
    public BlockHorizontalDistanceComparator(final BlockPos origin){
      this.origin = origin;
    }
    @Override
    public int compare(BlockPos pos1, BlockPos pos2){
      final double length1 = getHorizontalDistance(origin, pos1);
      final double length2 = getHorizontalDistance(origin, pos2);
      if(length1 < length2){ return -1; }
      if(length1 > length2){ return 1; }
      return 0;
    }
  }
  */

  /** This returns a list of Block Positions arranged in a cyllinder, given the origin and radius.
   *  The returned list is sorted so positions near the center are towards the beginning.
   * @param origin
   * @param radius
   */
  public static final Collection<BlockPos> getBlockPositionsAroundPillar(BlockPos origin, int radius){
    return getBlockPositionsAroundPillar(origin, radius, radius);
  }

  /** This returns a list of Block Positions arranged in a cyllinder, given the origin and dimensions.
   *  The returned list is sorted so positions near the center are towards the beginning.
   * @param origin
   * @param horizontal_radius
   * @param vertical_radius
   */
  public static final Collection<BlockPos> getBlockPositionsAroundPillar(BlockPos origin, int horizontal_radius, int vertical_radius){
    final ArrayList<BlockPos> list = new ArrayList<>();
    final int final_x = origin.getX() + horizontal_radius;
    final int final_y = origin.getY() + vertical_radius;
    final int final_z = origin.getZ() + horizontal_radius;
    int x;
    int y;
    int z;
    for(y = origin.getY() - vertical_radius; y < final_y; y++){
      for(x = origin.getX() - horizontal_radius; x < final_x; x++){
        for(z = origin.getZ() - horizontal_radius; z < final_z; z++){
          final BlockPos pos = new BlockPos(x, y, z);
          if(isWithinHorizontal(origin, pos, horizontal_radius)){
            list.add(pos);
          }
        }
      }
    }
    list.sort(new BlockDistanceComparator(origin));
    return list;
  }

  /** This returns a list of Block Positions in a spherical shape, given the origin and radius.
   *  The returned list is sorted so positions near the center are towards the beginning.
   * @param origin
   * @param radius
   */
  public static final Collection<BlockPos> getBlockPositionsAroundPoint(BlockPos origin, int radius){
    final ArrayList<BlockPos> list = new ArrayList<>();
    final int final_x = origin.getX() + radius;
    final int final_y = origin.getY() + radius;
    final int final_z = origin.getZ() + radius;
    int x;
    int y;
    int z;
    for(y = origin.getY() - radius; y < final_y; y++){
      for(x = origin.getX() - radius; x < final_x; x++){
        for(z = origin.getZ() - radius; z < final_z; z++){
          final BlockPos pos = new BlockPos(x, y, z);
          if(origin.withinDistance(pos, radius)){
            list.add(pos);
          }
        }
      }
    }
    list.sort(new BlockDistanceComparator(origin));
    return list;
  }

  /** This returns a list of Block Positions arranged in a custom shaped sphere, given the arguments.
   *  The returned list is sorted so positions near the center are towards the beginning.
   * @param origin
   * @param horizontal_radius
   * @param vertical_radius
   * @deprecated You'll need to fix the problem with this function before you can use it.
   *             Not correctly calculating the distance of a point to its origin given a non-uniform radius.
   */
  @Deprecated
  public static final Collection<BlockPos> getBlockPositionsAroundPoint(BlockPos origin, int horizontal_radius, int vertical_radius){
    final ArrayList<BlockPos> list = new ArrayList<>();
    final int final_x = origin.getX() + horizontal_radius;
    final int final_y = origin.getY() + vertical_radius;
    final int final_z = origin.getZ() + horizontal_radius;
    int x;
    int y;
    int z;
    for(y = origin.getY() - vertical_radius; y < final_y; y++){
      for(x = origin.getX() - horizontal_radius; x < final_x; x++){
        for(z = origin.getZ() - horizontal_radius; z < final_z; z++){
          final BlockPos pos = new BlockPos(x, y, z);
          if(origin.withinDistance(pos, (horizontal_radius + vertical_radius) / 2)){
            list.add(pos);
          }
        }
      }
    }
    list.sort(new BlockDistanceComparator(origin));
    return list;
  }
  
}
