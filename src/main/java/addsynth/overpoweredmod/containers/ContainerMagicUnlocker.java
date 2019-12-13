package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileMagicUnlocker;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerMagicUnlocker extends BaseContainer<TileMagicUnlocker> {

  public ContainerMagicUnlocker(final int id, final PlayerInventory player_inventory){
    super(Containers.MAGIC_INFUSER, id, player_inventory);
  }

  public ContainerMagicUnlocker(final int id, final PlayerInventory player_inventory, final TileMagicUnlocker tile){
    super(Containers.MAGIC_INFUSER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerMagicUnlocker(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.MAGIC_INFUSER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(tile,0,TileMagicUnlocker.slot_data[0].filter,38,44));
    addSlot(new InputSlot(tile,1,TileMagicUnlocker.slot_data[1].filter,56,44));
    addSlot(new OutputSlot(tile, 0, 111, 44));
  }

}
