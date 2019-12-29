package addsynth.overpoweredmod.machines.portal;

import java.util.function.Supplier;
import addsynth.core.util.MinecraftUtility;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class PortalControlMessage {

  private final BlockPos position;
  private final int action;

  public PortalControlMessage(final BlockPos position, final int action){
    this.position = position;
    this.action = action;
  }

  public static final void encode(final PortalControlMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.action);
  }

  public static final PortalControlMessage decode(final PacketBuffer buf){
    return new PortalControlMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readInt());
  }

  public static void handle(final PortalControlMessage message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      context.get().enqueueWork(() -> {
        final ServerWorld world = player.getServerWorld();
        if(world.isAreaLoaded(message.position, 0)){
          final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
          if(tile != null){
            switch(message.action){
            case 0: tile.check_portal(); break;
            case 1: tile.generate_portal(); break;
            }
          }
        }
      });
      context.get().setPacketHandled(true);
    }
  }

}
