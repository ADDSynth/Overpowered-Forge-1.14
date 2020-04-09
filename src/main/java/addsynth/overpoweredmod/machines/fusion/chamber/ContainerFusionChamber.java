package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.container.BaseContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerFusionChamber extends BaseContainer<TileFusionChamber> {

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
    addSlot(new InputSlot(tile, 0, TileFusionChamber.input_filter, 80, 37));
  }

}
