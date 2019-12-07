package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.automatic.TileIdentifier;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerIdentifier extends BaseContainer {

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory){
    super(Containers.IDENTIFIER, id, player_inventory, Machines.identifier);
    make_player_inventory(player_inventory,8,85);
    addSlot(new InputSlot(null,0,TileIdentifier.input_filter, 1,80,40));
  }

}
