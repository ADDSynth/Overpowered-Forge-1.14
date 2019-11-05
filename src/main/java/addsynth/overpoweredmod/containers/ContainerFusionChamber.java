package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.inventory.IInventory;

public final class ContainerFusionChamber extends BaseContainer<TileFusionChamber> {

  public ContainerFusionChamber(final IInventory player_inventory, final TileFusionChamber tile){
    super(tile);
    make_player_inventory(player_inventory);
    addSlotToContainer(new RestrictedSlot(tile.getInputInventory(), 0, TileFusionChamber.input_filter, 80, 37));
  }

}
