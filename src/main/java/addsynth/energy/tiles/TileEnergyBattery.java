package addsynth.energy.tiles;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.EnergyNetwork;
import addsynth.energy.EnergyNode;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

/**
 *  The base class is a TileEnergyWithStorage, which gives it a CustomEnergyStorage capability. Making it extend
 *  from TileEnergyTransmitter will ensure it automatically attempts to transmit energy to machines, but it also
 *  sets it to Transmit only, which is why after calling the super Constructor we set the transfer rate of the energy storage.
 */
public abstract class TileEnergyBattery extends TileEnergyTransmitter {

  public TileEnergyBattery(final TileEntityType type, final CustomEnergyStorage energy){
    super(type, energy);
  }

  public TileEnergyBattery(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy) {
    super(type, input_slots, filter, output_slots, energy);
  }

  /**
   * This overrides the getMachines method in the TileEnergyTransmitter class to prevent from
   * transmitting into other TileEnergyBatteries. But other Energy Transmitters are allowed to do that.
   */
  @Override
  protected final void get_machines_that_need_energy(){
    machines.clear();
    getNetworks();
    TileEnergyWithStorage tile;
    if(networks.size() > 0){
      for(EnergyNetwork network : networks){
        for(EnergyNode node : network.receivers){
          tile = node.getTile();
          if(tile instanceof TileEnergyBattery == false){
            if(tile.needsEnergy()){
              machines.add(tile);
            }
          }
        }
      }
    }
  }

}
