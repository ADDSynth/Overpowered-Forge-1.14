package addsynth.energy.compat.energy.forge;

import javax.annotation.Nonnull;
import addsynth.energy.lib.main.Energy;
import net.minecraftforge.energy.IEnergyStorage;

/** Accepts an {@link Energy} object and automatically calls our
 *  Energy functions in place of Forge's. Converts values seamlessly.
 *  Use this in your TileEntity's <code>getCapability()</code>
 *  function to allow other mods to use this as a compatible machine.
 * @author ADDSynth
 */
public class ForgeEnergyIntermediary implements IEnergyStorage {

  final Energy energy;

  public ForgeEnergyIntermediary(@Nonnull final Energy energy){
    this.energy = energy;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate){
    final int actual_energy = (int)energy.simulateReceive(maxReceive);
    if(simulate == false){
      energy.receiveEnergy(actual_energy);
    }
    return actual_energy;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate){
    final int actual_energy = (int)energy.simulateExtract(maxExtract);
    if(simulate == false){
      energy.extractEnergy(actual_energy);
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
