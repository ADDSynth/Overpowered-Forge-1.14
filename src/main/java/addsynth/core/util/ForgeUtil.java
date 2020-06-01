package addsynth.core.util;

import addsynth.core.ADDSynthCore;
import net.minecraftforge.fml.ModLoadingContext;

public final class ForgeUtil {

  /**
   * Taken from {@link net.minecraftforge.registries.GameData#checkPrefix(String)}.
   * @return String modID
   */
  public static final String getModID(){
    final String id = ModLoadingContext.get().getActiveNamespace();
    if(id.equals("minecraft")){
      ADDSynthCore.log.warn(ForgeUtil.class.getName()+".getModID() returned 'minecraft'. This might not be what you expected. Did you call getModID() at a wrong time?");
    }
    return id;
  }

}
