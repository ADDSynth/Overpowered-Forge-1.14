package addsynth.core.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public final class NetworkUtil {

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

  public static final void send_to_clients_in_world(final SimpleNetworkWrapper network, final World world, final IMessage message){
    network.sendToDimension(message, world.provider.getDimension());
  }

}
