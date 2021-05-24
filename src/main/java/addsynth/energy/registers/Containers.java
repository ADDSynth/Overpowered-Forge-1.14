package addsynth.energy.registers;

import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorContainer;
import addsynth.energy.gameplay.machines.compressor.ContainerCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.machines.generator.ContainerGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.ContainerUniversalEnergyInterface;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public final class Containers {

  public static final ContainerType<ContainerGenerator> GENERATOR =
    new ContainerType<>((IContainerFactory<ContainerGenerator>)ContainerGenerator::new);

  public static final ContainerType<ContainerCompressor> COMPRESSOR =
    new ContainerType<>((IContainerFactory<ContainerCompressor>)ContainerCompressor::new);

  public static final ContainerType<ContainerEnergyStorage> ENERGY_STORAGE_CONTAINER =
    new ContainerType<>((IContainerFactory<ContainerEnergyStorage>)ContainerEnergyStorage::new);

  public static final ContainerType<ContainerUniversalEnergyInterface> UNIVERSAL_ENERGY_INTERFACE =
    new ContainerType<>((IContainerFactory<ContainerUniversalEnergyInterface>)ContainerUniversalEnergyInterface::new);

  public static final ContainerType<ContainerElectricFurnace> ELECTRIC_FURNACE =
    new ContainerType<>((IContainerFactory<ContainerElectricFurnace>)ContainerElectricFurnace::new);

  public static final ContainerType<CircuitFabricatorContainer> CIRCUIT_FABRICATOR =
    new ContainerType<>((IContainerFactory<CircuitFabricatorContainer>)CircuitFabricatorContainer::new);

}
