package addsynth.overpoweredmod.compatability;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.Debug;

@Deprecated
public final class CompatabilityManager {

  /**
   * This function should only called in the PostInit loading stage. 
   */
  public static final void init_mod_compatability(){
    Debug.log_setup_info("Begin Mod Compatability Init...");
    
    Debug.log_setup_info("Finished Mod Compatability Init.");
  }

}
