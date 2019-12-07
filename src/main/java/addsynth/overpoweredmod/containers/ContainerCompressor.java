package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerCompressor extends BaseContainer {

  public ContainerCompressor(final int id, final PlayerInventory player_inventory){
    super(Containers.COMPRESSOR, id, player_inventory, Machines.compressor);
    make_player_inventory(player_inventory,8,100);
    addSlot(new InputSlot(null,0,38,41));
    addSlot(new InputSlot(null,1,56,41));
    addSlot(new OutputSlot(null,0,111,41));
  }

}
