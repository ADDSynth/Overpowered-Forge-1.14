package addsynth.core.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class NetworkUtil {

  public static final void writeBlockPos(final PacketBuffer data, final BlockPos pos){
    data.writeInt(pos.getX());
    data.writeInt(pos.getY());
    data.writeInt(pos.getZ());
  }

  public static final BlockPos readBlockPos(final PacketBuffer data){
    return new BlockPos(data.readInt(), data.readInt(), data.readInt());
  }

  public static final void writeBlockPositions(final PacketBuffer data, final BlockPos[] positions){
    data.writeInt(positions.length);
    for(final BlockPos pos : positions){
      writeBlockPos(data, pos);
    }
  }

  public static final BlockPos[] readBlockPositions(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final BlockPos[] positions = new BlockPos[length];
    for(i = 0; i < length; i++){
      positions[i] = readBlockPos(data);
    }
    return positions;
  }

  public static final String readString(final ByteBuf byte_data){
    final int string_length = byte_data.readInt();
    int i;
    final StringBuilder string_message = new StringBuilder();
    for(i = 0; i < string_length; i++){
      string_message.append(byte_data.readChar());
    }
    return string_message.toString();
  }

  public static final void writeString(final ByteBuf data, final String string){
    data.writeInt(string.length());
    int i;
    for(i = 0; i <string.length(); i++){
      data.writeChar(string.charAt(i));
    }
  }

  public static final void send_to_clients_in_world(final SimpleChannel network, final World world, final Object message){
    network.send(PacketDistributor.DIMENSION.with(() -> world.dimension.getType()), message);
  }

}
