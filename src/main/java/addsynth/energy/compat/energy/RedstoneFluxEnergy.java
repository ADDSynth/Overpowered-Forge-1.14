package addsynth.energy.compat.energy;

import addsynth.core.util.JavaUtils;
// import cofh.redstoneflux.api.IEnergyHandler;
// import cofh.redstoneflux.api.IEnergyProvider;
// import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public final class RedstoneFluxEnergy {

  public static final boolean check(final TileEntity tile){
    /*
    if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyHandler")){
      if(tile instanceof IEnergyHandler){
        return true;
      }
    }
    if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyReceiver")){
      if(tile instanceof IEnergyReceiver){
        return true;
      }
    }
    if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyProvider")){
      if(tile instanceof IEnergyProvider){
        return true;
      }
    }
    */
    return false;
  }

  public static final int get(final Object input, final int energy_requested, final boolean simulate, final Direction side){
    // if(input instanceof IEnergyProvider){
    //   final IEnergyProvider energy = (IEnergyProvider)input;
    //   return energy.extractEnergy(side, energy_requested, simulate);
    // }
    return 0;
  }

  public static final int send(final Object input, final int transmitted_energy, final Direction side){
    // if(input instanceof IEnergyReceiver){
    //   final IEnergyReceiver energy = (IEnergyReceiver)input;
    //   return energy.receiveEnergy(side, transmitted_energy, false);
    // }
    return 0;
  }
  
}
