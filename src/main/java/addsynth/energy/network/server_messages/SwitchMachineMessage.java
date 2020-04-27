package addsynth.energy.network.server_messages;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.tiles.machines.TileWorkMachine;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SwitchMachineMessage {

  private final BlockPos position;

  public SwitchMachineMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final SwitchMachineMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final SwitchMachineMessage decode(final PacketBuffer buf){
    return new SwitchMachineMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final SwitchMachineMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileWorkMachine tile = MinecraftUtility.getTileEntity(message.position, world, TileWorkMachine.class);
          if(tile != null){
            tile.toggleRun();
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
