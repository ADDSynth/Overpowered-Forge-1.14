package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.network_messages.ChangeInstrumentMessage;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerSyncMessage;
import addsynth.core.gameplay.team_manager.network_messages.RequestPlayerScoreMessage;
import addsynth.core.gameplay.team_manager.network_messages.PlayerScoreMessage;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
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
    ADDSynthCore.log.info("Begin registering ADDSynthCore network messages...");
    INSTANCE.registerMessage(0,
      MusicBoxMessage.class,
      MusicBoxMessage::encode,
      MusicBoxMessage::decode,
      MusicBoxMessage::handle
    );
    INSTANCE.registerMessage(1,
      NoteMessage.class,
      NoteMessage::encode,
      NoteMessage::decode,
      NoteMessage::handle
    );
    INSTANCE.registerMessage(2,
      ChangeInstrumentMessage.class,
      ChangeInstrumentMessage::encode,
      ChangeInstrumentMessage::decode,
      ChangeInstrumentMessage::handle
    );
    INSTANCE.registerMessage(3,
      TeamManagerSyncMessage.class,
      TeamManagerSyncMessage::encode,
      TeamManagerSyncMessage::decode,
      TeamManagerSyncMessage::handle
    );
    INSTANCE.registerMessage(4,
      TeamManagerCommand.class,
      TeamManagerCommand::encode,
      TeamManagerCommand::decode,
      TeamManagerCommand::handle
    );
    INSTANCE.registerMessage(5,
      RequestPlayerScoreMessage.class,
      RequestPlayerScoreMessage::encode,
      RequestPlayerScoreMessage::decode,
      RequestPlayerScoreMessage::handle
    );
    INSTANCE.registerMessage(6,
      PlayerScoreMessage.class,
      PlayerScoreMessage::encode,
      PlayerScoreMessage::decode,
      PlayerScoreMessage::handle
    );
    ADDSynthCore.log.info("Done registering ADDSynthCore network messages.");
  }
}
