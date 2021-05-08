package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.TeamData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public final class TeamManagerSyncMessage {

  public TeamManagerSyncMessage(){
  }

  public static final void encode(final TeamManagerSyncMessage message, final PacketBuffer buf){
    TeamData.encode(buf);
  }

  public static final TeamManagerSyncMessage decode(final PacketBuffer buf){
    TeamData.decode(buf);
    return new TeamManagerSyncMessage();
  }

  public static void handle(final TeamManagerSyncMessage message, final Supplier<NetworkEvent.Context> context){
    // context.get().enqueueWork(() -> {
    // });
    context.get().setPacketHandled(true);
  }

}
