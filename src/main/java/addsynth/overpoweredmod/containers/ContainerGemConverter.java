package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.tiles.machines.automatic.TileGemConverter;
import net.minecraft.inventory.IInventory;

public final class ContainerGemConverter extends BaseContainer<TileGemConverter> {

  public ContainerGemConverter(final IInventory player_inventory, final TileGemConverter tile){
    super(tile);
    make_player_inventory(player_inventory,8,112);
    addSlotToContainer(new InputSlot(tile, 0, Gems.gem_items,48,45));
    addSlotToContainer(new OutputSlot(tile,0,104,45));
  }

}
