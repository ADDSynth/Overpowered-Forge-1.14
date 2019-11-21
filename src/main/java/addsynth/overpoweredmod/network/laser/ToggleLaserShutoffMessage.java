package addsynth.overpoweredmod.network.laser;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ToggleLaserShutoffMessage {

  private final BlockPos position;

  public ToggleLaserShutoffMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final ToggleLaserShutoffMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final ToggleLaserShutoffMessage decode(final PacketBuffer buf){
    return new ToggleLaserShutoffMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final ToggleLaserShutoffMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.getServerWorld();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
          if(tile != null){
            tile.toggle_auto_shutoff();
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
