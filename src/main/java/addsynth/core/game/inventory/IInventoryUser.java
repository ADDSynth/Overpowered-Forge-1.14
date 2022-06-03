package addsynth.core.game.inventory;

/** It is not recommended to use this, except to check for ANY kind of inventory.
 *  Only implement this in TileEntities if you wish to keep the inventory private.
 */
public interface IInventoryUser extends IInventoryResponder {

  /** Blocks that have an inventory MUST drop the inventory contents
   *  when the block is destroyed. */
  public void drop_inventory();

}
