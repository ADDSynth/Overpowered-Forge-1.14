package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerLaserHousing extends BaseContainer<TileLaserHousing> {

  public ContainerLaserHousing(final int id, final PlayerInventory player_inventory){
    super(Containers.LASER_HOUSING, id, player_inventory);
  }

  public ContainerLaserHousing(final int id, final PlayerInventory player_inventory, final TileLaserHousing tile){
    super(Containers.LASER_HOUSING, id, player_inventory, tile);
  }

  public ContainerLaserHousing(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.LASER_HOUSING, id, player_inventory, data);
  }

}
