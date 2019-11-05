package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.inventory.IInventory;

public final class ContainerPortalFrame extends BaseContainer<TilePortalFrame> {

  public ContainerPortalFrame(final IInventory player_inventory, final TilePortalFrame tile){
    super(tile);
    make_player_inventory(player_inventory);
    addSlotToContainer(new RestrictedSlot(tile.getInputInventory(), 0, TilePortalFrame.input_filter, 80, 37));
  }

}
