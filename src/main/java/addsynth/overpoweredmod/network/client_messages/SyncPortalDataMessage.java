package addsynth.overpoweredmod.network.client_messages;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.NetworkUtil;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalControlPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SyncPortalDataMessage {

  private final BlockPos position;
  private final boolean[] items;
  private final String message;
  private final boolean valid_portal;

  public SyncPortalDataMessage(final BlockPos position, final boolean[] items, final String message, final boolean valid){
    this.position = position;
    this.items = items;
    this.message = message;
    this.valid_portal = valid;
  }

  public static final void encode(final SyncPortalDataMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeBoolean(message.items[0]);
    buf.writeBoolean(message.items[1]);
    buf.writeBoolean(message.items[2]);
    buf.writeBoolean(message.items[3]);
    buf.writeBoolean(message.items[4]);
    buf.writeBoolean(message.items[5]);
    buf.writeBoolean(message.items[6]);
    buf.writeBoolean(message.items[7]);
    NetworkUtil.writeString(buf, message.message); // OPTIMIZE: convert Portal Messages to an enum and only send the enum index as a byte.
    buf.writeBoolean(message.valid_portal);
  }

  public static final SyncPortalDataMessage decode(final PacketBuffer buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    final boolean[] items = {
      buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(),
      buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean()
    };
    final String message = NetworkUtil.readString(buf);
    final boolean valid_portal = buf.readBoolean();
    return new SyncPortalDataMessage(position, items, message, valid_portal);
  }

  public static void handle(final SyncPortalDataMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      final World world = Minecraft.getInstance().player.world;
      if(world.isAreaLoaded(message.position, 0)){
        final TilePortalControlPanel control_panel = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
        if(control_panel != null){
          control_panel.portal_items = message.items;
          control_panel.message      = message.message;
          control_panel.valid_portal = message.valid_portal;
        }
      }
    });
    context.get().setPacketHandled(true);
  }

}
