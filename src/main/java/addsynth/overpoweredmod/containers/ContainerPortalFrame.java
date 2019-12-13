package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerPortalFrame extends BaseContainer<TilePortalFrame> {

  public ContainerPortalFrame(final int id, final PlayerInventory player_inventory){
    super(Containers.PORTAL_FRAME, id, player_inventory);
  }

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
    addSlot(new RestrictedSlot(tile.getInputInventory(), 0, TilePortalFrame.input_filter, 80, 37));
  }

}
