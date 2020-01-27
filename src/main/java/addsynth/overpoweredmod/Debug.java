package addsynth.overpoweredmod;

public final class Debug {

  // https://github.com/knoxcraft/Knoxcraft/issues/2
  // http://www.minecraftforge.net/forum/topic/26607-how-to-show-tracedebug-log-entries-in-output-console/
  // http://www.minecraftforge.net/forum/topic/26174-how-to-enable-quotdebugquot-logging-in-eclipse/
  // http://logging.apache.org/log4j/2.x/manual/configuration.html

  private static final int debug_level = 1; // UNUSED  this, and the function below.
  
  /** This is the master debug variable that determines verbose init logging. */
  private static final boolean debug_setup = false;

  public static final void log_setup_info(final String message){
    if(debug_setup){
      OverpoweredMod.log.info(message);
      // Basically levels Severe, Error, Warning, and Info will print in the
      // log file and Console, but Debug and Trace will only print in the log file.
    }
  }

  public static final void log(final int debug_level, final String message){
    if(debug_level <= Debug.debug_level){
      OverpoweredMod.log.info(message);
    }
  }

}
