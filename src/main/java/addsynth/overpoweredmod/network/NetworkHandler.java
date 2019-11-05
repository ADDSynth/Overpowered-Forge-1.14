package addsynth.overpoweredmod.network;

import addsynth.energy.network.server_messages.*;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.network.client_messages.*;
import addsynth.overpoweredmod.network.laser.*;
import addsynth.overpoweredmod.network.server_messages.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

// http://mcforge.readthedocs.io/en/latest/networking/simpleimpl/

public final class NetworkHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(OverpoweredMod.MOD_ID);

  public static final void registerMessages(){
    Debug.log_setup_info("Begin Registering Network Messages...");
    INSTANCE.registerMessage(CycleGemConverterMessage.Handler.class,  CycleGemConverterMessage.class,  0, Side.SERVER);
    INSTANCE.registerMessage(SetLaserDistanceMessage.Handler.class,   SetLaserDistanceMessage.class,   1, Side.SERVER);
    INSTANCE.registerMessage(LaserClientSyncMessage.Handler.class,    LaserClientSyncMessage.class,    2, Side.CLIENT);
    INSTANCE.registerMessage(PortalControlMessage.Handler.class,      PortalControlMessage.class,      3, Side.SERVER);
    INSTANCE.registerMessage(SwitchMachineMessage.Handler.class,      SwitchMachineMessage.class,      4, Side.SERVER);
    INSTANCE.registerMessage(SyncPortalDataMessage.Handler.class,     SyncPortalDataMessage.class,     5, Side.CLIENT);
    INSTANCE.registerMessage(ToggleLaserShutoffMessage.Handler.class, ToggleLaserShutoffMessage.class, 6, Side.SERVER);
    INSTANCE.registerMessage(CycleTransferModeMessage.Handler.class,  CycleTransferModeMessage.class,  7, Side.SERVER);
    Debug.log_setup_info("Finished Registering Network Messages.");
  }

}
