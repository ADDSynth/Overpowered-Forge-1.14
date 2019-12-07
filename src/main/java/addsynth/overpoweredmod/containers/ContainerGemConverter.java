package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerGemConverter extends BaseContainer {

  public ContainerGemConverter(final int id, final PlayerInventory player_inventory){
    super(Containers.GEM_CONVERTER, id, player_inventory, Machines.gem_converter);
    make_player_inventory(player_inventory,8,112);
    addSlot(new InputSlot(null, 0, Gems.gem_items,48,45));
    addSlot(new OutputSlot(null,0,104,45));
  }

}
