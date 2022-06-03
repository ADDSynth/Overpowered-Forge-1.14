package addsynth.core.game.inventory;

public interface IInventoryResponder {

  /** Anything that has an inventory MUST save whenever that inventory changes. */
  public void onInventoryChanged();

}
