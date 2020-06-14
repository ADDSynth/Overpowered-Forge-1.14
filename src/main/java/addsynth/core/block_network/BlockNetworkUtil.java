package addsynth.core.block_network;

import java.util.function.BiFunction;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockNetworkUtil {

  /**
   * Please use this to simplify the creation of your BlockNetwork variable!
   * Call this in your TileEntity's <code>onLoad()</code> event or first tick!
   * @param <B> ? extends from BlockNetwork&ltT&gt
   * @param <T> ? extends from TileEntity AND IBlockNetworkUser&ltB&gt
   * @param world
   * @param tile The TileEntity attempting to creat a BlockNetwork.
   * @param constructor the function to call to create a new BlockNetwork, usually your BlockNetwork's constructor.
   */
  public static final <B extends BlockNetwork<T>, T extends TileEntity & IBlockNetworkUser<B>> void create_or_join(final World world, final T tile, final BiFunction<World, T, B> constructor){
    if(world == null){
      throw new NullPointerException("Can't create BlockNetwork because the world isn't loaded yet.");
    }
    if(world.isRemote == false){
      B network = tile.getBlockNetwork();
      if(network == null){ // block network doesn't already exist
        network = find_existing_network(world, tile);
        if(network == null){
          // new BlockNetwork
          createBlockNetwork(world, tile, constructor);
        }
        else{
          tile.setBlockNetwork(network);
          network.updateBlockNetwork(tile.getPos(), tile);
          // first existing Network that we find becomes the current Network, and overwrites all other networks.
        }
      }
    }
  }

  /** Only call this if a BlockNetwork requires data from another BlockNetwork during their update event.
   *  All normal BlockNetwork initializing should use the create_or_join() function!
   * @param world
   * @param tile
   * @param constructor
   */
  public static final <B extends BlockNetwork<T>, T extends TileEntity & IBlockNetworkUser<B>> void createBlockNetwork(final World world, final T tile, final BiFunction<World, T, B> constructor){
    if(world.isRemote == false){
      final B network = constructor.apply(world, tile);
      tile.setBlockNetwork(network);
      tile.load_block_network_data();
      network.updateBlockNetwork(tile.getPos(), tile);
    }
  }

  @SuppressWarnings({ "unchecked" })
  private static final <B extends BlockNetwork<T>, T extends TileEntity & IBlockNetworkUser<B>> B find_existing_network(final World world, final T tile){
    final BlockPos position = tile.getPos();
    BlockPos offset;
    T check_tile;
    B network = null;
    for(final Direction direction : Direction.values()){
      offset = position.offset(direction);
      check_tile = (T)MinecraftUtility.getTileEntity(offset, world, tile.getClass());
      if(check_tile != null){
        network = (B)check_tile.getBlockNetwork();
        if(network != null){
          break;
        }
      }
    }
    return network;
  }

  private static final <B extends BlockNetwork<T>, T extends TileEntity & IBlockNetworkUser<B>> void createNewBlockNetwork(final World world, final T tile, final BiFunction<World, T, B> constructor){
    final B network = constructor.apply(world, tile);
    tile.setBlockNetwork(network);
    network.updateBlockNetwork(tile.getPos(), tile);
  }

  /** <b>Required!</b> Call this function in your TileEntity's <code>invalidate()</code> or <code>remove()</code>
   *  function! Remember to call the <code>super</code> method first!
   * @param <B>
   * @param <T>
   * @param destroyed_tile
   * @param constructor
   */
  @SuppressWarnings({ "unchecked", "resource" })
  public static final <B extends BlockNetwork<T>, T extends TileEntity & IBlockNetworkUser<B>> void tileentity_was_removed(final T destroyed_tile, final BiFunction<World, T, B> constructor){
    final World world = destroyed_tile.getWorld();
    if(world.isRemote){
      // ADDSynthCore.log.error("This should not be run on the client-side!");
      return;
    }
    BlockPos offset;
    TileEntity tile;
    NodeList blocks = null;
    for(Direction side : Direction.values()){
      offset = destroyed_tile.getPos().offset(side);
      tile = world.getTileEntity(offset);
      
      if(tile != null){
        if(tile.getClass() == destroyed_tile.getClass()){

          if(blocks == null){
            final B first_network = destroyed_tile.getBlockNetwork();
            if(first_network != null){ // PRIORITY: This caused an error, figure out why this is being called during world load in a Single-player world in regards to the Laser Housings.
              first_network.updateBlockNetwork(offset, (T)tile);
              blocks = first_network.getTileEntityList();
            }
          }
          else{ // first adjacent TileEntity should've already updated its BlockNetwork by now.
            if(blocks.contains(offset) == false){
              createNewBlockNetwork(world, (T)tile, constructor);
              // When splitting one BlockNetwork into multiple networks, I could copy all its data,
              // but this creates duplicates, such as Energy, or items. So to avoid creating duplicates,
              // The first network remains intact, while subsequent orphan blocks become new BlockNetworks.
            }
          }

        }
      }
    }
    destroyed_tile.setBlockNetwork(null);
  }

}
