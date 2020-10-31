package addsynth.energy.gameplay.energy_storage;

import addsynth.core.container.AbstractContainer;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerEnergyStorage extends AbstractContainer<TileEnergyStorage> {

  public ContainerEnergyStorage(final int id, final PlayerInventory player_inventory, final TileEnergyStorage tile){
    super(Containers.ENERGY_STORAGE_CONTAINER, id, player_inventory, tile);
  }

  public ContainerEnergyStorage(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.ENERGY_STORAGE_CONTAINER, id, player_inventory, data);
  }

}
