package addsynth.overpoweredmod.machines.portal.rift;

import addsynth.core.tiles.TileBase;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;

public final class TilePortal extends TileBase implements ITickableTileEntity {

  private int count = 0;
  private static final int life = 800;

  public TilePortal(){
    super(Tiles.PORTAL_BLOCK);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      count += 1;
      if(count >= life){
        world.removeBlock(pos, false);
      }
    }
  }

}
