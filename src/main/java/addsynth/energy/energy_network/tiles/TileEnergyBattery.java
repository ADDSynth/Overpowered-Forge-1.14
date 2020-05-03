package addsynth.energy.energy_network.tiles;

import addsynth.energy.Energy;
import addsynth.energy.tiles.IEnergyUser;
import net.minecraft.tileentity.TileEntityType;

/**
 */
public abstract class TileEnergyBattery extends TileEnergyNetwork implements IEnergyUser {

  protected final Energy energy;

  public TileEnergyBattery(final TileEntityType type, final Energy energy){
    super(type);
    this.energy = energy;
  }

  @Override
  public final Energy getEnergy(){
    return energy;
  }

}
