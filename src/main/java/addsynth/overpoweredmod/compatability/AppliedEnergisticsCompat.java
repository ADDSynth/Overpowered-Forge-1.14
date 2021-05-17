package addsynth.overpoweredmod.compatability;

import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.cannon.TileLaser;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import appeng.api.AEAddon;
import appeng.api.IAEAddon;
import appeng.api.IAppEngApi;
import appeng.api.movable.IMovableRegistry;

@AEAddon
public final class AppliedEnergisticsCompat implements IAEAddon {

  @Override
  public void onAPIAvailable(IAppEngApi api){
    final IMovableRegistry moveable_registry = api.registries().movable();
    moveable_registry.whiteListTileEntity(TileCrystalEnergyExtractor.class);
    moveable_registry.whiteListTileEntity(TileGemConverter.class);
    moveable_registry.whiteListTileEntity(TileInverter.class);
    moveable_registry.whiteListTileEntity(TileMagicInfuser.class);
    moveable_registry.whiteListTileEntity(TileIdentifier.class);
    moveable_registry.whiteListTileEntity(TileDataCable.class);
    moveable_registry.whiteListTileEntity(TileLaser.class);
    moveable_registry.whiteListTileEntity(TileLaserHousing.class);
    moveable_registry.whiteListTileEntity(TileCrystalMatterGenerator.class);
    moveable_registry.whiteListTileEntity(TilePortalControlPanel.class);
    moveable_registry.whiteListTileEntity(TilePortalFrame.class);
    moveable_registry.whiteListTileEntity(TilePortal.class);
    moveable_registry.whiteListTileEntity(TileAdvancedOreRefinery.class);
    moveable_registry.whiteListTileEntity(TileFusionEnergyConverter.class);
    moveable_registry.whiteListTileEntity(TileFusionChamber.class);
    // TODO: Will need to implement some coding finesse to get the Energy Suspension Bridge here.
  }

}
