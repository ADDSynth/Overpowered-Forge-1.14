package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.automatic.TileMagicUnlocker;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerMagicUnlocker extends BaseContainer {

  public ContainerMagicUnlocker(final int id, final PlayerInventory player_inventory){
    super(Containers.MAGIC_INFUSER, id, player_inventory, Machines.magic_infuser);
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(null,0,TileMagicUnlocker.slot_data[0].filter,38,44));
    addSlot(new InputSlot(null,1,TileMagicUnlocker.slot_data[1].filter,56,44));
    addSlot(new OutputSlot(null, 0, 111, 44));
  }

}
