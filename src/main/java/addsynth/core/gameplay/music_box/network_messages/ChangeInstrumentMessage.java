package addsynth.core.gameplay.music_box.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ChangeInstrumentMessage {

  private BlockPos position;
  private byte track;
  private byte instrument;

  public ChangeInstrumentMessage(final BlockPos position, final byte track, final byte instrument){
    this.position = position;
    this.track = track;
    this.instrument = instrument;
  }

  public static final void encode(final ChangeInstrumentMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeByte(message.track);
    buf.writeByte(message.instrument);
  }

  public static final ChangeInstrumentMessage decode(final PacketBuffer buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    return new ChangeInstrumentMessage(position, buf.readByte(), buf.readByte());
  }

  public static void handle(final ChangeInstrumentMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileMusicBox music_box = MinecraftUtility.getTileEntity(message.position, world, TileMusicBox.class);
          if(music_box != null){
            music_box.change_track_instrument(message.track, message.instrument);
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
