package addsynth.overpoweredmod.tiles.technical;

import addsynth.core.tiles.TileBase;
import net.minecraft.util.ITickable;

public final class TilePortal extends TileBase implements ITickable {

  private int count = 0;
  private static final int life = 800;

  @Override
  public final void update(){
    if(world.isRemote == false){
      count += 1;
      if(count >= life){
        world.setBlockToAir(pos);
      }
    }
  }

}
