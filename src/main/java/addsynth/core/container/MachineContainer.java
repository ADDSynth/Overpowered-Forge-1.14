package addsynth.core.container;

import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.IOutputInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

/** Use this Container for TileEntities that use both Input and Output Inventories, such as machines. */
public abstract class MachineContainer<T extends TileEntity & IInputInventory & IOutputInventory> extends AbstractContainer<T> {

  public MachineContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id, player_inventory, tile);
  }

  public MachineContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
  }

}
