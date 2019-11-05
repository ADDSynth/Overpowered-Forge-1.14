package addsynth.core.block_network;

/**
 *  Attach this to TileEntities that use a Block Network. The BlockNetwork class uses this to
 *  set the BlockNetwork of all the TileEntities to the same Block Network.
 */
public interface IBlockNetworkUser {

  /**
   * Sets the network variable of your TileEntity. Use a cast for specific Block Networks.
   * @param network
   */
  public void setBlockNetwork(BlockNetwork network);

  /** Used by the {@link BlockNetwork} class.
   *  @return the BlockNetwork regardless whether it is null or not.
   */
  public BlockNetwork getBlockNetwork();

  /** It is up to the specific TileEntity how they want to use this function, normally returns the
   *  ACTUAL specific Block Network. Additionally, this can test if the variable is null and call
   *  the createBlockNetwork() function to create it so that whatever code is requesting the block
   *  network always gets a valid object returned.
   */
  public BlockNetwork getNetwork();

  /**
   * <p>
   * Used by TileEntities to initialize the specific type of BlockNetwork that they carry,
   * and then immediately call their updateNetwork() method.
   * </p>
   * <b>Example:</b><br>
   * <code>
   * <pre>
   * public void createBlockNetwork(){
   *   if(world == null){
   *     throw new NullPointerException("Can't create BlockNetwork because world is not loaded yet.");
   *   }
   *   if(world.isRemote == false){
   *     network = new BlockNetwork(world, block_type);
   *     network.updateNetwork(pos);
   *   }
   * }
   * </pre>
   * </code>
   */
  public void createBlockNetwork();

}
