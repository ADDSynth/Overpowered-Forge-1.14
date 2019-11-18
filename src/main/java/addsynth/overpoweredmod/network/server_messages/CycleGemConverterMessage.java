package addsynth.overpoweredmod.network.server_messages;

import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.automatic.TileGemConverter;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class CycleGemConverterMessage implements IMessage {

  private BlockPos position;
  private boolean cycle_direction;

  public CycleGemConverterMessage(){}

  public CycleGemConverterMessage(final BlockPos position, final boolean cycle_direction){
    this.position = position;
    this.cycle_direction = cycle_direction;
  }

  @Override
  public void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    cycle_direction = buf.readBoolean();
  }

  @Override
  public void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeBoolean(cycle_direction);
  }

  public static final class Handler implements IMessageHandler<CycleGemConverterMessage, IMessage> {

    @Override
    public IMessage onMessage(CycleGemConverterMessage message, MessageContext context) {
      // get the GemConverter TileEntity at the position and call the method.
      final ServerWorld world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final ServerWorld world, final CycleGemConverterMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileGemConverter tile = MinecraftUtility.getTileEntity(message.position, world, TileGemConverter.class);
        if(tile != null){
          tile.cycle(message.cycle_direction);
        }
      }
    }
  }

}
