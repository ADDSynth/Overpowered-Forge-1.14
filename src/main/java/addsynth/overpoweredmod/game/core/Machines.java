package addsynth.overpoweredmod.game.core;

import addsynth.energy.gameplay.compressor.Compressor;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.AdvancedOreRefinery;
import addsynth.overpoweredmod.machines.crystal_matter_generator.CrystalMatterGenerator;
import addsynth.overpoweredmod.machines.fusion.chamber.FusionChamber;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlLaserBeam;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit;
import addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.GemConverter;
import addsynth.overpoweredmod.machines.generator.Generator;
import addsynth.overpoweredmod.machines.identifier.Identifier;
import addsynth.overpoweredmod.machines.inverter.Inverter;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.LaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.MagicInfuser;
import addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.PortalFrame;

public final class Machines {

  static {
    Debug.log_setup_info("Begin loading Machines class...");
  }

  public static final Generator               generator                = new Generator("generator");
  
  
  public static final Compressor              compressor               = new Compressor("compressor");
  public static final GemConverter            gem_converter            = new GemConverter("gem_converter");
  public static final Inverter                inverter                 = new Inverter("inverter");
  public static final MagicInfuser            magic_infuser            = new MagicInfuser("magic_infuser");
  public static final Identifier              identifier               = new Identifier("identifier");
  public static final PortalControlPanel      portal_control_panel     = new PortalControlPanel("portal_control_panel");
  public static final PortalFrame             portal_frame             = new PortalFrame("portal_frame");
  public static final CrystalMatterGenerator  crystal_matter_generator = new CrystalMatterGenerator("crystal_matter_generator");
  public static final AdvancedOreRefinery     advanced_ore_refinery    = new AdvancedOreRefinery("advanced_ore_refinery");

  public static final FusionEnergyConverter   fusion_converter         = new FusionEnergyConverter("fusion_energy_converter");
  public static final FusionChamber           fusion_chamber           = new FusionChamber("fusion_chamber");
  public static final FusionControlUnit       fusion_control_unit      = new FusionControlUnit("fusion_control_unit");
  public static final LaserCannon             fusion_control_laser     = new LaserCannon("fusion_control_laser", -1);
  public static final FusionControlLaserBeam  fusion_control_laser_beam = new FusionControlLaserBeam("fusion_control_laser_beam");

  public static final LaserHousing            laser_housing            = new LaserHousing("laser_housing");

  static {
    Debug.log_setup_info("Finished loading Machines class.");
  }

}
