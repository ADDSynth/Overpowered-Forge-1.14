package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.RestrictedSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerFusionChamber extends BaseContainer {

  public ContainerFusionChamber(final int id, final PlayerInventory player_inventory){
    super(Containers.FUSION_CHAMBER, id, player_inventory, Machines.singularity_container);
    make_player_inventory(player_inventory);
    addSlot(new RestrictedSlot(null, 0, TileFusionChamber.input_filter, 80, 37));
  }

}
