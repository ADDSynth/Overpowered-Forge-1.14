package addsynth.core.block_network;

/**
 *  Attach this to TileEntities that use a Block Network. The {@link BlockNetwork} class uses this to
 *  set the BlockNetwork of all the TileEntities to the same Block Network.
 */
public interface IBlockNetworkUser<T extends BlockNetwork> {

  public void setBlockNetwork(T network);

  public T getBlockNetwork();

  /** This function is called by {@link BlockNetworkUtil#create_or_join} after initializing the
   *  Block Network. Use this function to set Block Network data from the TileEntity, after it's
   *  been loaded by Minecraft.
   */
  public default void load_block_network_data(){
  }

}
