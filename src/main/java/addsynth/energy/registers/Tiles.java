package addsynth.energy.registers;

import addsynth.energy.gameplay.Blocks;
import addsynth.energy.gameplay.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.universal_energy_interface.TileUniversalEnergyTransfer;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileEnergyWire> ENERGY_WIRE =
    TileEntityType.Builder.create(TileEnergyWire::new, Blocks.wire).build(null);

  public static final TileEntityType<TileEnergyStorage> ENERGY_CONTAINER =
    TileEntityType.Builder.create(TileEnergyStorage::new, Blocks.energy_storage).build(null);

  public static final TileEntityType<TileUniversalEnergyTransfer> UNIVERSAL_ENERGY_INTERFACE =
    TileEntityType.Builder.create(TileUniversalEnergyTransfer::new, Blocks.universal_energy_machine).build(null);

  public static final TileEntityType<TileElectricFurnace> ELECTRIC_FURNACE =
    TileEntityType.Builder.create(TileElectricFurnace::new, Blocks.electric_furnace).build(null);

}
