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
    INSTANCE.registerMessage(7,
      TeamManagerCommand.AddTeam.class,
      TeamManagerCommand.AddTeam::encode,
      TeamManagerCommand.AddTeam::decode,
      TeamManagerCommand.AddTeam::handle
    );
    INSTANCE.registerMessage(8,
      TeamManagerCommand.EditTeam.class,
      TeamManagerCommand.EditTeam::encode,
      TeamManagerCommand.EditTeam::decode,
      TeamManagerCommand.EditTeam::handle
    );
    INSTANCE.registerMessage(9,
      TeamManagerCommand.DeleteTeam.class,
      TeamManagerCommand.DeleteTeam::encode,
      TeamManagerCommand.DeleteTeam::decode,
      TeamManagerCommand.DeleteTeam::handle
    );
    INSTANCE.registerMessage(10,
      TeamManagerCommand.AddPlayerToTeam.class,
      TeamManagerCommand.AddPlayerToTeam::encode,
      TeamManagerCommand.AddPlayerToTeam::decode,
      TeamManagerCommand.AddPlayerToTeam::handle
    );
    INSTANCE.registerMessage(11,
      TeamManagerCommand.RemovePlayerFromTeam.class,
      TeamManagerCommand.RemovePlayerFromTeam::encode,
      TeamManagerCommand.RemovePlayerFromTeam::decode,
      TeamManagerCommand.RemovePlayerFromTeam::handle
    );
    INSTANCE.registerMessage(12,
      TeamManagerCommand.AddObjective.class,
      TeamManagerCommand.AddObjective::encode,
      TeamManagerCommand.AddObjective::decode,
      TeamManagerCommand.AddObjective::handle
    );
    INSTANCE.registerMessage(13,
      TeamManagerCommand.EditObjective.class,
      TeamManagerCommand.EditObjective::encode,
      TeamManagerCommand.EditObjective::decode,
      TeamManagerCommand.EditObjective::handle
    );
    INSTANCE.registerMessage(14,
      TeamManagerCommand.DeleteObjective.class,
      TeamManagerCommand.DeleteObjective::encode,
      TeamManagerCommand.DeleteObjective::decode,
      TeamManagerCommand.DeleteObjective::handle
    );
    INSTANCE.registerMessage(15,
      TeamManagerCommand.SetScore.class,
      TeamManagerCommand.SetScore::encode,
      TeamManagerCommand.SetScore::decode,
      TeamManagerCommand.SetScore::handle
    );
    INSTANCE.registerMessage(16,
      TeamManagerCommand.AddScore.class,
      TeamManagerCommand.AddScore::encode,
      TeamManagerCommand.AddScore::decode,
      TeamManagerCommand.AddScore::handle
    );
    INSTANCE.registerMessage(17,
      TeamManagerCommand.SubtractScore.class,
      TeamManagerCommand.SubtractScore::encode,
      TeamManagerCommand.SubtractScore::decode,
      TeamManagerCommand.SubtractScore::handle
    );
    INSTANCE.registerMessage(18,
      TeamManagerCommand.ResetScore.class,
      TeamManagerCommand.ResetScore::encode,
      TeamManagerCommand.ResetScore::decode,
      TeamManagerCommand.ResetScore::handle
    );
    INSTANCE.registerMessage(19,
      TeamManagerCommand.SetDisplaySlot.class,
      TeamManagerCommand.SetDisplaySlot::encode,
      TeamManagerCommand.SetDisplaySlot::decode,
      TeamManagerCommand.SetDisplaySlot::handle
    );
    INSTANCE.registerMessage(20,
      TeamManagerCommand.ClearDisplaySlot.class,
      TeamManagerCommand.ClearDisplaySlot::encode,
      TeamManagerCommand.ClearDisplaySlot::decode,
      TeamManagerCommand.ClearDisplaySlot::handle
    );
    ADDSynthCore.log.info("Done registering ADDSynthCore network messages.");
  }
}
