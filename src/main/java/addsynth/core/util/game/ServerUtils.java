package addsynth.core.util.game;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.math.BlockPos;
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

  /** <p>I was going to develop some complicated algorithm, but then I found the Vanilla code,
   *  and I got it to work! Please see the code inside this function and execute that
   *  instead of this function.</p>
   *  <p>This is used if you want to spawn an entity in a dimension, for instance, teleporting
   *  a player from one dimension to another. This will CURRENTLY find the top-most position
   *  in the chunk and return it.</p>
   *  <p>The complicated algorithm I was going to write was going to search for a free space
   *  by having the x,z coordinate as the origin and continuously searching outward, and also
   *  searching the entire y column for the greatest amount of space, and, assuming that was
   *  outside, spawn you at the bottom of the largest free space in the column. But if the
   *  spawn location it picked in that first column was far enough away from the origin
   *  coordinates, then it would also run this algorithm on nearby adjacent columns and see if
   *  it could find a spawn location that was closer to the origin coordinates.</p>
   * @param world
   * @param origin
   * @return
   */
  public static BlockPos get_spawn_position(final ServerWorld world, final BlockPos origin){
    int x = origin.getX();
    int z = origin.getZ();
    int y = 80; // world.getChunk(x >> 4, z >> 4).getHeight(); // getHeightmap(HeightMap.Type.WORLD_SURFACE).; // OPTIMIZE: this to use the world.getChunk(BlockPos) function?
    return new BlockPos(x,y,z);
    // Would have used:
    //   if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
    // in the complicated algorthm, for checking for air blocks
  }

}
