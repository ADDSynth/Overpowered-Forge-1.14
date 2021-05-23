package addsynth.core.util.server;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public final class ServerUtils {

  // DO NOT CACHE THE SERVER! Because the actively running server could change during the course
  // of the game, such as when a player quits a multiplayer world and starts a singleplayer world.

  /** @deprecated Use {@link ServerUtils#getServer(World)} whenever possible. */
  @Nullable
  public static final MinecraftServer getServer(){
    final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    if(server == null){
      ADDSynthCore.log.fatal(new NullPointerException(ServerUtils.class.getName()+".getServer() was unable to retrieve the current running server! Maybe there is no server running?"));
    }
    return server;
  }

  /** This is a more stable way to get the MinecraftServer in my opinion, but it only works if the
   *  World used is a Server World. Falls back to the other getServer() method if this doesn't work.
   * @param world
   */
  @Nullable
  public static final MinecraftServer getServer(final World world){
    final MinecraftServer server = world.getServer();
    if(server == null){
      return getServer();
    }
    return server;
  }

  public static ArrayList<ServerPlayerEntity> get_players_in_world(final World world){
    final ArrayList<ServerPlayerEntity> player_list = new ArrayList<>(20);
    final MinecraftServer server = getServer(world);
    if(server != null){
      for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
        if(player.world == world){
          player_list.add(player);
        }
      }
    }
    return player_list;
  }

  /** Allows any Entity and not EntityLiving? */
  public static void teleport_to_dimension(final Entity entity, final int dimension_id){
    // entity.changeDimension(dimension_id);
  }

  //public static void teleport_to_dimension(final Entity entity, final int dimension_id, final Teleporter teleporter){
  //   if(entity instanceof EntityPlayerMP){
  //     getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)entity, dimension_id, teleporter);
  //   }
  // }

  public static void teleport_to_dimension(final ServerPlayerEntity player, final int dimension_id, final Teleporter teleporter){
    // getServer().getPlayerList().transferPlayerToDimension(player, dimension_id, teleporter);
  }

}
