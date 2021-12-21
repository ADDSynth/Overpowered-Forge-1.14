package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.data.TeamData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/** Sent from the server to the client, to sync the {@link TeamData} */
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

  public static void handle(final TeamManagerSyncMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    // context.get().enqueueWork(() -> {
    // });
    context_supplier.get().setPacketHandled(true);
  }

}
