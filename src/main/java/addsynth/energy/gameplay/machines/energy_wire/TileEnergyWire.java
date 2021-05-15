package addsynth.energy.gameplay.machines.energy_wire;

import addsynth.energy.api.energy_network.tiles.BasicEnergyNetworkTile;
import addsynth.energy.registers.Tiles;

/**
 * This is the TileEntity that Energy Wires should use to form an Energy Network.
 * Have your Wire blocks create a new TileEntity instance of this class type.
 * This class and Energy Network will automatically detect the Block type and
 * connect to other blocks of the same type.
 * @author ADDSynth
 */
public final class TileEnergyWire extends BasicEnergyNetworkTile {

  public TileEnergyWire(){
    super(Tiles.ENERGY_WIRE);
  }

}
