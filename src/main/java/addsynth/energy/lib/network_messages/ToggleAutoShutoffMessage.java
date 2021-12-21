package addsynth.energy.lib.network_messages;

import java.util.function.Supplier;
import addsynth.energy.lib.tiles.machines.IAutoShutoff;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ToggleAutoShutoffMessage {

  private final BlockPos position;

  public ToggleAutoShutoffMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final ToggleAutoShutoffMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final ToggleAutoShutoffMessage decode(final PacketBuffer buf){
    return new ToggleAutoShutoffMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final ToggleAutoShutoffMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayerEntity player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerWorld world = player.func_71121_q();
      context.enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileEntity tile = world.getTileEntity(message.position);
          if(tile != null){
            if(tile instanceof IAutoShutoff){
              ((IAutoShutoff)tile).toggle_auto_shutoff();
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
