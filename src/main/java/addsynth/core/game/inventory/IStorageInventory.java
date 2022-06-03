package addsynth.core.game.inventory;

/** TileEntities that have an inventory that is just used for common storage
 *  should implement this. */
public interface IStorageInventory extends IInventoryUser {

  public CommonInventory getInventory();

}
