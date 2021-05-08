package addsynth.overpoweredmod.game;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ToggleAutoShutoffMessage {

  private final BlockPos position;

  public ToggleAutoShutoffMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final ToggleAutoShutoffMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final ToggleAutoShutoffMessage decode(final PacketBuffer buf){
    return new ToggleAutoShutoffMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final ToggleAutoShutoffMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
          if(tile != null){
            tile.toggle_auto_shutoff();
          }
          // OPTIMIZE: Toggling Auto Shutoff is now a common feature amongst machines. Make this a feature
          //           of Manual Machines and an IAutoShutoff interface and move to ADDSynth Energy!
          //           And also save an AutoShutoffCheckbox class that sends the Network message. We can
          //           instantiate a copy whenever we need to.
          final TilePortalControlPanel tile2 = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
          if(tile2 != null){
            tile2.toggle_auto_shutoff();
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
