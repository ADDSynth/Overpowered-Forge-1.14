package addsynth.core.inventory.container.slots;

import addsynth.core.tiles.TileMachine;
import net.minecraft.item.Item;

public final class InputSlot extends RestrictedSlot {

  private static final int default_stack_size = 64;
  private final int max_stack_size;

  public InputSlot(TileMachine tile, int index, int xPosition, int yPosition) {
    this(tile, index, null, default_stack_size, xPosition, yPosition);
  }

  public InputSlot(TileMachine tile, int index, Item[] valid_items, int xPosition, int yPosition) {
    this(tile, index, valid_items, default_stack_size, xPosition, yPosition);
  }

  public InputSlot(TileMachine tile, int index, int max_stack_size, int xPosition, int yPosition){
    this(tile, index, null, max_stack_size, xPosition, yPosition);
  }

  public InputSlot(TileMachine tile, int index, Item[] valid_items, int max_stack_size, int xPosition, int yPosition) {
    super(tile.getInputInventory(), index, valid_items, xPosition, yPosition);
    this.max_stack_size = max_stack_size;
  }

  @Override
  public int getSlotStackLimit() {
    return max_stack_size;
  }

}
