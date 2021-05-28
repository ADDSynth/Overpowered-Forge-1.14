package addsynth.core.util.entity;

import net.minecraft.entity.Entity;

public final class MobUtil {

  public static final void setPosition(final Entity entity, final double x, final double y, final double z){
    entity.setPosition(x, y, z); // also updates bounding box
    entity.prevPosX = x;
    entity.prevPosY = y;
    entity.prevPosZ = z;
    entity.lastTickPosX = x;
    entity.lastTickPosY = y;
    entity.lastTickPosZ = z;
  }

  /**
   * @param entity
   * @param direction must be a float value from 0.0f to 359.999999f
   * @see net.minecraft.util.Direction#getHorizontalAngle()
   */
  public static final void setEntityFacingDirection(final Entity entity, final float direction){
    entity.rotationYaw = direction;
    entity.prevRotationYaw = direction;
    entity.setRotationYawHead(direction);
    entity.setRenderYawOffset(direction);
  }

}
