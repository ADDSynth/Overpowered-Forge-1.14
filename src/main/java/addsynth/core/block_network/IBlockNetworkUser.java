package addsynth.core.block_network;

import javax.annotation.Nullable;
import addsynth.core.game.tiles.TileBase;

/**
 * <p>Attach this to TileEntities that use a Block Network. The {@link BlockNetwork} class
 *    uses this to set the BlockNetwork of all the TileEntities to the same Block Network.</p>
 * <p>If your Block Network has data that must be saved with each TileEntity, you MUST
 *    override the {@link TileBase#update_data update_data()} method and instead update
 *    your block network. During the update process call your own TileEntity's method to
 *    set the data, then call <code>super.update_data()</code>.</p>
 */
public interface IBlockNetworkUser<T extends BlockNetwork> {

  public void setBlockNetwork(T network);

  /** DO NOT CREATE BLOCK NETWORKS INSIDE THIS FUNCTION! {@link BlockNetworkUtil} calls this
   *  to get the BlockNetwork from the TileEntity, which then begins an infinite call loop!
   */
  public @Nullable T getBlockNetwork();

  /** This function is called by {@link BlockNetworkUtil#create_or_join} after initializing the
   *  Block Network. Use this function to set Block Network data from the TileEntity, after it's
   *  been loaded by Minecraft.
   */
  public default void load_block_network_data(){
  }

}
