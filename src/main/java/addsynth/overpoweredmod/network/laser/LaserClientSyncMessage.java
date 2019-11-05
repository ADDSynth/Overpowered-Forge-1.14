package addsynth.overpoweredmod.network.laser;

import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class LaserClientSyncMessage implements IMessage {

  private BlockPos position;
  private int number_of_lasers;

  public LaserClientSyncMessage(){}

  public LaserClientSyncMessage(final BlockPos position, final int number_of_lasers){
    this.position = position;
    this.number_of_lasers = number_of_lasers;
  }

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    number_of_lasers = buf.readInt();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeInt(number_of_lasers);
  }

  public static final class Handler implements IMessageHandler<LaserClientSyncMessage, IMessage> {

    @Override
    public IMessage onMessage(LaserClientSyncMessage message, MessageContext context) {
      Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
      return null;
    }
    
    private static final void processMessage(final LaserClientSyncMessage message){
      final WorldClient world = Minecraft.getMinecraft().world;
      if(world.isBlockLoaded(message.position)){
        final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
        if(tile != null){
          tile.number_of_lasers = message.number_of_lasers;
        }
      }
    }
  }

}
