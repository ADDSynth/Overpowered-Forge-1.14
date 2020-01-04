package addsynth.energy.gameplay.compressor;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerCompressor extends BaseContainer<TileCompressor> {

  public ContainerCompressor(final int id, final PlayerInventory player_inventory){
    super(Containers.COMPRESSOR, id, player_inventory);
  }

  public ContainerCompressor(final int id, final PlayerInventory player_inventory, final TileCompressor tile){
    super(Containers.COMPRESSOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerCompressor(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.COMPRESSOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,100);
    addSlot(new InputSlot(tile,0,38,41));
    addSlot(new InputSlot(tile,1,56,41));
    addSlot(new OutputSlot(tile,0,111,41));
  }

}