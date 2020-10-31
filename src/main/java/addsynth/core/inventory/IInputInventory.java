package addsynth.core.inventory;

/** TileEntities that use an {@link InputInventory} must implement this. */
public interface IInputInventory extends IInventoryUser {

  public InputInventory getInputInventory();

}
