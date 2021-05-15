package addsynth.energy.registers;

import addsynth.energy.gameplay.circuit_fabricator.CircuitFabricatorContainer;
import addsynth.energy.gameplay.compressor.ContainerCompressor;
import addsynth.energy.gameplay.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.generator.ContainerGenerator;
import addsynth.energy.gameplay.universal_energy_interface.ContainerUniversalInterface;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public final class Containers {

  public static final ContainerType<ContainerGenerator> GENERATOR =
    new ContainerType<>((IContainerFactory<ContainerGenerator>)ContainerGenerator::new);

  public static final ContainerType<ContainerCompressor> COMPRESSOR =
    new ContainerType<>((IContainerFactory<ContainerCompressor>)ContainerCompressor::new);

  public static final ContainerType<ContainerEnergyStorage> ENERGY_STORAGE_CONTAINER =
    new ContainerType<>((IContainerFactory<ContainerEnergyStorage>)ContainerEnergyStorage::new);

  public static final ContainerType<ContainerUniversalInterface> UNIVERSAL_ENERGY_INTERFACE =
    new ContainerType<>((IContainerFactory<ContainerUniversalInterface>)ContainerUniversalInterface::new);

  public static final ContainerType<ContainerElectricFurnace> ELECTRIC_FURNACE =
    new ContainerType<>((IContainerFactory<ContainerElectricFurnace>)ContainerElectricFurnace::new);

  public static final ContainerType<CircuitFabricatorContainer> CIRCUIT_FABRICATOR =
    new ContainerType<>((IContainerFactory<CircuitFabricatorContainer>)CircuitFabricatorContainer::new);

}
