package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.container.BaseContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

public final class ContainerMagicInfuser extends BaseContainer<TileMagicInfuser> {

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
    addSlot(new InputSlot(tile,0,new Item[]{Items.BOOK},38,44));
    addSlot(new InputSlot(tile,1,TileMagicInfuser.getFilter(),56,44));
    addSlot(new OutputSlot(tile, 0, 111, 44));
  }

}
