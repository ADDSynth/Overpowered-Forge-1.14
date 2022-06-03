package addsynth.core.game.inventory;

/** TileEntities that use an {@link OutputInventory} must implement this. */
public interface IOutputInventory extends IInventoryUser {

  public OutputInventory getOutputInventory();

}
