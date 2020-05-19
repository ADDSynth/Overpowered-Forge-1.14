package addsynth.energy;

import java.util.ArrayList;
import addsynth.core.util.DecimalNumber;
import addsynth.core.util.MathUtility;
import addsynth.energy.energy_network.EnergyNode;
import addsynth.energy.tiles.machines.TileWorkMachine;
import net.minecraft.tileentity.TileEntity;

public final class EnergyUtil {

  public static final void transfer_energy(final ArrayList<EnergyNode> from, final ArrayList<EnergyNode> to){

    int i;
    EnergyNode node;
    TileEntity tile;
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
      tile = node.getTile();
      if(tile instanceof TileWorkMachine){
        energy = (long)(((TileWorkMachine)tile).getNeededEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        energy = (long)(node.energy.getRequestedEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      receivers[i] = energy;
      total_energy_requested += energy;
    }

    // Determine Transfer info
    final long energy_to_transfer = Math.min(total_energy_available, total_energy_requested);
    if(energy_to_transfer == 0){
      return;
    }
    final long[] energy_to_extract = MathUtility.divide_evenly(energy_to_transfer, generators);
    final long[] energy_to_insert = MathUtility.divide_evenly(energy_to_transfer, receivers);

    // Extract Energy
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      node.energy.extractEnergy(((double)energy_to_extract[i]) / DecimalNumber.DECIMAL_ACCURACY);
    }

    // Insert as evenly as possible into all the Receiving machines
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      tile = node.getTile();
      if(tile instanceof TileWorkMachine){
        ((TileWorkMachine)tile).receiveEnergy(((double)energy_to_insert[i]) / DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        node.energy.receiveEnergy(((double)energy_to_insert[i]) / DecimalNumber.DECIMAL_ACCURACY);
      }
    }
  }

}
