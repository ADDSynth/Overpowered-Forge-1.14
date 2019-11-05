package addsynth.overpoweredmod.tiles.technical;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public final class TileLaserBeam extends TileEntity implements ITickable {

  private static final int max_life = 10;
  private int life = max_life;

  // I tested whether I could do the same function as this TileEntity with just a Blockstate Integer
  //   Property. In short, yes I can, but you have to manually schedule a block update using the
  //   world.scheduleUpdate() function. In retrospect, doing it this way does not reduce any lag caused
  //   by the LaserBeam block having light. So it's just easier to keep this as a TileEntity for now.

  @Override
  public final void update(){
    if(world.isRemote == false){
      life -= 1;
      if(life <= 0){
        world.setBlockToAir(this.pos);
      }
    }
  }

}
