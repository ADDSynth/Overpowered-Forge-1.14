package addsynth.overpoweredmod.game.core;

import addsynth.energy.gameplay.blocks.EnergyStorageBlock;
import addsynth.energy.gameplay.blocks.UniversalEnergyBlock;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.blocks.tiles.energy.*;
import addsynth.overpoweredmod.blocks.tiles.fusion.*;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserCannon;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserHousing;
import addsynth.overpoweredmod.blocks.tiles.machines.*;
import addsynth.overpoweredmod.blocks.tiles.portal.PortalControlPanel;
import addsynth.overpoweredmod.blocks.tiles.portal.PortalFrame;

public final class Machines {

  static {
    Debug.log_setup_info("Begin loading Machines class...");
  }

  public static final Generator               generator                = new Generator("generator");
  public static final EnergyStorageBlock      energy_storage           = new EnergyStorageBlock("energy_storage");
  public static final UniversalEnergyBlock    universal_energy_machine = new UniversalEnergyBlock("universal_energy_interface");
  
  
  public static final Compressor              compressor               = new Compressor("compressor");
  public static final ElectricFurnace         electric_furnace         = new ElectricFurnace("electric_furnace");
  public static final GemConverter            gem_converter            = new GemConverter("gem_converter");
  public static final Inverter                inverter                 = new Inverter("inverter");
  public static final MagicUnlocker           magic_infuser            = new MagicUnlocker("magic_infuser");
  public static final Identifier              identifier               = new Identifier("identifier");
  public static final PortalControlPanel      portal_control_panel     = new PortalControlPanel("portal_control_panel");
  public static final PortalFrame             portal_frame             = new PortalFrame("portal_frame");
  public static final CrystalMatterReplicator crystal_matter_generator = new CrystalMatterReplicator("crystal_matter_generator");
  public static final AdvancedOreRefinery     advanced_ore_refinery    = new AdvancedOreRefinery("advanced_ore_refinery");

  public static final FusionEnergyConverter   fusion_converter         = new FusionEnergyConverter("fusion_energy_converter");
  public static final FusionControlUnit       laser_scanning_unit      = new FusionControlUnit("fusion_control_unit");
  public static final FusionChamber           singularity_container    = new FusionChamber("fusion_chamber");
  public static final LaserCannon             fusion_laser             = new LaserCannon("fusion_control_laser", -1);
  public static final FusionControlLaserBeam    fusion_control_laser_beam = new FusionControlLaserBeam("fusion_control_laser_beam");

  public static final LaserHousing            laser_housing            = new LaserHousing("laser_housing");

  static {
    Debug.log_setup_info("Finished loading Machines class.");
  }

}
