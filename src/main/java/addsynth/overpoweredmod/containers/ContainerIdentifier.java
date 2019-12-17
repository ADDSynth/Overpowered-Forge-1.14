package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import addsynth.overpoweredmod.tiles.machines.automatic.TileIdentifier;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerIdentifier extends BaseContainer<TileIdentifier> {

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory){
    super(Containers.IDENTIFIER, id, player_inventory);
  }

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory, final TileIdentifier tile){
    super(Containers.IDENTIFIER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.IDENTIFIER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,85);
    addSlot(new InputSlot(tile,0,TileIdentifier.input_filter, 1,80,40));
  }

}
