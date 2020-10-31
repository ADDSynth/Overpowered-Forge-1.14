package addsynth.energy.tiles.energy;

import addsynth.energy.energy_network.tiles.TileEnergyNetwork;
import addsynth.energy.main.Energy;
import addsynth.energy.main.IEnergyUser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** TileEntities that act as standard Batteries that should be part of the Energy Network
 *  should extend from this class.
 */
public abstract class TileEnergyBattery extends TileEnergyNetwork implements IEnergyUser {

  protected final Energy energy;

  public TileEnergyBattery(final TileEntityType type, final Energy energy){
    super(type);
    this.energy = energy;
  }

  @Override
  public void tick(){
    super.tick(); // handles blocknetwork stuff
    if(onServerSide()){
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
  public final Energy getEnergy(){
    return energy;
  }

}
