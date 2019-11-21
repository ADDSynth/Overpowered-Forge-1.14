package addsynth.core.gameplay.music_box.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class NoteMessage {

  private BlockPos position;
  private byte frame;
  private byte track;
  private boolean on;
  private byte instrument;
  private byte note;
  private float volume;

  public NoteMessage(BlockPos position, byte frame, byte track, byte instrument, byte note, float volume){
    this.position = position;
    this.frame = frame;
    this.track = track;
    this.on = true;
    this.instrument = instrument;
    this.note = note;
    this.volume = volume;
  }

  public NoteMessage(BlockPos position, byte frame, byte track){
    this.position = position;
    this.frame = frame;
    this.track = track;
    this.on = false;
  }

  public static final void encode(final NoteMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeBoolean(message.on);
    buf.writeByte(message.frame);
    buf.writeByte(message.track);
    buf.writeByte(message.instrument);
    buf.writeByte(message.note);
    buf.writeFloat(message.volume);
  }

  public static final NoteMessage decode(final PacketBuffer buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    if(buf.readBoolean()){
      return new NoteMessage(position, buf.readByte(), buf.readByte(), buf.readByte(), buf.readByte(), buf.readFloat());
    }
    return new NoteMessage(position, buf.readByte(), buf.readByte());
  }

  public static void handle(final NoteMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.getServerWorld();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileMusicBox tile = MinecraftUtility.getTileEntity(message.position,world,TileMusicBox.class);
          if(tile != null){
            if(message.on){
              tile.set_note(message.track, message.frame, message.note);
            }
            else{
              tile.disable_note(message.track, message.frame);
            }
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
