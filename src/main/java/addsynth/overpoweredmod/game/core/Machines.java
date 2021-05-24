package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.AdvancedOreRefineryBlock;
import addsynth.overpoweredmod.machines.crystal_matter_generator.CrystalMatterGeneratorBlock;
import addsynth.overpoweredmod.machines.energy_extractor.CrystalEnergyExtractorBlock;
import addsynth.overpoweredmod.machines.fusion.chamber.FusionChamberBlock;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlLaserBeam;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit;
import addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverterBlock;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterBlock;
import addsynth.overpoweredmod.machines.identifier.IdentifierBlock;
import addsynth.overpoweredmod.machines.inverter.InverterBlock;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.LaserHousingBlock;
import addsynth.overpoweredmod.machines.magic_infuser.MagicInfuserBlock;
import addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanelBlock;
import addsynth.overpoweredmod.machines.portal.frame.PortalFrameBlock;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergyBridge;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergySuspensionBridgeBlock;

public final class Machines {

  static {
    Debug.log_setup_info("Begin loading Machines class...");
  }

  public static final CrystalEnergyExtractorBlock crystal_energy_extractor = new CrystalEnergyExtractorBlock("crystal_energy_extractor");
  public static final GemConverterBlock           gem_converter            = new GemConverterBlock("gem_converter");
  public static final InverterBlock               inverter                 = new InverterBlock("inverter");
  public static final MagicInfuserBlock           magic_infuser            = new MagicInfuserBlock("magic_infuser");
  public static final IdentifierBlock             identifier               = new IdentifierBlock("identifier");
  public static final PortalControlPanelBlock     portal_control_panel     = new PortalControlPanelBlock("portal_control_panel");
  public static final PortalFrameBlock            portal_frame             = new PortalFrameBlock("portal_frame");
  public static final CrystalMatterGeneratorBlock crystal_matter_generator = new CrystalMatterGeneratorBlock("crystal_matter_generator");
  public static final AdvancedOreRefineryBlock    advanced_ore_refinery    = new AdvancedOreRefineryBlock("advanced_ore_refinery");

  public static final FusionEnergyConverterBlock  fusion_converter         = new FusionEnergyConverterBlock("fusion_energy_converter");
  public static final FusionChamberBlock          fusion_chamber           = new FusionChamberBlock("fusion_chamber");
  public static final FusionControlUnit           fusion_control_unit      = new FusionControlUnit("fusion_control_unit");
  public static final LaserCannon                 fusion_control_laser     = new LaserCannon("fusion_control_laser", -1);
  public static final FusionControlLaserBeam      fusion_control_laser_beam = new FusionControlLaserBeam("fusion_control_laser_beam");

  public static final LaserHousingBlock           laser_housing            = new LaserHousingBlock("laser_housing");

  public static final EnergySuspensionBridgeBlock energy_suspension_bridge = new EnergySuspensionBridgeBlock("energy_suspension_bridge");
  public static final EnergyBridge                white_energy_bridge      = new EnergyBridge("white_energy_bridge", Lens.WHITE);
  public static final EnergyBridge                red_energy_bridge        = new EnergyBridge("red_energy_bridge", Lens.RED);
  public static final EnergyBridge                orange_energy_bridge     = new EnergyBridge("orange_energy_bridge", Lens.ORANGE);
  public static final EnergyBridge                yellow_energy_bridge     = new EnergyBridge("yellow_energy_bridge", Lens.YELLOW);
  public static final EnergyBridge                green_energy_bridge      = new EnergyBridge("green_energy_bridge", Lens.GREEN);
  public static final EnergyBridge                cyan_energy_bridge       = new EnergyBridge("cyan_energy_bridge", Lens.CYAN);
  public static final EnergyBridge                blue_energy_bridge       = new EnergyBridge("blue_energy_bridge", Lens.BLUE);
  public static final EnergyBridge                magenta_energy_bridge    = new EnergyBridge("magenta_energy_bridge", Lens.MAGENTA);

  static {
    Debug.log_setup_info("Finished loading Machines class.");
  }

}
