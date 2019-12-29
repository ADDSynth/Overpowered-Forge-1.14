package addsynth.overpoweredmod.machines.gem_converter;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerGemConverter extends BaseContainer<TileGemConverter> {

  public ContainerGemConverter(final int id, final PlayerInventory player_inventory){
    super(Containers.GEM_CONVERTER, id, player_inventory);
  }

  public ContainerGemConverter(final int id, final PlayerInventory player_inventory, final TileGemConverter tile){
    super(Containers.GEM_CONVERTER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerGemConverter(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.GEM_CONVERTER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,112);
    addSlot(new InputSlot(tile, 0, Gems.gem_items,48,45));
    addSlot(new OutputSlot(tile,0,104,45));
  }

}
