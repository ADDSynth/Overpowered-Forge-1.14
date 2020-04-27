package addsynth.core.block_network;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * A Block Network is a collection of Block Positions that we can use to search for other types of
 * blocks. It is updated whenever a nearby block is changed.</br>
 * This is a base abstract class which just holds all the standard functionality. The only reason
 * you'd want a BlockNetwork is to hold additional information, so extend this class with your own.<br>
 * <b>Required:</b> Blocks in a Block Network all hold a reference to the SAME Block Network. This reference
 * variable must be in a TileEntity which implements the {@link IBlockNetworkUser} interface.<br>
 * @see addsynth.energy.energy_network.EnergyNetwork EnergyNetwork
 * @see addsynth.overpoweredmod.machines.laser.machine.LaserNetwork
 * @see addsynth.overpoweredmod.machines.data_cable.DataCableNetwork
 * @see addsynth.overpoweredmod.machines.suspension_bridge.BridgeNetwork
 * @author ADDSynth
 */
public abstract class BlockNetwork<T extends TileEntity & IBlockNetworkUser> {

  /** Only code that searches for blocks on the server side needs to use the world variable. */
  protected final World world;

  @Nonnull
  protected T first_tile;

  protected final Class<T> class_type;

  /** All the blocks that are in this block network. */
  protected final NodeList blocks = new NodeList();



  @SuppressWarnings("unchecked")
  public BlockNetwork(@Nonnull final World world, @Nonnull final T tile){
    if(world.isRemote){
      ADDSynthCore.log.error("Block Networks have no business being created on the Client-Side! You can't and shouldn't do anything with them on the Client-side!");
      Thread.dumpStack();
    }
    this.world = world instanceof ServerWorld ? world : null;
    this.first_tile = tile;
    this.class_type = (Class<T>)tile.getClass();
  }



