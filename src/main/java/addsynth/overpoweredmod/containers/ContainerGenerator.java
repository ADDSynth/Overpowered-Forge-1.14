package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.tiles.machines.energy.TileEnergyGenerator;
import net.minecraft.inventory.IInventory;

public final class ContainerGenerator extends BaseContainer<TileEnergyGenerator> {

  public ContainerGenerator(final IInventory player_inventory, final TileEnergyGenerator tile){
    super(tile);
    make_player_inventory(player_inventory,8,94);
    addSlotToContainer(new InputSlot(tile,0,TileEnergyGenerator.input_filter,53,20));
  }

}
