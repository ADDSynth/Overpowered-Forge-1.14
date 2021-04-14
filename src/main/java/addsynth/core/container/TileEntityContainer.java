package addsynth.core.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

/** Use this Container only if your TileEntity doesn't have any inventories.
 *  If it does, you'll have to use one of the other Container classes.
 * @param <T>
 */
public abstract class TileEntityContainer<T extends TileEntity> extends AbstractContainer {

  protected final T tile;

  public TileEntityContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id, player_inventory);
    this.tile = tile;
  }

  @SuppressWarnings("unchecked")
  public TileEntityContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
    this.tile = (T)(player_inventory.player.world.getTileEntity(data.readBlockPos()));
  }

  @Override
  public boolean canInteractWith(final PlayerEntity player){
    return isWithinUsableDistance(IWorldPosCallable.DUMMY, player, tile.getBlockState().getBlock());
  }

  public final T getTileEntity(){
    return tile;
  }

}
