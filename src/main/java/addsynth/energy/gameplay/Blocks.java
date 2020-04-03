package addsynth.energy.gameplay;

import addsynth.energy.gameplay.electric_furnace.ElectricFurnace;
import addsynth.energy.gameplay.energy_storage.EnergyStorageBlock;
import addsynth.energy.gameplay.energy_wire.EnergyWire;
import addsynth.energy.gameplay.universal_energy_interface.UniversalEnergyBlock;

public final class Blocks {

  public static final EnergyWire              wire                     = new EnergyWire("energy_wire");
  public static final EnergyStorageBlock      energy_storage           = new EnergyStorageBlock("energy_storage");
  public static final UniversalEnergyBlock    universal_energy_machine = new UniversalEnergyBlock("universal_energy_interface");
  public static final ElectricFurnace         electric_furnace         = new ElectricFurnace("electric_furnace");
}
