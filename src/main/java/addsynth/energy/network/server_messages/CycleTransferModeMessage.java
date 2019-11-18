package addsynth.energy.network.server_messages;

import addsynth.core.util.MinecraftUtility;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class CycleTransferModeMessage implements IMessage {

  private BlockPos position;

  public CycleTransferModeMessage(){}

  public CycleTransferModeMessage(final BlockPos position){
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

  public static final class Handler implements IMessageHandler<CycleTransferModeMessage, IMessage> {

    @Override
    public IMessage onMessage(CycleTransferModeMessage message, MessageContext context) {
      final ServerWorld world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final ServerWorld world, final CycleTransferModeMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileUniversalEnergyTransfer tile = MinecraftUtility.getTileEntity(message.position, world, TileUniversalEnergyTransfer.class);
        if(tile != null){
          tile.set_next_transfer_mode();
        }
      }
    }
  }

}
