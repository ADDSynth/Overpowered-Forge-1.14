package addsynth.material.compat;

import addsynth.core.game.Compatability;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class MaterialsCompat {

  public static final boolean addsynth_energy_loaded = Compatability.ADDSYNTH_ENERGY.loaded;

  public static final boolean SteelModAbsent(){
    return !(
      Compatability.IMMERSIVE_ENGINEERING.loaded ||
      Compatability.MEKANISM.loaded ||
      Compatability.RAILCRAFT.loaded
    );
  }

  public static final void sendIMCMessages(final InterModEnqueueEvent event){
    if(Compatability.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

}
