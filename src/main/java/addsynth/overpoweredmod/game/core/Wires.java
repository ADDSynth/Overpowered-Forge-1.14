package addsynth.overpoweredmod.game.core;

import addsynth.energy.gameplay.energy_wire.EnergyWire;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.machines.data_cable.DataCable;

public final class Wires {

  static {
    Debug.log_setup_info("Begin loading Wires class...");
  }

  public static final EnergyWire              wire                     = new EnergyWire("energy_wire");
  public static final DataCable               data_cable               = new DataCable("data_cable");

  static {
    Debug.log_setup_info("Finished loading Wires class.");
  }

}
