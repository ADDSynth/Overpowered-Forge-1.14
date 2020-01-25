package addsynth.overpoweredmod.machines.data_cable;

import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public final class TileDataCable extends TileEntity implements IBlockNetworkUser, ITickableTileEntity {

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
        if(cable_network == null){
          createBlockNetwork();
        }
      }
      first_tick = false;
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
