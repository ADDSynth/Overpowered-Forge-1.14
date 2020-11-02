package addsynth.core.util.game;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;

public final class MessageUtil {

  /** Sends a SYSTEM type message to the player. This will execute correctly regardless if it was
   *  called from the server or client.
   * @param player
   * @param translation_key
   */
  public static final void send_to_player(final PlayerEntity player, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer(player.world); // gets server no matter what
    if(server != null){
      player.sendMessage(TextComponentHelper.createComponentTranslation(server, translation_key, arguments));
    }
  }

  public static final void send_to_all_players(final World world, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer(world);
    if(server != null){
      send_to_all_players(server, TextComponentHelper.createComponentTranslation(server, translation_key, arguments));
    }
  }

  public static final void send_to_all_players(final World world, final ITextComponent text_component){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer(world);
    if(server != null){
      send_to_all_players(server, text_component);
    }
  }
  
  private static final void send_to_all_players(final MinecraftServer server, final ITextComponent text_component){
    final PlayerList player_list = server.getPlayerList();
    if(player_list != null){
      player_list.sendMessage(text_component);
    }
  }

  public static final void send_to_all_players_in_world(final World world, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer(world);
    if(server != null){
      send_to_all_players_in_world(server, world, TextComponentHelper.createComponentTranslation(server, translation_key, arguments));
    }
  }

  public static final void send_to_all_players_in_world(final World world, final ITextComponent text_component){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer(world);
    if(server != null){
      send_to_all_players_in_world(server, world, text_component);
    }
  }

  private static final void send_to_all_players_in_world(final MinecraftServer server, final World world, final ITextComponent text_component){
    for(final ServerPlayerEntity player : server.getPlayerList().getPlayers()){
      if(player.world.getDimension().getType() == world.getDimension().getType()){
        player.sendMessage(text_component);
      }
    }
  }

}
