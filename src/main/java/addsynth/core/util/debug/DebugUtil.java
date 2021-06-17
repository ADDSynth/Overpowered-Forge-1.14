package addsynth.core.util.debug;

import javax.annotation.Nullable;
import addsynth.core.util.server.ServerUtils;
import net.minecraft.profiler.DebugProfiler;
import net.minecraft.server.MinecraftServer;

public final class DebugUtil {

  @Nullable
  @SuppressWarnings("resource")
  public static final DebugProfiler getProfiler(){
    @SuppressWarnings("deprecation")
    final MinecraftServer server = ServerUtils.getServer();
    return server != null ? server.getProfiler() : null;
  }

  /** Use this only if you don't have the server readily available. */
  public static final void beginSection(final String name){
    @SuppressWarnings({ "deprecation", "resource" })
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      server.getProfiler().startSection(name);
    }
  }

  /** Use this only if you don't have the server readily available. */
  public static final void endSection(){
    @SuppressWarnings({ "deprecation", "resource" })
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      server.getProfiler().endSection();
    }
  }

}
