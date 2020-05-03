package addsynth.energy;

import java.util.ArrayList;
import addsynth.core.util.DecimalNumber;
import addsynth.core.util.MathUtility;
import addsynth.energy.energy_network.EnergyNode;

public final class EnergyUtil {

  public static final void transfer_energy(final ArrayList<EnergyNode> from, final ArrayList<EnergyNode> to){

    int i;
    EnergyNode node;
    long energy;

    final int from_size = from.size();
    final int to_size   = to.size();

    // Collect Generator info
    long[] generators = new long[from_size];
    long total_energy_available = 0;
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      energy = (long)(node.energy.getAvailableEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      generators[i] = energy;
      total_energy_available += energy;
    }
    
    // Collect Receiver info
    long[] receivers = new long[to_size];
    long total_energy_requested = 0;
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      energy = (long)(node.energy.getRequestedEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      receivers[i] = energy;
      total_energy_requested += energy;
    }

    // Determine Transfer info
    final long energy_to_transfer = Math.min(total_energy_available, total_energy_requested);
    if(energy_to_transfer == 0){
      return;
    }
    final double ratio = energy_to_transfer < total_energy_available ? (double)energy_to_transfer / total_energy_available : 1.0;    

    // Extract Energy
    double total_energy = 0;
    for(i = 0; i < from_size; i++){
      energy = (long)((double)generators[i] * ratio); // long, multiplied by ratio, then truncated.
      node = from.get(i);
      total_energy += node.energy.extractEnergy((double)energy / DecimalNumber.DECIMAL_ACCURACY);
    }

    // Insert as evenly as possible into all the Receiving machines
    total_energy_available = (long)(total_energy * DecimalNumber.DECIMAL_ACCURACY);
    long[] energy_to_insert = MathUtility.divide_evenly(total_energy_available, receivers);
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      node.energy.receiveEnergy(((double)energy_to_insert[i]) / DecimalNumber.DECIMAL_ACCURACY);
    }
  }

}
