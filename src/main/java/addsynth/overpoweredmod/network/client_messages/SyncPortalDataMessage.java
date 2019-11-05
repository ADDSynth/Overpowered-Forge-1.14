package addsynth.overpoweredmod.network.client_messages;

import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.NetworkUtil;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalControlPanel;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class SyncPortalDataMessage implements IMessage {

  private BlockPos position;
  private boolean[] items;
  private String message;
  private boolean valid_portal;

  public SyncPortalDataMessage(){}

  public SyncPortalDataMessage(final BlockPos position, final boolean[] items, final String message, final boolean valid){
    this.position = position;
    this.items = items;
    this.message = message;
    this.valid_portal = valid;
  }

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    if(items == null){ items = new boolean[8]; }
    items[0] = buf.readBoolean();
    items[1] = buf.readBoolean();
    items[2] = buf.readBoolean();
    items[3] = buf.readBoolean();
    items[4] = buf.readBoolean();
    items[5] = buf.readBoolean();
    items[6] = buf.readBoolean();
    items[7] = buf.readBoolean();
    message = NetworkUtil.readString(buf);
    valid_portal = buf.readBoolean();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeBoolean(items[0]);
    buf.writeBoolean(items[1]);
    buf.writeBoolean(items[2]);
    buf.writeBoolean(items[3]);
    buf.writeBoolean(items[4]);
    buf.writeBoolean(items[5]);
    buf.writeBoolean(items[6]);
    buf.writeBoolean(items[7]);
    NetworkUtil.writeString(buf, message);
    buf.writeBoolean(valid_portal);
  }

  public static final class Handler implements IMessageHandler<SyncPortalDataMessage, IMessage> {

    @Override
    public IMessage onMessage(SyncPortalDataMessage message, MessageContext context) {
      Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
      return null;
    }
    
    private static final void processMessage(final SyncPortalDataMessage message){
      final WorldClient world = Minecraft.getMinecraft().world;
      if(world.isBlockLoaded(message.position)){
        final TilePortalControlPanel control_panel = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
        if(control_panel != null){
          control_panel.portal_items = message.items;
          control_panel.message      = message.message;
          control_panel.valid_portal = message.valid_portal;
        }
      }
    }
  }

}
