package addsynth.overpoweredmod.machines.laser.network_messages;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SetLaserDistanceMessage {

  private final BlockPos position;
  private final int laser_distance;

  public SetLaserDistanceMessage(final BlockPos position, final int laser_distance){
    this.position = position;
    this.laser_distance = laser_distance;
  }

  public static final void encode(final SetLaserDistanceMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.laser_distance);
  }

  public static final SetLaserDistanceMessage decode(final PacketBuffer buf){
    return new SetLaserDistanceMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readInt());
  }

  public static void handle(final SetLaserDistanceMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      context.get().enqueueWork(() -> {
        final ServerWorld world = player.func_71121_q();
        if(world.isAreaLoaded(message.position, 0)){
          final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
          if(tile != null){
            tile.getNetwork().setLaserDistance(message.laser_distance);
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
