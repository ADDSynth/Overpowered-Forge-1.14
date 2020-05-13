package addsynth.energy.energy_network.tiles;

import addsynth.core.block_network.BlockNetworkUtil;
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
        BlockNetworkUtil.create_or_join(world, this, EnergyNetwork::new);
        first_tick = false;
      }
      network.tick(this);
    }
  }

  @Override
  public void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, EnergyNetwork::new);
  }

  @Override
  public EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public void setBlockNetwork(EnergyNetwork network){
    this.network = network;
  }

}
