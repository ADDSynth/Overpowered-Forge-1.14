package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.container.BaseContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerPortalFrame extends BaseContainer<TilePortalFrame> {

  public ContainerPortalFrame(final int id, final PlayerInventory player_inventory, final TilePortalFrame tile){
    super(Containers.PORTAL_FRAME, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerPortalFrame(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.PORTAL_FRAME, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory);
    addSlot(new InputSlot(tile, 0, TilePortalFrame.getFilter(), 80, 37));
  }

}
