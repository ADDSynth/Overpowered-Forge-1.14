package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileCompressor;
import net.minecraft.inventory.IInventory;

public final class ContainerCompressor extends BaseContainer<TileCompressor> {

  public ContainerCompressor(final IInventory player_inventory, final TileCompressor tile){
    super(tile);
    make_player_inventory(player_inventory,8,100);
    addSlotToContainer(new InputSlot(tile,0,38,41));
    addSlotToContainer(new InputSlot(tile,1,56,41));
    addSlotToContainer(new OutputSlot(tile,0,111,41));
  }

}
