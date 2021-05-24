package addsynth.energy.compat;

import addsynth.energy.gameplay.machines.circuit_fabricator.TileCircuitFabricator;
import addsynth.energy.gameplay.machines.compressor.TileCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.machines.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.machines.generator.TileGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyInterface;
import appeng.api.AEAddon;
import appeng.api.IAEAddon;
import appeng.api.IAppEngApi;
import appeng.api.movable.IMovableRegistry;

@AEAddon
public final class AppliedEnergisticsCompat implements IAEAddon {

  @Override
  public void onAPIAvailable(IAppEngApi api){
    final IMovableRegistry moveable_registry = api.registries().movable();
    moveable_registry.whiteListTileEntity(TileEnergyWire.class);
    moveable_registry.whiteListTileEntity(TileGenerator.class);
    moveable_registry.whiteListTileEntity(TileEnergyStorage.class);
    moveable_registry.whiteListTileEntity(TileCompressor.class);
    moveable_registry.whiteListTileEntity(TileElectricFurnace.class);
    moveable_registry.whiteListTileEntity(TileCircuitFabricator.class);
    moveable_registry.whiteListTileEntity(TileUniversalEnergyInterface.class);
  }

}
