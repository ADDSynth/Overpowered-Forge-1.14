package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.util.data.ScoreUtil;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Score;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

/** Created on the client side whenever the player selects a Player or Objective,
 *  and gets sent to the Server. Determines player's score given the objective and
 *  returns the score back to the client that requested the score. */
public final class RequestPlayerScoreMessage {

  private String player;
  private String objective;

  public RequestPlayerScoreMessage(final String player, String objective){
    this.player = player;
    this.objective = objective;
  }

  public static final void encode(final RequestPlayerScoreMessage message, final PacketBuffer buf){
    buf.writeString(message.player);
    buf.writeString(message.objective);
  }

  public static final RequestPlayerScoreMessage decode(final PacketBuffer buf){
    return new RequestPlayerScoreMessage(NetworkUtil.readString(buf), NetworkUtil.readString(buf));
  }

  public static void handle(final RequestPlayerScoreMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity source = context.get().getSender();
    if(source != null){
      context.get().enqueueWork(() -> {
        // server only gets sent this message if player name and objective name strings exist. 
        try{
          @SuppressWarnings("resource")
          final MinecraftServer server = source.getServer();
          if(server != null){
            final Score score = ScoreUtil.getScore(server, message.player, message.objective);
            final int player_score = score.getScorePoints();
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> source), new PlayerScoreMessage(player_score));
          }
        }
        catch(Exception e){}
      });
    }
    context.get().setPacketHandled(true);
  }

}
