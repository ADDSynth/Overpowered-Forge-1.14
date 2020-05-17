package addsynth.energy.energy_network.tiles;

import addsynth.energy.Energy;
import addsynth.energy.tiles.IEnergyUser;
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
    this.energy.setResponder(this);
  }

  @Override
    public void tick(){
      super.tick(); // handles blocknetwork stuff
      energy.update(world);
    }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(energy != null){
      energy.readFromNBT(nbt);
    }
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(energy != null){
      energy.writeToNBT(nbt);
    }
    return nbt;
  }

  @Override
  public final Energy getEnergy(){
    return energy;
  }

  @Override
  public void onEnergyChanged(){
    update_data();
  }

}
