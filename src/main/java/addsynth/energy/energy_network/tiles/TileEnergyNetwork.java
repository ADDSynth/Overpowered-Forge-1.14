package addsynth.energy.energy_network.tiles;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.tiles.TileBase;
import addsynth.energy.energy_network.EnergyNetwork;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEnergyNetwork extends TileBase implements ITickableTileEntity, IBlockNetworkUser<EnergyNetwork> {

  private EnergyNetwork network;

  public TileEnergyNetwork(final TileEntityType type){
    super(type);
  }

  @Override
  public void tick(){
    if(onServerSide()){
      if(network == null){
        BlockNetworkUtil.create_or_join(world, this, EnergyNetwork::new);
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
  @Nullable
  public EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public void setBlockNetwork(EnergyNetwork network){
    this.network = network;
  }

}
