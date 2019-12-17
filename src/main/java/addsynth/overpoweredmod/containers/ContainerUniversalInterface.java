package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerUniversalInterface extends BaseContainer<TileUniversalEnergyTransfer> {

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory);
  }

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory, final TileUniversalEnergyTransfer tile){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory, tile);
  }

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory, data);
  }

}
