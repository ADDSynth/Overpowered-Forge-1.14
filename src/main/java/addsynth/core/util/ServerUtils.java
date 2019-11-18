package addsynth.core.util;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class ServerUtils {

  public static final MinecraftServer getServer(){
    // Thanks Google: http://www.minecraftforge.net/forum/topic/37211-19-minecraftservergetserver-is-missing/
    return FMLCommonHandler.instance().getMinecraftServerInstance();
  }

  public static ArrayList<ServerPlayerEntity> get_players_in_world(final World world){
    final ArrayList<ServerPlayerEntity> player_list = new ArrayList<>(20);
    final MinecraftServer server = getServer();
    if(server != null){
      for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
        if(player.world == world){
          player_list.add(player);
        }
      }
    }
    return player_list;
  }

  public static void send_message_to_all_players(final ITextComponent text_component) {
    MinecraftServer server = getServer();
    if(server != null){
      PlayerList player_list = server.getPlayerList();
      if(player_list != null){
        player_list.sendMessage(text_component);
      }
    }
  }

  public static void send_message_to_all_players_in_world(final ITextComponent text_component, final World world){
    final MinecraftServer server = getServer();
    if(server != null){
      for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
        if(player.world == world){
          player.sendMessage(text_component);
        }
      }
    }
  }

  /** Allows any Entity and not EntityLiving? */
  public static void teleport_to_dimension(final Entity entity, final int dimension_id){
    entity.changeDimension(dimension_id);
  }

  //public static void teleport_to_dimension(final Entity entity, final int dimension_id, final Teleporter teleporter){
  //   if(entity instanceof EntityPlayerMP){
  //     getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)entity, dimension_id, teleporter);
  //   }
  // }

  public static void teleport_to_dimension(final ServerPlayerEntity player, final int dimension_id, final Teleporter teleporter){
    getServer().getPlayerList().transferPlayerToDimension(player, dimension_id, teleporter);
  }

  /*
  JUST USE: NetworkHandler.INSTANCE.sendToDimension(new Message(), world.provider.getDimension()));
  public static void send_network_message_to_clients_in_world(SimpleNetworkWrapper network, World world, IMessage message){
    final ArrayList<EntityPlayerMP> players_in_world = get_players_in_world(world);
    for(EntityPlayerMP player : players_in_world){
      network.sendTo(message, player);
    }
  }
  */

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
    int y = world.getChunk(x >> 4, z >> 4).getHeightValue(x & 15, z & 15); // OPTIMIZE: this to use the world.getChunk(BlockPos) function?
    return new BlockPos(x,y,z);
    // Would have used:
    //   if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
    // in the complicated algorthm, for checking for air blocks
  }

}
