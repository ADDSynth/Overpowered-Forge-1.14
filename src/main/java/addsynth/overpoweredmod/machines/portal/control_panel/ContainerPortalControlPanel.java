package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerPortalControlPanel extends BaseContainer<TilePortalControlPanel> {

  public ContainerPortalControlPanel(final int id, final PlayerInventory player_inventory, final TilePortalControlPanel tile){
    super(Containers.PORTAL_CONTROL_PANEL, id, player_inventory, tile);
  }

  public ContainerPortalControlPanel(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.PORTAL_CONTROL_PANEL, id, player_inventory, data);
  }

}
