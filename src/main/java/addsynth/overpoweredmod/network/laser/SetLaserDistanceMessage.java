package addsynth.overpoweredmod.network.laser;

import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class SetLaserDistanceMessage implements IMessage {

  private BlockPos position;
  private int laser_distance;

  public SetLaserDistanceMessage(){}

  public SetLaserDistanceMessage(final BlockPos position, final int laser_distance){
    this.position = position;
    this.laser_distance = laser_distance;
  }

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    laser_distance = buf.readInt();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeInt(laser_distance);
  }

  public static final class Handler implements IMessageHandler<SetLaserDistanceMessage, IMessage> {

    @Override
    public IMessage onMessage(SetLaserDistanceMessage message, MessageContext context) {
      final ServerWorld world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final ServerWorld world, final SetLaserDistanceMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
        if(tile != null){
          tile.getNetwork().setLaserDistance(message.laser_distance);
        }
      }
    }
  }

}
