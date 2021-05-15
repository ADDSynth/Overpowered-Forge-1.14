package addsynth.energy.api.tiles.energy;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.energy.api.energy_network.EnergyNetwork;
import addsynth.energy.api.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.api.main.Energy;
import addsynth.energy.api.main.IEnergyUser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** TileEntities that act as standard Batteries that should be part of the Energy Network
 *  should extend from this class.
 */
public abstract class TileEnergyBattery extends AbstractEnergyNetworkTile implements IEnergyUser {

  protected final Energy energy;

  public TileEnergyBattery(final TileEntityType type, final Energy energy){
    super(type);
    this.energy = energy;
  }

  @Override
  public void tick(){
    if(onServerSide()){
      if(network == null){
        BlockNetworkUtil.create_or_join(world, this, EnergyNetwork::new);
      }
      network.tick(this);
      if(energy.tick()){
        update_data();
      }
    }
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(energy != null){
      energy.loadFromNBT(nbt);
    }
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(energy != null){
      energy.saveToNBT(nbt);
    }
    return nbt;
  }

  @Override
  public final void remove(){
    super.remove();
    if(onServerSide()){
      network.drain_battery(energy);
      BlockNetworkUtil.tileentity_was_removed(this, EnergyNetwork::new);
    }
  }

  @Override
  public final void setBlockNetwork(final EnergyNetwork network){
    this.network = network;
  }

  @Override
  public final @Nullable EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final Energy getEnergy(){
    return energy;
  }

}
