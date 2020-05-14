package addsynth.overpoweredmod.machines.data_cable;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public final class TileDataCable extends TileEntity implements IBlockNetworkUser<DataCableNetwork>, ITickableTileEntity {

  private DataCableNetwork cable_network;
  private boolean first_tick = true;

  public TileDataCable(){
    super(Tiles.DATA_CABLE);
  }

  @Override
  public final void onLoad(){
  }

  @Override
  public void tick(){
    if(first_tick){
      if(world.isRemote == false){
        BlockNetworkUtil.create_or_join(world, this, DataCableNetwork::new);
      }
      first_tick = false;
    }
  }

  @Override
  public void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, DataCableNetwork::new);
  }

  @Override
  public final void setBlockNetwork(final DataCableNetwork network){
    this.cable_network = network;
  }

  @Override
  @Nullable
  public final DataCableNetwork getBlockNetwork(){
    return cable_network;
  }

}
