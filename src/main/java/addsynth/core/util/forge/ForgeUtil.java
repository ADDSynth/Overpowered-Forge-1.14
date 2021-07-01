package addsynth.core.util.forge;

import addsynth.core.ADDSynthCore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

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

  /** Returns true if this is a Client instance of Minecraft.<br />
   *  This does not check if we're currently executing on the Client side.
   */
  public static final boolean isClientInstance(){
    return FMLEnvironment.dist == Dist.CLIENT;
  }

  /** Returns true if this is a dedicated server instance. */
  public static final boolean isDedicatedServer(){
    return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
  }

}
