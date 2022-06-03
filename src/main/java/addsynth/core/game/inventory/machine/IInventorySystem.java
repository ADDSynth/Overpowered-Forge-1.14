package addsynth.core.game.inventory.machine;

import addsynth.core.game.inventory.IInventoryResponder;

/** <p>Primarily used in {@link MachineInventory}.
 *  <p>In the {@link IInventoryResponder#onInventoryChanged() onInventoryChanged()} function,
 *     you must set the <code>changed</code> variable to <code>true</code>, and you must
 *     also call the <code>recheck()</code> function.
 *  <p>Have your TileEntities call the <code>tick()</code> method to check if any inventories changed.
 * @author ADDSynth
 */
// TEST: Recheck in 2027, if anything else actually uses this, otherwise just merge into MachineInventory.
public interface IInventorySystem {

  /** Returns whever this machine can do work. */
  public boolean can_work();

  /** Use this function to configure the resulting ItemStack based on the items currently
   *  in the Input Inventory. This must be called whenever the inventory changes, as well
   *  as when the inventory is loaded! This must set the <code>result</code> and
   *  <code>can_work</code> variables.
   */
  public void recheck();

  public void begin_work();

  public boolean tick();

}
