package addsynth.energy.gameplay;

import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorBlock;
import addsynth.energy.gameplay.machines.compressor.Compressor;
import addsynth.energy.gameplay.machines.electric_furnace.ElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.EnergyStorageBlock;
import addsynth.energy.gameplay.machines.energy_wire.EnergyWire;
import addsynth.energy.gameplay.machines.generator.GeneratorBlock;
import addsynth.energy.gameplay.machines.universal_energy_interface.UniversalEnergyBlock;

public final class EnergyBlocks {

  public static final EnergyWire              wire                     = new EnergyWire("energy_wire");
  public static final GeneratorBlock          generator                = new GeneratorBlock("generator");
  public static final EnergyStorageBlock      energy_storage           = new EnergyStorageBlock("energy_storage");
  public static final Compressor              compressor               = new Compressor("compressor");
  public static final ElectricFurnace         electric_furnace         = new ElectricFurnace("electric_furnace");
  public static final CircuitFabricatorBlock  circuit_fabricator       = new CircuitFabricatorBlock("circuit_fabricator");
  public static final UniversalEnergyBlock    universal_energy_machine = new UniversalEnergyBlock("universal_energy_interface");

}
