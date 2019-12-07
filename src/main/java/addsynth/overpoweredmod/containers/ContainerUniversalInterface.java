package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerUniversalInterface extends BaseContainer {

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory, Machines.universal_energy_machine);
    make_player_inventory(player_inventory,8,90);
    addSlot(new InputSlot(null, 0, TileElectricFurnace.furnace_input,40,40));
    addSlot(new OutputSlot(null,0,95,40));
  }

}
