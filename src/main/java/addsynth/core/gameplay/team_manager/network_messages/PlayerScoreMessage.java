package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public final class PlayerScoreMessage {

  private final int score;

  public PlayerScoreMessage(int score){
    this.score = score;
  }

  public static final void encode(final PlayerScoreMessage message, final PacketBuffer buf){
    buf.writeInt(message.score);
  }

  public static final PlayerScoreMessage decode(final PacketBuffer buf){
    return new PlayerScoreMessage(buf.readInt());
  }

  public static void handle(final PlayerScoreMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      TeamManagerGui.player_score = message.score;
    });
    context.get().setPacketHandled(true);
  }

}
