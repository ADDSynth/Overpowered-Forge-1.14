package addsynth.core.container;

import addsynth.core.inventory.IInputInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

/** This Container is for TileEntities that only have an Input Inventory. */
public abstract class InputContainer<T extends TileEntity & IInputInventory> extends AbstractContainer<T> {

  public InputContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id, player_inventory, tile);
  }

  public InputContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
  }

}
