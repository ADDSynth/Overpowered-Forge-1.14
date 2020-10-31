package addsynth.energy.gameplay.universal_energy_interface;

import addsynth.core.container.AbstractContainer;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerUniversalInterface extends AbstractContainer<TileUniversalEnergyTransfer> {

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory, final TileUniversalEnergyTransfer tile){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory, tile);
  }

  public ContainerUniversalInterface(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.UNIVERSAL_ENERGY_INTERFACE, id, player_inventory, data);
  }

}
