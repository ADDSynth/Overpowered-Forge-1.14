package addsynth.core.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

/** This container is used for blocks that don't need to
 *  be a TileEntity.
 */
public abstract class BlockContainer extends AbstractContainer {

  private final Block block;

  public BlockContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final Block block){
    super(type, id, player_inventory);
    this.block = block;
  }

  public BlockContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id, player_inventory, data);
    this.block = player_inventory.player.world.getBlockState(data.readBlockPos()).getBlock();
  }

  @Override
  public boolean canInteractWith(final PlayerEntity player){
    return isWithinUsableDistance(IWorldPosCallable.DUMMY, player, this.block);
  }

}
