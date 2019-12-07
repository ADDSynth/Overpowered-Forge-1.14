package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerPortalFrame extends BaseContainer {

  public ContainerPortalFrame(final int id, final PlayerInventory player_inventory){
    super(Containers.PORTAL_FRAME, id, player_inventory, Machines.portal_frame);
    make_player_inventory(player_inventory);
    addSlot(new RestrictedSlot(null, 0, TilePortalFrame.input_filter, 80, 37));
  }

}
