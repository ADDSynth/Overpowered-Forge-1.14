package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.tiles.machines.automatic.TileCrystalMatterReplicator;
import net.minecraft.inventory.IInventory;

/** Used by guis that don't have any inventories or inventory slots, all they do is display
 *  text and information from the TileEntity.
 */
public final class ContainerCrystalGenerator extends BaseContainer<TileCrystalMatterReplicator> {

  public ContainerCrystalGenerator(final IInventory player_inventory, final TileCrystalMatterReplicator tile){
    super(tile);
    make_player_inventory(player_inventory,8,110);
    int i;
    for(i = 0; i < 8; i++){
      addSlot(new OutputSlot(tile,i,8 + (i*18),54));
    }
  }

}
