package addsynth.energy.registers;

import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.TileCircuitFabricator;
import addsynth.energy.gameplay.machines.compressor.TileCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.machines.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.machines.generator.TileGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyTransfer;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileEnergyWire> ENERGY_WIRE =
    TileEntityType.Builder.create(TileEnergyWire::new, EnergyBlocks.wire).build(null);

  public static final TileEntityType<TileGenerator> GENERATOR =
    TileEntityType.Builder.create(TileGenerator::new, EnergyBlocks.generator).build(null);

  public static final TileEntityType<TileCompressor> COMPRESSOR =
    TileEntityType.Builder.create(TileCompressor::new, EnergyBlocks.compressor).build(null);

  public static final TileEntityType<TileEnergyStorage> ENERGY_CONTAINER =
    TileEntityType.Builder.create(TileEnergyStorage::new, EnergyBlocks.energy_storage).build(null);

  public static final TileEntityType<TileUniversalEnergyTransfer> UNIVERSAL_ENERGY_INTERFACE =
    TileEntityType.Builder.create(TileUniversalEnergyTransfer::new, EnergyBlocks.universal_energy_machine).build(null);

  public static final TileEntityType<TileElectricFurnace> ELECTRIC_FURNACE =
    TileEntityType.Builder.create(TileElectricFurnace::new, EnergyBlocks.electric_furnace).build(null);

  public static final TileEntityType<TileCircuitFabricator> CIRCUIT_FABRICATOR =
    TileEntityType.Builder.create(TileCircuitFabricator::new, EnergyBlocks.circuit_fabricator).build(null);

}
