package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.machines.gem_converter.CycleGemConverterMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.ToggleLaserShutoffMessage;
import addsynth.overpoweredmod.machines.portal.control_panel.GeneratePortalMessage;
import addsynth.overpoweredmod.machines.portal.control_panel.SyncPortalDataMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

// http://mcforge.readthedocs.io/en/latest/networking/simpleimpl/

public final class NetworkHandler {

  private static final String PROTOCAL_VERSION = "1";

  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
    new ResourceLocation(OverpoweredMod.MOD_ID, "main_network_channel"),
    () -> PROTOCAL_VERSION, PROTOCAL_VERSION::equals, PROTOCAL_VERSION::equals
  );

  public static final void registerMessages(){
    Debug.log_setup_info("Begin Registering Network Messages...");

    INSTANCE.registerMessage(0,
      CycleGemConverterMessage.class,
      CycleGemConverterMessage::encode,
      CycleGemConverterMessage::decode,
      CycleGemConverterMessage::handle
    );
    INSTANCE.registerMessage(1,
      SetLaserDistanceMessage.class,
      SetLaserDistanceMessage::encode,
      SetLaserDistanceMessage::decode,
      SetLaserDistanceMessage::handle
    );
    INSTANCE.registerMessage(2,
      LaserClientSyncMessage.class,
      LaserClientSyncMessage::encode,
      LaserClientSyncMessage::decode,
      LaserClientSyncMessage::handle
    );
    INSTANCE.registerMessage(3,
      GeneratePortalMessage.class,
      GeneratePortalMessage::encode,
      GeneratePortalMessage::decode,
      GeneratePortalMessage::handle
    );
    INSTANCE.registerMessage(4,
      SyncPortalDataMessage.class,
      SyncPortalDataMessage::encode,
      SyncPortalDataMessage::decode,
      SyncPortalDataMessage::handle
    );
    INSTANCE.registerMessage(5,
      ToggleLaserShutoffMessage.class,
      ToggleLaserShutoffMessage::encode,
      ToggleLaserShutoffMessage::decode,
      ToggleLaserShutoffMessage::handle
    );

    Debug.log_setup_info("Finished Registering Network Messages.");
  }

}
