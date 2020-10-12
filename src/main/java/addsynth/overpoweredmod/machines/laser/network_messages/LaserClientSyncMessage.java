package addsynth.overpoweredmod.machines.laser.network_messages;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class LaserClientSyncMessage {

  private final BlockPos position;
  private final int number_of_lasers;

  public LaserClientSyncMessage(final BlockPos position, final int number_of_lasers){
    this.position = position;
    this.number_of_lasers = number_of_lasers;
  }

  public static final void encode(final LaserClientSyncMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.number_of_lasers);
  }

  public static final LaserClientSyncMessage decode(final PacketBuffer buf){
    return new LaserClientSyncMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readInt());
  }

  public static final void handle(final LaserClientSyncMessage message, final Supplier<NetworkEvent.Context> context){
    context.get().enqueueWork(() -> {
      @SuppressWarnings("resource") final Minecraft minecraft = Minecraft.getInstance();
      final World world = minecraft.player.world;
      if(world.isAreaLoaded(message.position, 0)){
        final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
        if(tile != null){
          tile.number_of_lasers = message.number_of_lasers;
        }
      }
    });
    context.get().setPacketHandled(true);
  }

}
