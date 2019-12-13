package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.energy.gameplay.tiles.TileEnergyStorage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerEnergyStorage extends BaseContainer<TileEnergyStorage> {

  public ContainerEnergyStorage(final int id, final PlayerInventory player_inventory){
    super(Containers.ENERGY_STORAGE_CONTAINER, id, player_inventory);
  }

  public ContainerEnergyStorage(final int id, final PlayerInventory player_inventory, final TileEnergyStorage tile){
    super(Containers.ENERGY_STORAGE_CONTAINER, id, player_inventory, tile);
  }

  public ContainerEnergyStorage(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.ENERGY_STORAGE_CONTAINER, id, player_inventory, data);
  }

}
