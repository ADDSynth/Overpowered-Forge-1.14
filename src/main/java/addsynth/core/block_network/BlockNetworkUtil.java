package addsynth.core.block_network;

import addsynth.core.util.MinecraftUtility;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockNetworkUtil {

  @SuppressWarnings({ "unchecked", "resource" })
  public static final <B extends BlockNetwork, T extends TileEntity & IBlockNetworkUser> B find_existing_network(final T tile){
    final World    world    = tile.getWorld();
    final BlockPos position = tile.getPos();
    BlockPos offset;
    T check_tile;
    B network = null;
    for(final Direction direction : Direction.values()){
      offset = position.offset(direction);
      check_tile = (T)MinecraftUtility.getTileEntity(offset, world, tile.getClass());
      if(check_tile != null){
        network = (B)check_tile.getBlockNetwork();
        break;
      }
    }
    return network;
  }

}
