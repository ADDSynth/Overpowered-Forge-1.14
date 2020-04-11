package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SyncClientBridgeMessage {

  private final BlockPos position;
  private BridgeMessage[] messages;

  public SyncClientBridgeMessage(final BlockPos position, final BridgeMessage[] messages){
    this.position = position;
    this.messages = messages;
  }

  public static final void encode(final SyncClientBridgeMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.messages[0].ordinal());
    buf.writeInt(message.messages[1].ordinal());
    buf.writeInt(message.messages[2].ordinal());
    buf.writeInt(message.messages[3].ordinal());
    buf.writeInt(message.messages[4].ordinal());
    buf.writeInt(message.messages[5].ordinal());
  }

  public static final SyncClientBridgeMessage decode(final PacketBuffer buf){
    final BlockPos position = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    final BridgeMessage[] v = BridgeMessage.values();
    final BridgeMessage[] messages = {
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()],
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()]};
    return new SyncClientBridgeMessage(position, messages);
  }

  public static final void handle(final SyncClientBridgeMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      final World world = Minecraft.getInstance().player.world;
      if(world.isAreaLoaded(message.position, 0)){
        final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(message.position, world, TileSuspensionBridge.class);
        if(tile != null){
          tile.setMessages( message.messages);
        }
      }
    });
    context.get().setPacketHandled(true);
  }

}
