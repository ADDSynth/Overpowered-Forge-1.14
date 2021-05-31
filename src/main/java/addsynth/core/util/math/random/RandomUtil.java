package addsynth.core.util.math.random;

import java.util.Random;

public final class RandomUtil {

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
      throw new IllegalArgumentException(RandomUtil.class.getSimpleName()+".choose() requires a list of at least 1 integer!");
    }
    return list[random.nextInt(list.length)];
  }

  public static final <T> T choose(final Random random, final T[] list){
    if(list.length == 0){
      throw new IllegalArgumentException(RandomUtil.class.getSimpleName()+".choose() requires a list of 1 or more objects!");
    }
    return list[random.nextInt(list.length)];
  }

}
