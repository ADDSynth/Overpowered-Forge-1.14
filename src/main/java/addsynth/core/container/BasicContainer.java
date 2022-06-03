package addsynth.core.container;

import addsynth.core.game.inventory.IStorageInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

/** Use this Container if your TileEntity uses a standard Inventory that allows
 *  items to be inserted and extracted.
 * @param <T>
 */
public abstract class BasicContainer<T extends TileEntity & IStorageInventory> extends TileEntityContainer<T> {

  public BasicContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id, player_inventory, tile);
  }

  public BasicContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
  }

}
