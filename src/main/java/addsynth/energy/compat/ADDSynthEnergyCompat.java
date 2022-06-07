package addsynth.energy.compat;

import addsynth.core.compat.Compatibility;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class ADDSynthEnergyCompat {

  public static final void sendIMCMessages(final InterModEnqueueEvent event){
    if(Compatibility.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

}
