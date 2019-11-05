package addsynth.overpoweredmod.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * The Teleporter class is what spawns the Nether Portal (if going to/from the Nether) and the End spawn location,
 * depending on which dimension id you're going to.
 * Since using the default Teleporter will try to spawn a Nether Portal for all dimensions that are not The End,
 * I create my own Teleporter and override all relevant functions so they don't do anything, because I just want
 * to teleport the player to and from the Unknown Dimension.
 */
public final class CustomTeleporter extends Teleporter {

  private final BlockPos spawn = WeirdWorldProvider.spawn;

  public CustomTeleporter(final WorldServer worldIn){
    super(worldIn);
  }

  @Override
  public void placeInPortal(final Entity entity, final float rotationYaw){
    if(world.provider.getDimension() == WeirdDimension.id){
      set_entity_position(entity, spawn.getX(), spawn.getY(), spawn.getZ());
    }
    else{
      final int x = (int)Math.round(entity.posX);
      final int z = (int)Math.round(entity.posZ);
      // bypasses world.getHeight() function I was using earlier.
      final int y = world.getChunk(entity.chunkCoordX, entity.chunkCoordZ).getHeightValue(x & 15, z & 15);
      set_entity_position(entity, x + 0.5, y, z + 0.5);
    }
  }

  private static final void set_entity_position(final Entity entity, final double x, final double y, final double z){
    final float yaw = entity.rotationYaw;
    final float pitch = entity.rotationPitch;
    if(entity instanceof EntityPlayerMP){
      // TODO: set players to face North.
      ((EntityPlayerMP)entity).connection.setPlayerLocation(x, y, z, yaw, pitch);
      return;
    }
    entity.setPositionAndRotation(x, y, z, yaw, pitch);
  }

}