  // This works perfectly and very efficiently. Never change it!
  protected static final void remove_invalid_nodes(final ArrayList<? extends Node> node_list){
    node_list.removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  @Deprecated // DELETE
  private final void setNetwork(final BlockPos position){
    final TileEntity tile = world.getTileEntity(position);
    if(tile != null){
      if(tile instanceof IBlockNetworkUser){
        ((IBlockNetworkUser)tile).setBlockNetwork(this);
      }
      else{
      }
    }
    else{
      throw new NullPointerException("Could not find a TileEntity at position "+position.toString()+". BlockNetwork variables must be assigned to Tile Entities.");
    }
  }

  @Nullable
  public static final BlockNetwork getNetwork(final World world, final BlockPos position){
    final TileEntity tile = world.getTileEntity(position);
    if(tile != null){
      if(tile instanceof IBlockNetworkUser){
        return ((IBlockNetworkUser)tile).getBlockNetwork();
      }
      // throw new RuntimeException("The TileEntity "+tile.toString()+" does not implement the IBlockNetwork interface.");
    }
    // throw new RuntimeException("Could not find a TileEntity at position "+position.toString()+". BlockNetwork variables must be assigned to Tile Entities.");
    return null;
  }

  public void updateNetwork(final BlockPos from){
    final TileEntity tile = world.getTileEntity(from);
    if(is_valid_tile(tile)){
      updateNetwork(from, (T)tile);
    }
  }

  /**
   * Must be called when splitting or joining BlockNetworks, and right after creating BlockNetworks during TileEnity load.
   * @param from
   */
  public void updateNetwork(final BlockPos from, final T tile){
    if(world != null){
      if(world.isRemote == false){
        onBeforeUpdate();

        blocks.clear();
        clear_custom_data();
        final ArrayList<BlockPos> searched = new ArrayList<>(100);
        first_tile = tile;
        blocks.add(new Node(from, tile));
        searched.add(from);
        search_algorithm(from, searched);

        onUpdateNetworkFinished(from);
        return;
      }
    }
    ADDSynthCore.log.error("BlockNetwork.updateNetwork() method is not supposed to be called on client-side.");
    // Thread.dumpStack();
  }

  private final void search_algorithm(final BlockPos from, ArrayList<BlockPos> searched){
    Block block;
    BlockPos position;
    TileEntity tile;
    for(Direction side: Direction.values()){
      position = from.offset(side);
      if(searched.contains(position) == false){
        searched.add(position);
        block = world.getBlockState(position).getBlock();
        tile  = world.getTileEntity(position);
        if(is_valid_tile(tile)){
          blocks.add(new Node(position, tile));
          setNetwork(position);
          search_algorithm(position, searched);
        }
        customSearch(position, block, tile);
      }
    }
  }

  private final boolean is_valid_tile(final TileEntity tile){
    if(tile != null){
      if(tile.isRemoved() == false && class_type.isInstance(tile)){
        return true;
      }
    }
    return false;
  }

  /**
   * Required! call this method in breakBlock() methods of Blocks, like this:
   * <code>
   * <pre>
   * public void breakBlock(World world, BlockPos pos, IBlockState state){
   *   if(world.isRemote){
   *     super.breakBlock(world, pos, state);
   *   }
   *   else{
   *     BlockNetwork network = BlockNetwork.getNetwork(world, pos);
   *     super.breakBlock(world, pos, state); // breaks the block and TileEntity.
   *     BlockNetwork.block_was_broken(network, world, pos, this);
   *   }
   * }
   * </pre>
   * </code>
   * @param existing_network
   * @param world
   * @param pos
   * @param block
   */
  public static final void block_was_broken(final BlockNetwork existing_network, final World world, final BlockPos pos, final Block block){
    BlockPos new_position;
    IBlockNetworkUser tile;
    for(Direction side : Direction.values()){
      new_position = pos.offset(side);
      if(world.getBlockState(new_position).getBlock() == block){
        tile = (IBlockNetworkUser)world.getTileEntity(new_position);
        if(tile.getBlockNetwork() == existing_network){
          tile.createBlockNetwork();
        }
      }
    }
  }

  protected final boolean is_redstone_powered(){
    boolean powered = false;
    for(final Node node : blocks){
      if(world.isBlockPowered(node.position)){
        powered = true;
        break;
      }
    }
    return powered;
  }

  public final int getCount(){
    return blocks == null ? 0 : blocks.size();
  }

  protected abstract void clear_custom_data();

  /** Called before updateNetwork() executes. */
  protected void onBeforeUpdate(){ // UNUSED DELETE
  }

  /**
   * Called when updateNetwork() function is completed.
   */
  protected void onUpdateNetworkFinished(final BlockPos origin_position){
  }

  protected abstract void customSearch(final BlockPos position, final Block block, final TileEntity tile);

  /**<p>
   *   <b>Required:</b> call this in the block's
   *   {@link Block#neighborChanged(IBlockState, World, BlockPos, Block, BlockPos)} method.<br>
   *   <b>Do not use</b> the {@link Block#onNeighborChange(IBlockAccess, BlockPos, BlockPos)} method!
   *   Starting in Minecraft 1.11 the {@link World#updateComparatorOutputLevel(BlockPos, Block)} method
   *   is no longer called at the end of the {@link World#setTileEntity(BlockPos, TileEntity)} function,
   *   so it doesn't update Block Networks at all.
   * <p>
   *   If the neighboring block is a block that holds this type of BlockNetwork (such as a wire), then that
   *   block also has a TileEntity which automatically adds itself to the existing network when its
   *   {@link TileEntity#onLoad()} method is called, and correctly reassigns block networks when its
   *   breakBlock() method is called.
   * <p>
   *   This method is used to check for additional blocks that the BlockNetwork should look for, such as machines.
   *   If the reported position that changed is a valid block then call updateNetwork().
   *   If the reported position is not a TileEntity or not a valid block, then you need to check the custom
   *   data to make sure it is still valid. For entities you can call the TileEntity.isInvalid() function.
   * @param current_position
   * @param position_of_neighbor
   */
  public abstract void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor);

}
