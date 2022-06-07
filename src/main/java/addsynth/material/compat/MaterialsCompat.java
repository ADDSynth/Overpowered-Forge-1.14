package addsynth.material.compat;

import addsynth.core.compat.Compatibility;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class MaterialsCompat {

  public static final boolean addsynth_energy_loaded = Compatibility.ADDSYNTH_ENERGY.loaded;

  public static final boolean SteelModAbsent(){
    return !(
      Compatibility.IMMERSIVE_ENGINEERING.loaded ||
      Compatibility.MEKANISM.loaded ||
      Compatibility.RAILCRAFT.loaded
    );
  }

  public static final boolean BronzeModAbsent(){
    return !(
      Compatibility.IMMERSIVE_ENGINEERING.loaded || // In Alloy Smelter
      Compatibility.MEKANISM.loaded                 // by combining dusts
    );
  }

  public static final void sendIMCMessages(final InterModEnqueueEvent event){
    if(Compatibility.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
  }

}
