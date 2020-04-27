package addsynth.energy.tiles.machines;

public enum MachineState {

  OFF("Off"),
  POWERING_ON("Powering ON"),
  POWERING_OFF("Powering OFF"),
  IDLE("Idle"),
  RUNNING("Working");

  public static final MachineState[] value = MachineState.values();

  private final String status;

  MachineState(final String status){
    this.status = status;
  }

  public final String getStatus(){
    return status;
  }

}
