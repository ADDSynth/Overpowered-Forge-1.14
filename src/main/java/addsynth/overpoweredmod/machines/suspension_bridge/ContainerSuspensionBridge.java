package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.container.AbstractContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerSuspensionBridge extends AbstractContainer<TileSuspensionBridge> {

  public static final int lens_slot_x = 53;

  public ContainerSuspensionBridge(final int id, final PlayerInventory player_inventory, final TileSuspensionBridge tile){
    super(Containers.ENERGY_SUSPENSION_BRIDGE, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerSuspensionBridge(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.ENERGY_SUSPENSION_BRIDGE, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory, 23, 85);
    addSlot(new InputSlot(tile, 0, TileSuspensionBridge.filter, 1, lens_slot_x, 20));
  }

}
