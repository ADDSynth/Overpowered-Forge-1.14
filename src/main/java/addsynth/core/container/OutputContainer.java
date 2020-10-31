package addsynth.core.container;

import addsynth.core.inventory.IOutputInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

/** Use this Container for TileEntities that only have an Output Inventory. */
public abstract class OutputContainer<T extends TileEntity & IOutputInventory> extends AbstractContainer<T> {

  public OutputContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id, player_inventory, tile);
  }

  public OutputContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
  }

}
