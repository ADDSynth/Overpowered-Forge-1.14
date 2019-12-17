package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.registers.Containers;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerFusionChamber extends BaseContainer<TileFusionChamber> {

  public ContainerFusionChamber(final int id, final PlayerInventory player_inventory){
    super(Containers.FUSION_CHAMBER, id, player_inventory);
  }

  public ContainerFusionChamber(final int id, final PlayerInventory player_inventory, final TileFusionChamber tile){
    super(Containers.FUSION_CHAMBER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerFusionChamber(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.FUSION_CHAMBER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory);
    addSlot(new RestrictedSlot(tile.getInputInventory(), 0, TileFusionChamber.input_filter, 80, 37));
  }

}
