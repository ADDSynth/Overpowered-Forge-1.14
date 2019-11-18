package addsynth.overpoweredmod.network.server_messages;

import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalControlPanel;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class PortalControlMessage implements IMessage {

  private BlockPos position;
  private int action;

  public PortalControlMessage(){}

  public PortalControlMessage(final BlockPos position, final int action){
    this.position = position;
    this.action = action;
  }

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    action = buf.readInt();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeInt(action);
  }

  public static final class Handler implements IMessageHandler<PortalControlMessage, IMessage> {

    @Override
    public IMessage onMessage(PortalControlMessage message, MessageContext context) {
      final ServerWorld world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final ServerWorld world, final PortalControlMessage message){
      if(world.isBlockLoaded(message.position)){
        final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
        if(tile != null){
          switch(message.action){
          case 0: tile.check_portal(); break;
          case 1: tile.generate_portal(); break;
          }
        }
      }
    }
  }

}
