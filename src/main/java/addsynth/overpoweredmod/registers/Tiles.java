package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.beam.TileLaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.TileLaser;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  static {
    Debug.log_setup_info(Tiles.class.getName()+" class was loaded...");
  }

  public static final TileEntityType<TileCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR =
    TileEntityType.Builder.create(TileCrystalEnergyExtractor::new, Machines.crystal_energy_extractor).build(null);

  public static final TileEntityType<TileGemConverter> GEM_CONVERTER =
    TileEntityType.Builder.create(TileGemConverter::new, Machines.gem_converter).build(null);

  public static final TileEntityType<TileInverter> INVERTER =
    TileEntityType.Builder.create(TileInverter::new, Machines.inverter).build(null);

  public static final TileEntityType<TileMagicInfuser> MAGIC_INFUSER =
    TileEntityType.Builder.create(TileMagicInfuser::new, Machines.magic_infuser).build(null);

  public static final TileEntityType<TileIdentifier> IDENTIFIER =
    TileEntityType.Builder.create(TileIdentifier::new, Machines.identifier).build(null);

  public static final TileEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    TileEntityType.Builder.create(TileAdvancedOreRefinery::new, Machines.advanced_ore_refinery).build(null);

  public static final TileEntityType<TileCrystalMatterGenerator> CRYSTAL_MATTER_REPLICATOR =
    TileEntityType.Builder.create(TileCrystalMatterGenerator::new, Machines.crystal_matter_generator).build(null);

  public static final TileEntityType<TileSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    TileEntityType.Builder.create(TileSuspensionBridge::new, Machines.energy_suspension_bridge).build(null);

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
    TileEntityType.Builder.create(TileFusionChamber::new, Machines.fusion_chamber).build(null);

  public static final TileEntityType<TileBlackHole> BLACK_HOLE =
    TileEntityType.Builder.create(TileBlackHole::new, Init.black_hole).build(null);

  static {
    Debug.log_setup_info(Tiles.class.getName()+" class finished loading.");
  }

}
