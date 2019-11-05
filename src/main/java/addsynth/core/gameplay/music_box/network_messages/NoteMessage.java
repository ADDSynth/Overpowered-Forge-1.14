package addsynth.core.gameplay.music_box.network_messages;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.MinecraftUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class NoteMessage implements IMessage {

  private BlockPos position;
  private byte frame;
  private byte track;
  private boolean on;
  private byte instrument;
  private byte note;
  private float volume;

  public NoteMessage(){}

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

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    on = buf.readBoolean();
    frame = buf.readByte();
    track = buf.readByte();
    instrument = buf.readByte();
    note = buf.readByte();
    volume = buf.readFloat();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeBoolean(on);
    buf.writeByte(frame);
    buf.writeByte(track);
    buf.writeByte(instrument);
    buf.writeByte(note);
    buf.writeFloat(volume);
  }

  public static final class Handler implements IMessageHandler<NoteMessage, IMessage> {

    @Override
    public IMessage onMessage(NoteMessage message, MessageContext context) {
      final WorldServer world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final WorldServer world, final NoteMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileMusicBox tile = MinecraftUtility.getTileEntity(message.position,world,TileMusicBox.class);
        if(tile != null){
          if(message.on){
            tile.set_note(message.track, message.frame,message.note);
          }
          else{
            tile.disable_note(message.track, message.frame);
          }
        }
      }
    }
  }

}
