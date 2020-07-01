package addsynth.energy.compat.energy.forge;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class ForgeEnergy {

  public static final boolean check(final TileEntity tile, final Direction capability_side){
    return tile.getCapability(CapabilityEnergy.ENERGY, capability_side).orElseGet(null) != null;
  }

  public static final int get(final Object input, final int energy_requested, final boolean simulate){
    final IEnergyStorage energy = (IEnergyStorage)input;
    if(energy.canExtract()){
      return energy.extractEnergy(energy_requested, simulate);
    }
    return 0;
  }

  public static final int send(final Object input, final int transmitted_energy){
    final IEnergyStorage energy = (IEnergyStorage)input;
    if(energy.canReceive()){
      return energy.receiveEnergy(transmitted_energy, false);
    }
    return 0;
  }
  
}
