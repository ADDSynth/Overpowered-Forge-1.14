package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.machines.circuit_fabricator.ChangeCircuitCraftType;
import addsynth.energy.gameplay.machines.universal_energy_interface.CycleTransferModeMessage;
import addsynth.energy.lib.network_messages.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class NetworkHandler {

  private static final String PROTOCAL_VERSION = "1";

  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
    new ResourceLocation(ADDSynthEnergy.MOD_ID, "main_network_channel"),
    () -> PROTOCAL_VERSION, PROTOCAL_VERSION::equals, PROTOCAL_VERSION::equals
  );

  public static final void registerMessages(){
    INSTANCE.registerMessage(0,
      SwitchMachineMessage.class,
      SwitchMachineMessage::encode,
      SwitchMachineMessage::decode,
      SwitchMachineMessage::handle
    );
    INSTANCE.registerMessage(1,
      CycleTransferModeMessage.class,
      CycleTransferModeMessage::encode,
      CycleTransferModeMessage::decode,
      CycleTransferModeMessage::handle
    );
    INSTANCE.registerMessage(2,
      ToggleAutoShutoffMessage.class,
      ToggleAutoShutoffMessage::encode,
      ToggleAutoShutoffMessage::decode,
      ToggleAutoShutoffMessage::handle
    );
    INSTANCE.registerMessage(3,
      ChangeCircuitCraftType.class,
      ChangeCircuitCraftType::encode,
      ChangeCircuitCraftType::decode,
      ChangeCircuitCraftType::handle
    );
  }

}
