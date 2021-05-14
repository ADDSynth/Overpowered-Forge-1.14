package addsynth.energy.gameplay.circuit_fabricator;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ChangeCircuitCraftType {

  private final BlockPos position;
  private final int circuit_id;

  public ChangeCircuitCraftType(final BlockPos position, final int circuit_id){
    this.position = position;
    this.circuit_id = circuit_id;
  }

  public static final void encode(final ChangeCircuitCraftType message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.circuit_id);
  }

  public static final ChangeCircuitCraftType decode(final PacketBuffer buf){
    return new ChangeCircuitCraftType(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()), buf.readInt());
  }

  public static void handle(final ChangeCircuitCraftType message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileCircuitFabricator tile = MinecraftUtility.getTileEntity(message.position, world, TileCircuitFabricator.class);
          if(tile != null){
            tile.change_circuit_craft(message.circuit_id, true);
            tile.ejectInvalidItems(player);
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
