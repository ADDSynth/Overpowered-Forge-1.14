package addsynth.energy.gameplay.tiles;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.config.Values;

public final class TileEnergyStorage extends TileEnergyBattery {

  public TileEnergyStorage(){
    super(new CustomEnergyStorage(Values.energy_storage_container_capacity,Values.energy_storage_container_extract_rate));
  }

}
