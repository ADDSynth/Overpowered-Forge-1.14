package addsynth.overpoweredmod.machines.suspension_bridge;

public enum BridgeMessage {

  PENDING("Invalid Shape", false),
  OKAY("Normal", true),
  OBSTRUCTED("Obstructed", false),
  NO_BRIDGE("No Bridge", false),
  INVALID_BRIDGE("Invalid", false),
  NO_LENS("No Lens", false);

  private final String message;
  private final boolean valid;
  
  BridgeMessage(final String message, final boolean valid){
    this.message = message;
    this.valid = valid;
  }

  public final String getMessage(){
    return message;
  }

  public final boolean is_valid(){
    return valid;
  }

}
