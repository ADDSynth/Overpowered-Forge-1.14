package addsynth.energy.gameplay;

import addsynth.energy.gameplay.circuit_fabricator.CircuitFabricatorBlock;
import addsynth.energy.gameplay.compressor.Compressor;
import addsynth.energy.gameplay.electric_furnace.ElectricFurnace;
import addsynth.energy.gameplay.energy_storage.EnergyStorageBlock;
import addsynth.energy.gameplay.energy_wire.EnergyWire;
import addsynth.energy.gameplay.generator.GeneratorBlock;
import addsynth.energy.gameplay.universal_energy_interface.UniversalEnergyBlock;

public final class EnergyBlocks {

  public static final EnergyWire              wire                     = new EnergyWire("energy_wire");
  public static final GeneratorBlock          generator                = new GeneratorBlock("generator");
  public static final EnergyStorageBlock      energy_storage           = new EnergyStorageBlock("energy_storage");
  public static final Compressor              compressor               = new Compressor("compressor");
  public static final ElectricFurnace         electric_furnace         = new ElectricFurnace("electric_furnace");
  public static final CircuitFabricatorBlock  circuit_fabricator       = new CircuitFabricatorBlock("circuit_fabricator");
  public static final UniversalEnergyBlock    universal_energy_machine = new UniversalEnergyBlock("universal_energy_interface");

}
