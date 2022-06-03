package addsynth.core.game.inventory.machine;

import addsynth.core.game.inventory.CommonInventory;

public interface IMachineInventory {

  public CommonInventory getWorkingInventory();

  /** Counts the items in the inventory and returns the lowest number.
   *  This assumes a recipe requires only 1 of each item in the inventory.
   *  Call the <code>getJobs()</code> function from the {@link MachineInventory}.
   */
  public int getJobs();

}
