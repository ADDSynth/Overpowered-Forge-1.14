package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SyncPortalDataMessage {

  private final BlockPos position;
  private final boolean[] items;
  private final PortalMessage message;
  private final boolean valid_portal;

  public SyncPortalDataMessage(final BlockPos position, final boolean[] items, final PortalMessage message, final boolean valid){
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
    buf.writeInt(message.message.ordinal());
    buf.writeBoolean(message.valid_portal);
  }

  public static final SyncPortalDataMessage decode(final PacketBuffer buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    final boolean[] items = {
      buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(),
      buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean()
    };
    final PortalMessage message = PortalMessage.values()[buf.readInt()];
    final boolean valid_portal = buf.readBoolean();
    return new SyncPortalDataMessage(position, items, message, valid_portal);
  }

  public static void handle(final SyncPortalDataMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      final World world = Minecraft.getInstance().player.world;
      if(world.isAreaLoaded(message.position, 0)){
        final TilePortalControlPanel control_panel = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
        if(control_panel != null){
          control_panel.setData(message.items, message.message, message.valid_portal);
        }
      }
    });
    context.get().setPacketHandled(true);
  }

}
