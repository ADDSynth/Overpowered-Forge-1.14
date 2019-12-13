package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileInverter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerInverter extends BaseContainer<TileInverter> {

  public ContainerInverter(final int id, final PlayerInventory player_inventory){
    super(Containers.INVERTER, id, player_inventory);
  }

  public ContainerInverter(final int id, final PlayerInventory player_inventory, final TileInverter tile){
    super(Containers.INVERTER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerInverter(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.INVERTER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(tile,0,TileInverter.input_filter,50,44));
    addSlot(new OutputSlot(tile,0,105,44));
  }

}
