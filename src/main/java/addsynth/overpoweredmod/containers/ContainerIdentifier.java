package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileIdentifier;
import net.minecraft.inventory.IInventory;

public final class ContainerIdentifier extends BaseContainer<TileIdentifier> {

  public ContainerIdentifier(final IInventory player_inventory, final TileIdentifier tile){
    super(tile);
    make_player_inventory(player_inventory,8,85);
    addSlotToContainer(new InputSlot(tile,0,TileIdentifier.input_filter, 1,80,40));
  }

}
