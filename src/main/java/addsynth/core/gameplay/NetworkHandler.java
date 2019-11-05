package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class NetworkHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ADDSynthCore.MOD_ID);

  public static final void registerMessages(){
    INSTANCE.registerMessage(MusicBoxMessage.Handler.class,          MusicBoxMessage.class,          0, Side.SERVER);
    INSTANCE.registerMessage(NoteMessage.Handler.class,              NoteMessage.class,              1, Side.SERVER);
  }
}
