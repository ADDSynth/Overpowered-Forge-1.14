package addsynth.overpoweredmod.tiles;

import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.overpoweredmod.game.block_networks.DataCableNetwork;
import addsynth.overpoweredmod.game.core.Wires;
import net.minecraft.tileentity.TileEntity;

public final class TileDataCable extends TileEntity implements IBlockNetworkUser {

  private DataCableNetwork cable_network;

  public TileDataCable(){
    super(Tiles.DATA_CABLE);
  }

  @Override
  public final void onLoad(){
    if(world.isRemote == false){
      createBlockNetwork();
    }
  }

  @Override
  public final void setBlockNetwork(final BlockNetwork network){
    this.cable_network = (DataCableNetwork)network;
  }

  @Override
  public final DataCableNetwork getNetwork(){
    return cable_network;
  }

  @Override
  public final BlockNetwork getBlockNetwork(){
    return cable_network;
  }

  @Override
  public final void createBlockNetwork(){
    cable_network = new DataCableNetwork(world, Wires.data_cable, this);
    if(world.isRemote == false){ // TODO: only run on server.
      cable_network.updateNetwork(pos);
    }
  }

}
