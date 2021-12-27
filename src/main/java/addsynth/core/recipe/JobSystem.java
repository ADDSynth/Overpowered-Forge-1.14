package addsynth.core.recipe;

import java.util.ArrayList;
import java.util.function.Function;
import addsynth.core.items.ItemUtil;
import net.minecraft.item.ItemStack;

public final class JobSystem {

  public static final WorkJob[] getJobs(final ItemStack[] inventory, final Function<ItemStack[], ItemStack> result_provider){
    final ArrayList<WorkJob> jobs = new ArrayList<>(64); // isn't being used right now, but ideally, this should stay in the order we add them, so use some kind of Queue.
    boolean exists;
    ItemStack result;
    do{
      result = result_provider.apply(inventory);
      exists = ItemUtil.itemStackExists(result);
      if(exists){
        jobs.add(WorkJob.subtractFromInventory(inventory, result));
      }
    }
    while(exists);
    return jobs.toArray(new WorkJob[jobs.size()]);
  }

  /** A simpler form. Assumes recipes will only use 1 of each item in the inventory,
   *  so the maximum number of recipes we can craft equals the item of the least amount.
   * @param inventory
   */
  public static final WorkJob[] getJobs(final ItemStack[] inventory){
    final int count = getMaxNumberOfJobs(inventory, false);
    final WorkJob[] jobs = new WorkJob[count];
    int i;
    for(i = 0; i < count; i++){
      jobs[i] = WorkJob.subtractFromInventory(inventory, ItemStack.EMPTY);
    }
    return jobs;
  }

  /** A simpler form. Assumes recipes will only use 1 of each item in the inventory,
   *  so the maximum number of recipes we can craft equals the item of the least amount.
   * @param inventory
   */
  public static final int getMaxNumberOfJobs(final ItemStack[] inventory, final boolean requires_all_slots){
    int min = Integer.MAX_VALUE;
    for(final ItemStack stack : inventory){
      if(ItemUtil.itemStackExists(stack)){
        if(stack.getCount() < min){
          min = stack.getCount();
        }
      }
      else{
        if(requires_all_slots){
          return 0;
        }
      }
    }
    if(min == Integer.MAX_VALUE){
      return 0;
    }
    return min;
  }

}
