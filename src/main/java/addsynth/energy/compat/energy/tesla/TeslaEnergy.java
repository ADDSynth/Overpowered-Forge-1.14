package addsynth.energy.compat.energy.tesla;

import addsynth.core.util.JavaUtils;
// import net.darkhax.tesla.api.*;
// import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.tileentity.TileEntity;

public final class TeslaEnergy {

  public static final boolean check(final TileEntity tile){
    // MAYBE: And then I rediscovered the existance of Forge's CapabilityInjector annotation.
    /*
    if(JavaUtils.classExists("net.darkhax.tesla.capability.TeslaCapabilities")){
      if(tile.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, capability_side)){
        energy = tile.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, capability_side);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
          continue;
        }
      }
      if(tile.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, capability_side)){
        energy = tile.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, capability_side);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
          continue;
        }
      }
      if(tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, capability_side)){
        energy = tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, capability_side);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
          continue;
        }
      }
    }
    */
    return false;
  }

  public static final int get(final Object input, final int energy_requested, final boolean simulate){
    // if(input instanceof ITeslaProducer){
    //   final ITeslaProducer energy = (ITeslaProducer)input;
    //   return JavaUtils.cast_to_int(energy.takePower(energy_requested, simulate));
    // }
    return 0;
  }

  public static final int send(final Object input, final int transmitted_energy){
    // if(input instanceof ITeslaConsumer){
    //   final ITeslaConsumer energy = (ITeslaConsumer)input;
    //   return JavaUtils.cast_to_int(energy.givePower(transmitted_energy, false));
    // }
    return 0;
  }

}
