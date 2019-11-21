package addsynth.overpoweredmod.network.server_messages;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.automatic.TileGemConverter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class CycleGemConverterMessage {

  private final BlockPos position;
  private final boolean cycle_direction;

  public CycleGemConverterMessage(final BlockPos position, final boolean cycle_direction){
    this.position = position;
    this.cycle_direction = cycle_direction;
  }

  public static final void encode(final CycleGemConverterMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeBoolean(message.cycle_direction);
  }

  public static final CycleGemConverterMessage decode(final PacketBuffer buf){
    return new CycleGemConverterMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readBoolean());
  }

  public static void handle(final CycleGemConverterMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      context.get().enqueueWork(() -> {
        final ServerWorld world = player.getServerWorld();
        if(world.isAreaLoaded(message.position, 0)){
          final TileGemConverter tile = MinecraftUtility.getTileEntity(message.position, world, TileGemConverter.class);
          if(tile != null){
            tile.cycle(message.cycle_direction);
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
