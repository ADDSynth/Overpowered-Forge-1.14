package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileInverter;
import net.minecraft.inventory.IInventory;

public final class ContainerInverter extends BaseContainer<TileInverter> {

  public ContainerInverter(final IInventory player_inventory, final TileInverter tile){
    super(tile);
    make_player_inventory(player_inventory,8,105);
    addSlotToContainer(new InputSlot(tile,0,TileInverter.input_filter,50,44));
    addSlotToContainer(new OutputSlot(tile,0,105,44));
  }

}
