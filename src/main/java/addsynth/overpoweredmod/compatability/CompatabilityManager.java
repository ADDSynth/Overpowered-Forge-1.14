package addsynth.overpoweredmod.compatability;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.Debug;

public final class CompatabilityManager {

  /**
   * This function should only called in the PostInit loading stage. 
   */
  public static final void init_mod_compatability(){
    Debug.log_setup_info("Begin Mod Compatability Init...");
    
    if(Compatability.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
    
    Debug.log_setup_info("Finished Mod Compatability Init.");
  }

}
