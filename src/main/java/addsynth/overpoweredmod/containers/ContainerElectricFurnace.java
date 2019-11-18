package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.inventory.IInventory;

public final class ContainerElectricFurnace extends BaseContainer<TileElectricFurnace> {

  public ContainerElectricFurnace(final IInventory player_inventory, final TileElectricFurnace tile){
    super(tile);
    make_player_inventory(player_inventory,8,90);
    addSlot(new InputSlot(tile, 0, TileElectricFurnace.furnace_input,40,40));
    addSlot(new OutputSlot(tile,0,95,40));
  }

}
