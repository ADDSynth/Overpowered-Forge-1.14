package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.automatic.TileInverter;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerInverter extends BaseContainer {

  public ContainerInverter(final int id, final PlayerInventory player_inventory){
    super(Containers.INVERTER, id, player_inventory, Machines.inverter);
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(null,0,TileInverter.input_filter,50,44));
    addSlot(new OutputSlot(null,0,105,44));
  }

}
