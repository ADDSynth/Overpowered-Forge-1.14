package addsynth.overpoweredmod.tiles;

import addsynth.energy.gameplay.tiles.*;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.tiles.machines.automatic.*;
import addsynth.overpoweredmod.tiles.machines.energy.TileEnergyGenerator;
import addsynth.overpoweredmod.tiles.machines.fusion.*;
import addsynth.overpoweredmod.tiles.machines.laser.*;
import addsynth.overpoweredmod.tiles.machines.other.*;
import addsynth.overpoweredmod.tiles.machines.portal.*;
import addsynth.overpoweredmod.tiles.technical.*;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileEnergyWire> ENERGY_WIRE =
    TileEntityType.Builder.create(TileEnergyWire::new, Wires.wire).build(null);

  public static final TileEntityType<TileEnergyGenerator> GENERATOR =
    TileEntityType.Builder.create(TileEnergyGenerator::new, Machines.generator).build(null);

  public static final TileEntityType<TileEnergyStorage> ENERGY_CONTAINER =
    TileEntityType.Builder.create(TileEnergyStorage::new, Machines.energy_storage).build(null);

  public static final TileEntityType<TileUniversalEnergyTransfer> UNIVERSAL_ENERGY_INTERFACE =
    TileEntityType.Builder.create(TileUniversalEnergyTransfer::new, Machines.universal_energy_machine).build(null);

  public static final TileEntityType<TileCompressor> COMPRESSOR =
    TileEntityType.Builder.create(TileCompressor::new, Machines.compressor).build(null);

  public static final TileEntityType<TileElectricFurnace> ELECTRIC_FURNACE =
    TileEntityType.Builder.create(TileElectricFurnace::new, Machines.electric_furnace).build(null);

  public static final TileEntityType<TileGemConverter> GEM_CONVERTER =
    TileEntityType.Builder.create(TileGemConverter::new, Machines.gem_converter).build(null);

  public static final TileEntityType<TileInverter> INVERTER =
    TileEntityType.Builder.create(TileInverter::new, Machines.inverter).build(null);

  public static final TileEntityType<TileMagicUnlocker> MAGIC_INFUSER =
    TileEntityType.Builder.create(TileMagicUnlocker::new, Machines.magic_infuser).build(null);

  public static final TileEntityType<TileIdentifier> IDENTIFIER =
    TileEntityType.Builder.create(TileIdentifier::new, Machines.identifier).build(null);

  public static final TileEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    TileEntityType.Builder.create(TileAdvancedOreRefinery::new, Machines.advanced_ore_refinery).build(null);

  public static final TileEntityType<TileCrystalMatterReplicator> CRYSTAL_MATTER_REPLICATOR =
    TileEntityType.Builder.create(TileCrystalMatterReplicator::new, Machines.crystal_matter_generator).build(null);

  public static final TileEntityType<TileSuspensionBridge> SUSPENSION_BRIDGE = null;

  public static final TileEntityType<TileDataCable> DATA_CABLE =
    TileEntityType.Builder.create(TileDataCable::new, Wires.data_cable).build(null);

  public static final TileEntityType<TilePortalControlPanel> PORTAL_CONTROL_PANEL =
    TileEntityType.Builder.create(TilePortalControlPanel::new, Machines.portal_control_panel).build(null);

  public static final TileEntityType<TilePortalFrame> PORTAL_FRAME =
    TileEntityType.Builder.create(TilePortalFrame::new, Machines.portal_frame).build(null);

  public static final TileEntityType<TilePortal> PORTAL_BLOCK =
    TileEntityType.Builder.create(TilePortal::new, Portal.portal).build(null);

  public static final TileEntityType<TileLaserHousing> LASER_MACHINE =
    TileEntityType.Builder.create(TileLaserHousing::new, Machines.laser_housing).build(null);

  public static final TileEntityType<TileLaser> LASER =
    TileEntityType.Builder.create(TileLaser::new, Laser.cannons).build(null);

  public static final TileEntityType<TileLaserBeam> LASER_BEAM =
    TileEntityType.Builder.create(TileLaserBeam::new, Laser.beams).build(null);

  public static final TileEntityType<TileFusionEnergyConverter> FUSION_ENERGY_CONVERTER =
    TileEntityType.Builder.create(TileFusionEnergyConverter::new, Machines.fusion_converter).build(null);

  public static final TileEntityType<TileFusionChamber> FUSION_CHAMBER =
    TileEntityType.Builder.create(TileFusionChamber::new, Machines.singularity_container).build(null);

}
