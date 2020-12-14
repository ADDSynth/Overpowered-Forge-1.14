package addsynth.core.block_network;

import java.util.Collection;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.block.BlockUtil;
import addsynth.energy.energy_network.EnergyNetwork;
import addsynth.overpoweredmod.machines.data_cable.DataCableNetwork;
import addsynth.overpoweredmod.machines.laser.machine.LaserNetwork;
import addsynth.overpoweredmod.machines.suspension_bridge.BridgeNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * <p>A Block Network is a collection of Block Positions of TileEntities that should act as a single
 * entity and/or share data between them. To stay in-sync it needs to be updated whenever a
 * TileEntity is added or removed.
 * 
 * <p>This is a base abstract class which just holds all the standard functionality. The only reason
 * you'd want a BlockNetwork is to hold additional data, so extend this class with your own.
 *
 * <p><b>Required:</b> TileEntities in a Block Network all hold a reference to the SAME Block Network.
 * This reference variable must be in a TileEntity which implements the {@link IBlockNetworkUser} interface.<br>
 * 
 * <p>-----------------------------------------------------------------------------------------------
 * <p>Here I will describe how BlockNetworks function for my own sanity and others:
 * 
 * <p><b>1.</b> When a player sets down a TileEntity that is new, its block_network variable is null,
 * so it must be initialized to a new BlockNetwork instance. Then it must immediately be updated to add
 * itself to the list of blocks in the BlockNetwork.
 * 
 * <p><b>2.</b> Now say the player places another TileEntity down next to the first one. The easiest
 * solution would be to initialize a new BlockNetwork, and update it. If you do this, then you
 * had BETTER ensure that all TileEntities you find that use this BlockNetwork should be set to THIS
 * BlockNetwork.
 * <p>However, we have a problem. What you did was create a NEW BlockNetwork, then set all TileEntity's
 * block_network variable to this new BlockNetwork. This overwrites the data that any existing
 * BlockNetwork had to their default values.
 * <p>So, the correct way to handle this is, when a TileEntity is created, you first check adjacent
 * positions for existing BlockNetworks, then set our own block_network variable to the one we found.
 * 
 * <p><b>3.</b> So Now let's set down a bunch of TileEntities, and they all share the same BlockNetwork.
 * You Quit the world, then load it back up again. The game will load the TileEntities, but in what order?
 * You can safely assume that the first TileEntity to load will correctly initialize its BlockNetwork field,
 * call its update function, and correctly find all connecting TileEntities and set their block_network variable.
 * So, whenever the World loads the next TileEntity, you'd better ONLY create a new BlockNetwork if it's null.
 * 
 * <p><b>4.</b> What happens when we remove a single TileEntity is pretty straight-forward. Minecraft
 * invalidates the TileEntity and then removes it, and when that happens there are no more references to the
 * TileEntity or its block_network variable. But what about a TileEntity that is a part of a BlockNetwork
 * of multiple TileEntities? Well first we must detect the TileEntity is being removed, then we must update
 * the BlockNetwork, but prevent it from finding the TileEntity that is being removed.
 * 
 * <p><b>5.</b> Continuing from #2, You must've wondered what happens if you place a TileEntity that
 * is adjacent to multiple TileEntities, but they were not connected, so they each have a different
 * BlockNetwork. We still must find an existing BlockNetwork and join it, but which one? Well each one
 * contains the same type of data but possibly different values, it would be far too cumbersome to try
 * to average them all out, so to simplify this case scenerio, we just accept the first one we find and
 * call the update method, which will find all TileEntities and overwrite their block_network variable.
 * 
 * <p><b>6.</b> Continuing from #4, and the inverse of #5, what happens if you have a row of TileEntities,
 * and you remove one in the middle, thus creating multiple BlockNetworks? This is by far the most
 * complicated scenerio that can happen. The TileEntity CAN be removed from the BlockNetwork's internal
 * list of blocks, but all TileEntities are still part of the SAME BlockNetwork, even though there's a gap!
 * So what we need to do is call the updateBlockNetwork function using the position of one of the
 * adjacent TileEntities. This will correctly setup one side of the gap, but not the others! We also need
 * to ensure that we keep the BlockNetwork's data as best as we can.
 * <p>So here's how we do it. when a TileEntity is removed, go ahead and detect which sides have the same
 * type of TileEntity on each side, this means they WERE part of the original BlockNetwork. Call the update
 * method on the first position we find, this will correctly re-assign all block positions. Continue
 * checking all the other sides of the TileEntity being deleted, and if we find a TileEntity, it's either
 * an orphan or now counted as part of the first BlockNetwork. We should check that position against the
 * positions we've just finished discovering from the first BlockNetwork. If they aren't in the new list,
 * that means they are no longer connected, and should be a new BlockNetwork.
 * <p>Any new BlockNetworks we create, we can assign their data to that of the original BlockNetwork's data,
 * but this just duplicates the data. Where there were previously 1 BlockNetwork with the data, now there
 * are 2 or more. This duplicates data such as Energy, or items that were a part of the BlockNetwork.
 * We obviously don't want players to exploit this duplication, so all new BlockNetworks must be reset
 * to their default data.
 *
 * <p><b>7.</b> Oh dang. There's actually one more scenario I forgot about. Some block networks have extra
 * data, sure, but let's say you want to keep a list of TileEntities this BlockNetwork connects to,
 * Tiles that AREN'T part of the Block Network. For example, the {@link EnergyNetwork}
 * keeps track of {@link addsynth.energy.main.IEnergyUser Energy Users} that the network connects to.
 * In this case, you want to update the Block Network (or at least the BlockNetwork's data) whenever
 * you detect the block next to a TileEntity that belongs to this Block Network was CHANGED, because
 * a block could've been added or removed, and you need to update the data accordingly. So, we use
 * {@link Block#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)}
 * to detect the block beside us has changed and then call our BlockNetwork's update event.
 *
 * <p><b>8.</b> So now let's talk about BlockNetwork data. Out of the 4 BlockNetwork examples that exist in
 * ADDSynth's mods as of writing this, {@link EnergyNetwork} and {@link DataCableNetwork} only store positions
 * of TileEntities. That can be calculated at runtime and doesn't need to save any data to TileEntities.
 * However, {@link LaserNetwork} has a shared boolean value that determines if the LaserNetwork is running
 * or not. When a player toggles the On/Off switch, this must set the BlockNetwork's variable, and then
 * update all TileEntities. And {@link BridgeNetwork} allows a player to insert a Lens in any Suspension Bridge
 * block, and all Bridge blocks share the same network, so the player can access the Lens from any other block.
 * <p>The function for updating all the TileEntities in the {@link BlockNetwork#blocks} list doesn't exist,
 * so you'll have to make your own, and call it whenever your BlockNetwork's data changes. Also, when the
 * world loads, it does create new BlockNetworks, but you also want it to load saved data. For this reason
 * {@link BlockNetworkUtil#create_or_join} automatically
 * calls your TileEntity's {@link IBlockNetworkUser#load_block_network_data()} function.
 *
 * <p><b>9.</b> One last bit of advice. Sometimes a BlockNetwork wants information on another BlockNetwork,
 * so it calls that TileEntity's {@link IBlockNetworkUser#getBlockNetwork()} function. But the return
 * value of that function MIGHT be null. This only happens during World load, when one BlockNetwork starts
 * loading and updating before the other. Because of the way {@link BlockNetworkUtil#create_or_join} is
 * set up right now, you can't put anything in the {@link IBlockNetworkUser#getBlockNetwork()} that will
 * initialize a BlockNetwork because that would cause an infinite loop. Instead, if you depend on
 * another BlockNetwork during a BlockNetwork update, check if it is null and initialize it yourself.
 *
 * <p><b>10.</b> Continuing from #7, This scenerio involves when during the BlockNetwork update, it changes
 * a block in the world, such as by calling {@link World#setBlockState(BlockPos, BlockState, int)}.
 * This will then call {@link Block#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)}
 * Which can cause some Block Networks to begin updating again, while during the first update, which may
 * trigger a Null Pointer Exception. Argh, I'm not explaining this very well. You must either check
 * if the Block Network is null, or check what kind of BlockNetwork it is and create a new one yourself,
 * which in turn calls that BlockNetwork's update method anyway.
 * <p>Currently, all of our neighbor detections assumes this only occurs during normal gameplay, and NOT
 * DURING WORLD LOAD! So there are no null checks. Luckily it seems that only the Energy Suspension Bridge
 * changes the world during its Block Network update, and it doesn't need to detect when blocks next to it
 * has changed.
 *
 * @see EnergyNetwork
 * @see LaserNetwork
 * @see DataCableNetwork
 * @see BridgeNetwork
 * @author ADDSynth
 * @since July 1, 2020
 */
public abstract class BlockNetwork<T extends TileEntity & IBlockNetworkUser> {

  /** Only code that searches for blocks on the server side needs to use the world variable. */
  protected final World world;

  /** The first TileEntity to create this BlockNetwork or the first to be discovered.
   *  Used in this BlockNetwork's update function to ensure it only executes once per tick.
   */
  @Nonnull
  protected T first_tile;

  protected Class<T> class_type;

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
  protected static final void remove_invalid_nodes(final Collection<? extends Node> node_list){
    node_list.removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  public final void updateBlockNetwork(final BlockPos from){
    updateBlockNetwork(from, first_tile);
  }

  /**
   * Must be called when splitting or joining BlockNetworks, and right after creating BlockNetworks during TileEnity load.
   * @param from
   * @param tile First TileEntity in network, or a new TileEntity if the first was destroyed.
   */
  public final void updateBlockNetwork(final BlockPos from, final T tile){
    if(world != null){
      if(world.isRemote == false){
        try{
          blocks.clear();
          clear_custom_data();
          blocks.setFrom(BlockUtil.find_blocks(from, world, this::is_valid_tile, this::customSearch));
          blocks.setBlockNetwork(this);
  
          // Changes first_tile less often, only when necessary
          if(blocks.contains(first_tile) == false){
            first_tile = tile;
          }
  
          onUpdateNetworkFinished();
        }
        catch(Exception e){
          ADDSynthCore.log.fatal("Error occured in BlockNetwork update! WHAT HAPPENED???", e);
        }
        return;
      }
    }
    ADDSynthCore.log.error("BlockNetwork.updateNetwork() method is not supposed to be called on client-side.");
    // Thread.dumpStack();
  }

  private final boolean is_valid_tile(final Node node){
    final TileEntity tile = node.getTile();
    if(tile != null){
      if(tile.isRemoved() == false && class_type.isInstance(tile)){
        return true;
      }
    }
    return false;
  }

  /** This is a common update function that runs every tick. You call this from your TileEntity's tick function.<br />
   *  YOU MUST call this only on the server-side!!!<br />
   *  Every TileEntity of that type will call this! To ensure it is only called ONCE per tick, YOU MUST first check
   *  the incomming TileEntity is equivalent as the <code>first_tile</code>!
   */
  public void tick(final T tile){
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

  public final NodeList getTileEntityList(){
    return blocks;
  }

  protected abstract void clear_custom_data();

  /**
   * Called when updateNetwork() function is completed.
   */
  protected void onUpdateNetworkFinished(){
  }

  protected void customSearch(final Node node){
  }

  // DELETE: Once we start coding for MC 1.16 and leave 1.12 behind, retest the onNeighborChange / neighborChanged issue, and
  //         remove all references to onNeighborChange.
  /**<p>
   *   Call this in the block's
   *   {@link Block#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)} method.<br>
   *   <b>Do not use</b> the {@link Block#onNeighborChange(BlockState, net.minecraft.world.IWorldReader, BlockPos, BlockPos)} method!
   *   Starting in Minecraft 1.11 the {@link World#updateComparatorOutputLevel(BlockPos, Block)} method
   *   is no longer called at the end of the {@link World#setTileEntity(BlockPos, TileEntity)} function,
   *   so it doesn't update Block Networks at all.
   * <p>NOTE: It seems <code>onNeighborChange()</code> is added by Forge and only gets called if the neighbor block was
   *   a TileEntity, whereas <code>neighborChanged()</code> gets called every time an adjacent block gets changed.
   *   Best continue to call Minecraft's <code>neighborChanged()</code> function, in case someone's BlockNetwork
   *   wants to keep track of basic blocks.
   * <p>
   *   We actually recommend you call the simplified helper function
   *   {@link BlockNetworkUtil#neighbor_changed(World, BlockPos, BlockPos)} instead.
   * <p>
   *   Your BlockNetwork automatically responds to block changes if the block is part of your BlockNetwork.
   *   So this is only used to respond to block changes that your BlockNetwork keeps track of, that are
   *   NOT part of your BlockNetwork.
   * <p>
   *   First, you do not want to update on EVERY neighbor block change, only the blocks that affect your
   *   BlockNetwork. Once you detect the type of block, the simplest way to update is to call your BlockNetwork's
   *   {@link #updateBlockNetwork(BlockPos)} function. This will automatically clear your custom data and call
   *   {@link #customSearch(Node)} which again checks the block and then you can decide what to do with it.
   *   This is recommended if your BlockNetwork keeps track of lots of different kinds of blocks. But if
   *   you only track one type of block then we recommend doing the optimized approach, by checking the position
   *   of the neighbor and adding or removing it from your list accordingly.
   * <p>
   *   If the list of TileEntities you keep track of utilize {@link Node Nodes}, then you can call
   *   {@link #remove_invalid_nodes(Collection)} to automatically remove TileEntities that were removed.
   *   Otherwise you'll have to check for removed TileEntities yourself!
   * @see EnergyNetwork#neighbor_was_changed(BlockPos, BlockPos)
   * @see DataCableNetwork#neighbor_was_changed(BlockPos, BlockPos)
   * @see LaserNetwork#neighbor_was_changed(BlockPos, BlockPos)
   * @param current_position
   * @param position_of_neighbor
   */
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
  }

}
