package addsynth.core.block_network;

/**
 *  Attach this to TileEntities that use a Block Network. The BlockNetwork class uses this to
 *  set the BlockNetwork of all the TileEntities to the same Block Network.
 */
public interface IBlockNetworkUser<T extends BlockNetwork> {

  public void setBlockNetwork(T network);

  public T getBlockNetwork();

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
