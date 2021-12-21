package addsynth.overpoweredmod.machines.laser.network_messages;

import java.util.List;
import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.network.NetworkUtil;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public final class LaserClientSyncMessage {

  private final BlockPos[] positions;
  private final int number_of_lasers;

  public LaserClientSyncMessage(final BlockPos[] positions, final int number_of_lasers){
    this.positions = positions;
    this.number_of_lasers = number_of_lasers;
  }

  public LaserClientSyncMessage(final List<BlockPos> positions, final int number_of_lasers){
    this(positions.toArray(new BlockPos[positions.size()]), number_of_lasers);
  }

  public static final void encode(final LaserClientSyncMessage message, final PacketBuffer buf){
    NetworkUtil.writeBlockPositions(buf, message.positions);
    buf.writeInt(message.number_of_lasers);
  }

  public static final LaserClientSyncMessage decode(final PacketBuffer buf){
    return new LaserClientSyncMessage(NetworkUtil.readBlockPositions(buf), buf.readInt());
  }

  public static final void handle(final LaserClientSyncMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    context.enqueueWork(() -> {

      @SuppressWarnings("resource")
      final Minecraft minecraft = Minecraft.getInstance();
      final World world = minecraft.player.world;

      TileLaserHousing tile;
      for(final BlockPos pos : message.positions){
        if(world.isAreaLoaded(pos, 0)){
          tile = MinecraftUtility.getTileEntity(pos, world, TileLaserHousing.class);
          if(tile != null){
            tile.number_of_lasers = message.number_of_lasers;
          }
        }
      }
    });
    context.setPacketHandled(true);
  }

}
