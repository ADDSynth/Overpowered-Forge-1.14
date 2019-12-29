package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerMagicInfuser extends BaseContainer<TileMagicInfuser> {

  public ContainerMagicInfuser(final int id, final PlayerInventory player_inventory){
    super(Containers.MAGIC_INFUSER, id, player_inventory);
  }

  public ContainerMagicInfuser(final int id, final PlayerInventory player_inventory, final TileMagicInfuser tile){
    super(Containers.MAGIC_INFUSER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerMagicInfuser(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.MAGIC_INFUSER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(tile,0,TileMagicInfuser.slot_data[0].filter,38,44));
    addSlot(new InputSlot(tile,1,TileMagicInfuser.slot_data[1].filter,56,44));
    addSlot(new OutputSlot(tile, 0, 111, 44));
  }

}
