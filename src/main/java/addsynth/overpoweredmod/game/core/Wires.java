package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.machines.data_cable.DataCable;

public final class Wires {

  static {
    Debug.log_setup_info("Begin loading Wires class...");
  }

  public static final DataCable               data_cable               = new DataCable("data_cable");

  static {
    Debug.log_setup_info("Finished loading Wires class.");
  }

}
