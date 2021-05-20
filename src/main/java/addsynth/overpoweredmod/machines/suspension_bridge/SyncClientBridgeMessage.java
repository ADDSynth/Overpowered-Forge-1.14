package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.List;
import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SyncClientBridgeMessage {

  private final BlockPos[] positions;
  private BridgeMessage bridge_message;
  private BridgeMessage[] messages;

  public SyncClientBridgeMessage(final BlockPos[] positions, final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this.positions = positions;
    this.bridge_message = bridge_message;
    this.messages = messages;
  }

  public SyncClientBridgeMessage(final List<BlockPos> positions, final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this(positions.toArray(new BlockPos[positions.size()]), bridge_message, messages);
  }

  public static final void encode(final SyncClientBridgeMessage message, final PacketBuffer buf){
    NetworkUtil.writeBlockPositions(buf, message.positions);
    buf.writeInt(message.bridge_message.ordinal());
    buf.writeInt(message.messages[0].ordinal());
    buf.writeInt(message.messages[1].ordinal());
    buf.writeInt(message.messages[2].ordinal());
    buf.writeInt(message.messages[3].ordinal());
    buf.writeInt(message.messages[4].ordinal());
    buf.writeInt(message.messages[5].ordinal());
  }

  public static final SyncClientBridgeMessage decode(final PacketBuffer buf){
    final BlockPos[] positions = NetworkUtil.readBlockPositions(buf);
    final BridgeMessage[] v = BridgeMessage.values();
    final BridgeMessage bridge_message = v[buf.readInt()];
    final BridgeMessage[] messages = {
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()],
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()]};
    return new SyncClientBridgeMessage(positions, bridge_message, messages);
  }

  public static final void handle(final SyncClientBridgeMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      @SuppressWarnings("resource") final Minecraft minecraft = Minecraft.getInstance();
      final World world = minecraft.player.world;
      TileSuspensionBridge tile;
      for(final BlockPos pos : message.positions){
        if(world.isAreaLoaded(pos, 0)){
          tile = MinecraftUtility.getTileEntity(pos, world, TileSuspensionBridge.class);
          if(tile != null){
            tile.setMessages(message.bridge_message, message.messages);
          }
        }
      }
    });
    context.get().setPacketHandled(true);
  }

}
