package addsynth.overpoweredmod.network.laser;

import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class ToggleLaserShutoffMessage implements IMessage {

  private BlockPos position;

  public ToggleLaserShutoffMessage(){}

  public ToggleLaserShutoffMessage(final BlockPos position){
    this.position = position;
  }

  @Override
  public void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
  }

  @Override
  public void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
  }

  public static final class Handler implements IMessageHandler<ToggleLaserShutoffMessage, IMessage> {

    @Override
    public IMessage onMessage(ToggleLaserShutoffMessage message, MessageContext context){
      final WorldServer world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final WorldServer world, final ToggleLaserShutoffMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
        if(tile != null){
          tile.toggle_auto_shutoff();
        }
      }
    }
  }

}
