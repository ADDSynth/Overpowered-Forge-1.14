package addsynth.overpoweredmod.registers;

import addsynth.energy.gameplay.compressor.ContainerCompressor;
import addsynth.energy.gameplay.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.universal_energy_interface.ContainerUniversalInterface;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.ContainerOreRefinery;
import addsynth.overpoweredmod.machines.crystal_matter_generator.ContainerCrystalGenerator;
import addsynth.overpoweredmod.machines.fusion.chamber.ContainerFusionChamber;
import addsynth.overpoweredmod.machines.gem_converter.ContainerGemConverter;
import addsynth.overpoweredmod.machines.generator.ContainerGenerator;
import addsynth.overpoweredmod.machines.identifier.ContainerIdentifier;
import addsynth.overpoweredmod.machines.inverter.ContainerInverter;
import addsynth.overpoweredmod.machines.laser.machine.ContainerLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.ContainerMagicInfuser;
import addsynth.overpoweredmod.machines.portal.control_panel.ContainerPortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.ContainerPortalFrame;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public final class Containers {

  public static final ContainerType<ContainerGenerator> GENERATOR =
    new ContainerType<>((IContainerFactory<ContainerGenerator>)ContainerGenerator::new);

  public static final ContainerType<ContainerEnergyStorage> ENERGY_STORAGE_CONTAINER =
    new ContainerType<>((IContainerFactory<ContainerEnergyStorage>)ContainerEnergyStorage::new);

  public static final ContainerType<ContainerUniversalInterface> UNIVERSAL_ENERGY_INTERFACE =
    new ContainerType<>((IContainerFactory<ContainerUniversalInterface>)ContainerUniversalInterface::new);

  public static final ContainerType<ContainerCompressor> COMPRESSOR =
    new ContainerType<>((IContainerFactory<ContainerCompressor>)ContainerCompressor::new);

  public static final ContainerType<ContainerElectricFurnace> ELECTRIC_FURNACE =
    new ContainerType<>((IContainerFactory<ContainerElectricFurnace>)ContainerElectricFurnace::new);

  public static final ContainerType<ContainerInverter> INVERTER =
    new ContainerType<>((IContainerFactory<ContainerInverter>)ContainerInverter::new);

  public static final ContainerType<ContainerGemConverter> GEM_CONVERTER =
    new ContainerType<>((IContainerFactory<ContainerGemConverter>)ContainerGemConverter::new);

  public static final ContainerType<ContainerMagicInfuser> MAGIC_INFUSER =
    new ContainerType<>((IContainerFactory<ContainerMagicInfuser>)ContainerMagicInfuser::new);

  public static final ContainerType<ContainerIdentifier> IDENTIFIER =
    new ContainerType<>((IContainerFactory<ContainerIdentifier>)ContainerIdentifier::new);

  public static final ContainerType<ContainerOreRefinery> ADVANCED_ORE_REFINERY =
    new ContainerType<>((IContainerFactory<ContainerOreRefinery>)ContainerOreRefinery::new);

  public static final ContainerType<ContainerCrystalGenerator> CRYSTAL_MATTER_GENERATOR =
    new ContainerType<>((IContainerFactory<ContainerCrystalGenerator>)ContainerCrystalGenerator::new);
  
  public static final ContainerType<ContainerPortalControlPanel> PORTAL_CONTROL_PANEL =
    new ContainerType<>((IContainerFactory<ContainerPortalControlPanel>)ContainerPortalControlPanel::new);

  public static final ContainerType<ContainerPortalFrame> PORTAL_FRAME =
    new ContainerType<>((IContainerFactory<ContainerPortalFrame>)ContainerPortalFrame::new);

  public static final ContainerType<ContainerLaserHousing> LASER_HOUSING =
    new ContainerType<>((IContainerFactory<ContainerLaserHousing>)ContainerLaserHousing::new);

  public static final ContainerType<ContainerFusionChamber> FUSION_CHAMBER =
    new ContainerType<>((IContainerFactory<ContainerFusionChamber>)ContainerFusionChamber::new);

}
