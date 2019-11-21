package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class NetworkHandler {

  private static final String PROTOCAL_VERSION = "1";

  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
    new ResourceLocation(ADDSynthCore.MOD_ID, "main_network_channel"),
    () -> PROTOCAL_VERSION, PROTOCAL_VERSION::equals, PROTOCAL_VERSION::equals
  );

  public static final void registerMessages(){
    INSTANCE.registerMessage(0,MusicBoxMessage.class,MusicBoxMessage::encode,MusicBoxMessage::decode,MusicBoxMessage::handle);
    INSTANCE.registerMessage(1,NoteMessage.class,NoteMessage::encode,NoteMessage::decode,NoteMessage::handle);
  }
}
