package addsynth.core.inventory;

import addsynth.core.tiles.TileMachine;

/**
 * <p>Inventories that derive from this class must specify a {@link TileMachine} that responds
 * to inventory changes.
 * <p>{@link InputInventory} and {@link OutputInventory} derive from this class.
 * @author ADDSynth
 */
public abstract class ReactiveInventory extends CommonInventory {

  private final TileMachine responder;

  public ReactiveInventory(final TileMachine responder, final int number_of_slots){
    super(number_of_slots);
    this.responder = responder;
  }

  /** I've checked. This calls onInventoryChanged() in {@link TileMachine} and is only used
   *  to call test_condition() in {@link addsynth.energy.tiles.machines.PassiveMachine}, which
   *  must check both the Input and Output inventories, so passing the slot index is useless.
   *  We can pass an Inventory_Type enum value if we really need to.
   */
  @Override
  protected final void onContentsChanged(final int slot){
    if(responder != null){
      responder.onInventoryChanged();
    }
  }

}
