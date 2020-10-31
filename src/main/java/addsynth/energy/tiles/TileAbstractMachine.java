package addsynth.energy.tiles;

import addsynth.core.tiles.TileBase;
import addsynth.energy.main.Energy;
import addsynth.energy.main.IEnergyConsumer;
import addsynth.energy.main.Receiver;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/** All machines that only receive energy to do work derive from this class. */
public abstract class TileAbstractMachine extends TileBase implements ITickableTileEntity, IEnergyConsumer {

  protected final Receiver energy;

  public TileAbstractMachine(final TileEntityType type, final Receiver energy){
    super(type);
    this.energy = energy;
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(energy != null){ energy.loadFromNBT(nbt);}
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(energy != null){ energy.saveToNBT(nbt);}
    return nbt;
  }

  @Override
  public Energy getEnergy(){
    return energy;
  }

  @Override
  public double getNeededEnergy(){
    return energy.getRequestedEnergy();
  }

  @Override
  public void receiveEnergy(double add_energy){
    energy.receiveEnergy(add_energy);
  }

}
