package addsynth.energy.main;

import java.util.ArrayList;
import addsynth.core.util.math.DecimalNumber;
import addsynth.core.util.math.MathUtility;
import addsynth.energy.energy_network.EnergyNode;
import net.minecraft.tileentity.TileEntity;

public final class EnergyUtil {

  public static final void transfer_energy(final ArrayList<EnergyNode> from, final ArrayList<EnergyNode> to){
  
    int i;
    EnergyNode node;
    TileEntity tile;
    long energy;
  
    final int from_size = from.size();
    final int to_size   = to.size();

    final long[] available_energy = new long[from_size];
    final long[] requested_energy = new long[to_size];
    long total_available_energy = 0;
    long total_requested_energy = 0;
    
    // Part 1: Collect Generator info
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      // TODO: I always forget one thing! What if a Generator is connected to 2 or more Energy Networks?
      //       the first network will attempt to drain all the energy from it. Must call a function with
      //       the Generator TileEntity to see how many Energy Networks are connected then divide the
      //       available energy based on how many networks it's connected to.
      //       Cache references? Instead of counting Energy Networks each tick, have Generators
      //       keep a list of Energy Networks they are connected to. Have the Energy Network update
      //       update its entry in the Generator's list every time the Energy Network updates.
      energy = (long)(node.energy.getAvailableEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      available_energy[i] = energy;
      total_available_energy += energy;
    }
    
    // Part 2: Collect Receiver info
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      tile = node.getTile();
      if(tile instanceof IEnergyConsumer){
        energy = (long)(((IEnergyConsumer)tile).getNeededEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        energy = (long)(node.energy.getRequestedEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      requested_energy[i] = energy;
      total_requested_energy += energy;
    }
    
    // Part 3a: Determine energy to transfer
    final long energy_to_transfer = Math.min(total_available_energy, total_requested_energy);
    if(energy_to_transfer == 0){
      return;
    }
    final long[] energy_to_extract = MathUtility.divide_evenly(energy_to_transfer, available_energy);
    final long[] energy_to_receive = MathUtility.divide_evenly(energy_to_transfer, requested_energy);
    
    // Part 3b: Extract energy from Generators
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      node.energy.extractEnergy((double)energy_to_extract[i] / DecimalNumber.DECIMAL_ACCURACY);
    }
    
    // Part 3c: Insert energy into Receivers
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      tile = node.getTile();
      if(tile instanceof IEnergyConsumer){
        ((IEnergyConsumer)tile).receiveEnergy((double)energy_to_receive[i] / DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        node.energy.receiveEnergy((double)energy_to_receive[i] / DecimalNumber.DECIMAL_ACCURACY);
      }
    }
  }

  public static final void balance_batteries(final ArrayList<EnergyNode> batteries){
    balance_batteries(batteries, 0);
  }
  
  public static final void balance_batteries(final ArrayList<EnergyNode> batteries, final long removed_battery_energy){

    int i;
    final int length = batteries.size();
    Energy energy_storage;

    long total_energy = removed_battery_energy;
    long total_capacity = 0;
    final long[] capacity = new long[length];

    for(i = 0; i < length; i++){
      energy_storage = batteries.get(i).energy;
      total_energy   += (long)(energy_storage.getEnergy()   * DecimalNumber.DECIMAL_ACCURACY);
      capacity[i]     = (long)(energy_storage.getCapacity() * DecimalNumber.DECIMAL_ACCURACY);
      total_capacity += capacity[i];
    }
    
    if(total_energy == total_capacity){
      return;
    }
    
    final long[] energy_to_insert = MathUtility.divide_evenly(total_energy, capacity);
    
    for(i = 0; i < length; i++){
      energy_storage = batteries.get(i).energy;
      energy_storage.setEnergy((double)energy_to_insert[i] / DecimalNumber.DECIMAL_ACCURACY);
    }
  }

}
