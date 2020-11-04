package addsynth.energy.energy_network;

import java.util.ArrayList;
import addsynth.core.util.math.DecimalNumber;
import addsynth.core.util.math.MathUtility;
import addsynth.energy.main.Energy;
import addsynth.energy.main.ICustomEnergyUser;
import net.minecraft.tileentity.TileEntity;

/*
  I could either transfer all energy all at once, but consider the following:
  1 Generator, generates 500 energy.
  1 Battery, can receive and extract 500 energy.
  1 Machine, requests 22 energy.
  Total Receive = 522, total Extract = 1000
  But receive is the lower number so we use that.
  Therefore, 522 gets divided amongst all energy producing machines.
  2 machines produce energy. 261 get extracted from the Generator, and 261 from the battery.
  Energy is NOT transferred from the Generator to the batteries.
  
  Therefore, you MUST use a 3-way system:
  First transfer energy from generators to the receivers.
  Second, if receivers still need energy, transfer it from batteries.
  Lastly, transfer remaining energy from Generators from batteries.
  But you run into another problem:
  1 Generator produces 100, and 1 Battery produces 100.
  1 Machine requests 5 energy per tick.
  First the generator transfers 5 energy to the machine.
  But then, the batteries can ALSO transfer 5 energy to the machine, so
  it gets 10 Energy in total for this tick.
  
  Since the second method is best, we use that. But must figure out a way to
  check if the machine has already received its energy for this tick.
*/

public final class EnergyUtil {

  public static final void transfer_energy(final ArrayList<EnergyNode> from, final ArrayList<EnergyNode> to){
  
    int i;
    EnergyNode node;
    TileEntity tile;
  
    final int from_size = from.size();
    final int to_size   = to.size();

    final long[] available_energy = new long[from_size];
    final long[] requested_energy = new long[to_size];
    long total_available_energy = 0;
    long total_requested_energy = 0;
    
    // TODO: I always forget one thing! What if a Generator is connected to 2 or more Energy Networks?
    //       the first network will attempt to drain all the energy from it. Must call a function with
    //       the Generator TileEntity to see how many Energy Networks are connected then divide the
    //       available energy based on how many networks it's connected to.
    //       Cache references? Instead of counting Energy Networks each tick, have Generators
    //       keep a list of Energy Networks they are connected to. Have the Energy Network update
    //       update its entry in the Generator's list every time the Energy Network updates.

    // Part 1a: Collect Generator Info
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      tile = node.getTile();

      if(tile instanceof ICustomEnergyUser){
        available_energy[i] = (long)(((ICustomEnergyUser)tile).getAvailableEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        available_energy[i] = (long)(node.energy.getAvailableEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      total_available_energy += available_energy[i];
    }
    
    // Part 1b: Collect Receiver Info
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      tile = node.getTile();

      if(tile instanceof ICustomEnergyUser){
        requested_energy[i] = (long)(((ICustomEnergyUser)tile).getRequestedEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      else{
        requested_energy[i] = (long)(node.energy.getRequestedEnergy() * DecimalNumber.DECIMAL_ACCURACY);
      }
      total_requested_energy += requested_energy[i];
    }
    
    // Part 2: Determine energy to transfer
    final long energy_to_transfer = Math.min(total_available_energy, total_requested_energy);
    if(energy_to_transfer == 0){
      return;
    }
    final long[] energy_to_extract = MathUtility.divide_evenly(energy_to_transfer, available_energy);
    final long[] energy_to_receive = MathUtility.divide_evenly(energy_to_transfer, requested_energy);
    
    // Part 3a: Extract energy from Generators
    for(i = 0; i < from_size; i++){
      node = from.get(i);
      node.energy.extractEnergy((double)energy_to_extract[i] / DecimalNumber.DECIMAL_ACCURACY);
    }
    
    // Part 3b: Insert energy into Receivers
    for(i = 0; i < to_size; i++){
      node = to.get(i);
      node.energy.receiveEnergy((double)energy_to_receive[i] / DecimalNumber.DECIMAL_ACCURACY);
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
