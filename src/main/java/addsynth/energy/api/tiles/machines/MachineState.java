package addsynth.energy.api.tiles.machines;

import addsynth.core.util.StringUtil;

public enum MachineState {

  OFF         ("gui.addsynth_energy.machine_state.off"),
  POWERING_ON ("gui.addsynth_energy.machine_state.powering_on"),
  POWERING_OFF("gui.addsynth_energy.machine_state.powering_off"),
  IDLE        ("gui.addsynth_energy.machine_state.idle"),
  RUNNING     ("gui.addsynth_energy.machine_state.running");

  public static final MachineState[] value = MachineState.values();

  private final String translation_key;

  private MachineState(final String translation_key){
    this.translation_key = translation_key;
  }

  public final String getStatus(){
    return StringUtil.translate(translation_key);
  }

}
