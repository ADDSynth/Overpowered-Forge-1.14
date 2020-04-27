package addsynth.energy.energy_network.tiles;

import addsynth.energy.Energy;
import net.minecraft.tileentity.TileEntityType;

/**
 */
public abstract class TileEnergyBattery extends TileEnergyNetwork {

  protected final Energy energy;

  public TileEnergyBattery(final TileEntityType type, final Energy energy){
    super(type);
    this.energy = energy;
  }

  public final Energy getEnergy(){
    return energy;
  }

}
