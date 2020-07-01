package addsynth.energy.compat.energy.forge;

import addsynth.energy.Energy;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgeEnergyIntermediary implements IEnergyStorage {

  final Energy energy;

  public ForgeEnergyIntermediary(final Energy energy){
    this.energy = energy;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate){
    final int actual_energy = (int)energy.simulateReceive(maxReceive);
    if(simulate == false){
      energy.receiveEnergy(maxReceive);
    }
    return actual_energy;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate){
    final int actual_energy = (int)energy.simulateReceive(maxExtract);
    if(simulate == false){
      energy.receiveEnergy(maxExtract);
    }
    return actual_energy;
  }

  @Override
  public int getEnergyStored(){
    return (int)energy.getEnergy();
  }

  @Override
  public int getMaxEnergyStored(){
    return (int)energy.getCapacity();
  }

  @Override
  public boolean canExtract(){
    return energy.canExtract();
  }

  @Override
  public boolean canReceive(){
    return energy.canReceive();
  }

}
