package addsynth.core.util;

import java.util.Random;

public final class Weight {

  private static final int[][] common_weight_distributions = new int[][] {
    {1},
    {1,1,2},
    {1,1,1,2,2,3},
    {1,1,1,1,2,2,2,3,3,4},
    {1,1,1,1,1,2,2,2,2,3,3,3,4,4,5},
    {1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,4,4,4,5,5,6}
  };
  // 2
  // 1/3   2/3
  
  // 3
  // 1/10  3/10  6/10

  // 4
  // 1/20  3/20  6/20  10/20
  
  // 5
  // 1/35  3/35  6/35  10/35  15/35  
  
  /*
  public static final int getWeightedValue(final int highest_value){
    if(highest_value <= 0){
      throw new IllegalArgumentException("only values > 0 are allowed.");
    }
    if(highest_value <= 6){
      
    }
    else{
      return -1;
    }
  }
  */
  
  /** This creates a linearly distributed bucket list, so that you have a higher chance of
   *  getting a low value rather than a high value. For example, say you pass the number 5
   *  as the argument, this function will then create the bucket list:<br />
   *  <code>[0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4]</code><br />
   *  The function will then randomly pick one of these numbers and return it. This means
   *  You have a 33% chance of getting 0, a 27% chance of getting 1, a 20% of getting 2,
   *  a 13% chance of getting 3, and a 7% chance of getting 4.
   * @param highest_value
   */
  public static final int getWeightedValue(final int highest_value){
    if(highest_value <= 0){
      throw new IllegalArgumentException("only values > 0 are allowed.");
    }
    int j = highest_value;
    int total_buckets = 0;
    while(j > 0){
      total_buckets += j;
      j -= 1;
    }
    final int[] bucket = new int[total_buckets];
    int i = 0;
    j = highest_value;
    int k = 0;
    int z;
    for(z = 0; z < total_buckets; z++){
      bucket[z] = k;
      i++;
      if(i >= j){
        i = 0;
        j--;
        k++;
      }
    }
    return bucket[(new Random()).nextInt(total_buckets)] + 1;
  }
  
}
