package addsynth.energy.gameplay.tiles;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.tiles.Tiles;

public final class TileEnergyStorage extends TileEnergyBattery {

  public TileEnergyStorage(){
    super(Tiles.ENERGY_CONTAINER, new CustomEnergyStorage(Values.energy_storage_container_capacity.get(),Values.energy_storage_container_extract_rate.get()));
  }

}
