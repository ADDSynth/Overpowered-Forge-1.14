package addsynth.energy.api.network_messages;

import java.util.function.Supplier;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.api.tiles.machines.ISwitchableMachine;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class SwitchMachineMessage {

  private final BlockPos position;

  public SwitchMachineMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final SwitchMachineMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final SwitchMachineMessage decode(final PacketBuffer buf){
    return new SwitchMachineMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final SwitchMachineMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileEntity tile = world.getTileEntity(message.position);
          if(tile != null){
            if(tile instanceof ISwitchableMachine){
              ((ISwitchableMachine)tile).togglePowerSwitch();
            }
            else{
              ADDSynthEnergy.log.warn(
                "A "+SwitchMachineMessage.class.getSimpleName()+" network message was sent to the Server."+
                "Cannot toggle the Power Switch for TileEntity '"+tile.getClass().getSimpleName()+"' at "+
                "position "+message.position+" because"+
                "it is does not implement the "+ISwitchableMachine.class.getSimpleName()+" interface."
              );
            }
          }
          else{
            ADDSynthEnergy.log.warn(new NullPointerException("No TileEntity exists at location: "+message.position+"."));
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
