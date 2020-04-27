package addsynth.energy.energy_network.tiles;

import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.energy_network.EnergyNetwork;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEnergyNetwork extends TileEntity implements ITickableTileEntity, IBlockNetworkUser<EnergyNetwork> {

  private EnergyNetwork network;
  private boolean first_tick = true;

  public TileEnergyNetwork(final TileEntityType type){
    super(type);
  }

  @Override
  public void tick(){
    if(world.isRemote == false){
      if(first_tick){
        if(network == null){
          createBlockNetwork();
        }
        first_tick = true;
      }
    }
  }

  @Override
  public EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public void setBlockNetwork(EnergyNetwork network){
    this.network = network;
  }

  @Override
  public void createBlockNetwork(){
    if(network == null){
      network = new EnergyNetwork(world, this);
    }
  }

}
