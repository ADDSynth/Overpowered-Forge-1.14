package addsynth.core.util.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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

  /** You can use either this method or {@link PacketBuffer#writeString(String)} method,
   *  however keep in mind that the vanilla method is limited to strings of length 32767.
   * @param data
   * @param string
   */
  public static final void writeString(final PacketBuffer data, final String string){
    final int length = data.readInt();
    data.writeInt(length);
    int i;
    for(i = 0; i < length; i++){
      data.writeChar(string.charAt(i));
    }
  }

  /** You MUST use this to read strings! You cannot use the vanilla method
   *  because it is marked with the ClientOnly annotation. If you try to use
   *  that on the server side you'll crash the server.
   * @param data
   * @return String
   */
  public static final String readString(final PacketBuffer data){
    final int string_length = data.readInt();
    int i;
    final StringBuilder string_message = new StringBuilder();
    for(i = 0; i < string_length; i++){
      string_message.append(data.readChar());
    }
    return string_message.toString();
  }

  public static final void writeStringArray(final PacketBuffer data, final String[] string_array){
    data.writeInt(string_array.length);
    for(final String s : string_array){
      writeString(data, s);
    }
  }

  public static final String[] readStringArray(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final String[] strings = new String[length];
    for(i = 0; i < length; i++){
      strings[i] = readString(data);
    }
    return strings;
  }

  public static final void writeTextComponentArray(final PacketBuffer data, final ITextComponent[] text_component_array){
    data.writeInt(text_component_array.length);
    for(final ITextComponent t : text_component_array){
      data.writeString(t.getFormattedText());
    }
  }

  public static final ITextComponent[] readTextComponentArray(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final ITextComponent[] text_components = new ITextComponent[length];
    for(i = 0; i < length; i++){
      text_components[i] = new StringTextComponent(readString(data));
    }
    return text_components;
  }

  /** Sends the Network message to all clients in the world you specify.
   *  Must be called on the server side.
   * @param network
   * @param world
   * @param message
   */
  public static final void send_to_clients_in_world(final SimpleChannel network, final World world, final Object message){
    network.send(PacketDistributor.DIMENSION.with(() -> world.dimension.getType()), message);
  }

}
