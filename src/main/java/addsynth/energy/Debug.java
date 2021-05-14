package addsynth.energy;

public final class Debug {

  public static final boolean debug_setup = false;

  public static final void log_setup_info(final String message){
    if(debug_setup){
      ADDSynthEnergy.log.info(message);
    }
  }

}
