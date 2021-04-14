package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerIdentifier extends TileEntityContainer<TileIdentifier> {

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory, final TileIdentifier tile){
    super(Containers.IDENTIFIER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerIdentifier(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.IDENTIFIER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,87);
    addSlot(new InputSlot( tile, 0, TileIdentifier.input_filter, 1, 28, 42));
    addSlot(new OutputSlot(tile, 0, 124, 42));
  }

}
