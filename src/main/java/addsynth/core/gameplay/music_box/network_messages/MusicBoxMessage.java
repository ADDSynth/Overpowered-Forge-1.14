package addsynth.core.gameplay.music_box.network_messages;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.MinecraftUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class MusicBoxMessage implements IMessage {

  private BlockPos position;
  private TileMusicBox.Command command;
  private byte info;

  public MusicBoxMessage(){}

  public MusicBoxMessage(final BlockPos position, final TileMusicBox.Command command){
    this(position, command, 0);
  }

  public MusicBoxMessage(final BlockPos position, final TileMusicBox.Command command, final int data){
    this.position = position;
    this.command = command;
    this.info = (byte)data;
  }

  @Override
  public final void fromBytes(final ByteBuf buf){
    position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    command = TileMusicBox.Command.value[buf.readInt()];
    info = buf.readByte();
  }

  @Override
  public final void toBytes(final ByteBuf buf){
    buf.writeInt(position.getX());
    buf.writeInt(position.getY());
    buf.writeInt(position.getZ());
    buf.writeInt(command.ordinal());
    buf.writeByte(info);
  }

  public static final class Handler implements IMessageHandler<MusicBoxMessage, IMessage> {

    @Override
    public IMessage onMessage(MusicBoxMessage message, MessageContext context) {
      final WorldServer world = context.getServerHandler().player.getServerWorld();
      world.addScheduledTask(() -> processMessage(world, message));
      return null;
    }
    
    private static final void processMessage(final WorldServer world, final MusicBoxMessage message){
      if(world.isBlockLoaded(message.position)){
        final TileMusicBox music_box = MinecraftUtility.getTileEntity(message.position, world, TileMusicBox.class);
        if(music_box != null){
          // each of these individual functions updates the tile data, so you'd think It would be
          // better to just call it once after this switch statement? But the play() function is
          // also called in the TileMusicBox itself! so it MUST call update_data() in the function.
          switch(message.command){
          case PLAY:                    music_box.play(false); break;
          case CHANGE_TEMPO:            music_box.change_tempo(message.info > 0); break;
          case CYCLE_NEXT_DIRECTION:    music_box.increment_next_direction(); break;
          case CHANGE_TRACK_INSTRUMENT: music_box.change_track_instrument(message.info); break;
          case TOGGLE_MUTE:             music_box.toggle_mute(message.info); break;
          }
        }
      }
    }
  }

}
