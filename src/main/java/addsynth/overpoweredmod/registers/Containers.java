package addsynth.overpoweredmod.registers;

import addsynth.energy.gameplay.compressor.ContainerCompressor;
import addsynth.energy.gameplay.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.universal_energy_interface.ContainerUniversalInterface;
import addsynth.overpoweredmod.OverpoweredMod;
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
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public final class Containers {

  public static final ContainerType<ContainerGenerator> GENERATOR = register("generator", ContainerGenerator::new);
  public static final ContainerType<ContainerEnergyStorage> ENERGY_STORAGE_CONTAINER = register("energy_storage", ContainerEnergyStorage::new);
  public static final ContainerType<ContainerUniversalInterface> UNIVERSAL_ENERGY_INTERFACE = register("universal_energy_interface", ContainerUniversalInterface::new);

  public static final ContainerType<ContainerCompressor> COMPRESSOR = register("compressor", ContainerCompressor::new);
  public static final ContainerType<ContainerElectricFurnace> ELECTRIC_FURNACE = register("electric_furnace", ContainerElectricFurnace::new);
  public static final ContainerType<ContainerInverter> INVERTER = register("inverter", ContainerInverter::new);
  public static final ContainerType<ContainerGemConverter> GEM_CONVERTER = register("gem_converter", ContainerGemConverter::new);
  public static final ContainerType<ContainerMagicInfuser> MAGIC_INFUSER = register("magic_infuser", ContainerMagicInfuser::new);
  public static final ContainerType<ContainerIdentifier> IDENTIFIER = register("identifier", ContainerIdentifier::new);
  public static final ContainerType<ContainerOreRefinery> ADVANCED_ORE_REFINERY = register("advanced_ore_refinery", ContainerOreRefinery::new);
  public static final ContainerType<ContainerCrystalGenerator> CRYSTAL_MATTER_GENERATOR = register("crystal_matter_generator", ContainerCrystalGenerator::new);
  
  public static final ContainerType<ContainerPortalControlPanel> PORTAL_CONTROL_PANEL = register("portal_control_panel", ContainerPortalControlPanel::new);
  public static final ContainerType<ContainerPortalFrame> PORTAL_FRAME = register("portal_frame", ContainerPortalFrame::new);

  public static final ContainerType<ContainerLaserHousing> LASER_HOUSING = register("laser_housing", ContainerLaserHousing::new);

  public static final ContainerType<ContainerFusionChamber> FUSION_CHAMBER = register("fusion_chamber", ContainerFusionChamber::new);

  private static final <T extends Container> ContainerType<T> register(final String name, final ContainerType.IFactory<T> factory){
    final ContainerType<T> container = new ContainerType<>(factory);
    container.setRegistryName(OverpoweredMod.MOD_ID, name);
    return container;
  }

}
