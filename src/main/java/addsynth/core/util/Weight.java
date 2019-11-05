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
  
  public static final int getWeightedValue(final int highest_value){
    if(highest_value <= 0){
      throw new IllegalArgumentException("only values > 0 are allowed.");
    }
    int i;
    int j = highest_value;
    int total_buckets = 0;
    while(j > 0){
      total_buckets += j;
      j -= 1;
    }
    final int[] bucket = new int[total_buckets];
    i = 0;
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
