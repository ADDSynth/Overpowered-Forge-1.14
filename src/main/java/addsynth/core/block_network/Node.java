package addsynth.core.block_network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class Node {

  public BlockPos position;
  protected TileEntity tile;

  public Node(final BlockPos position, final TileEntity tile){
    this.position = position;
    this.tile = tile;
  }

  public boolean isInvalid(){
    return (tile == null || position == null) ? true : (tile.isInvalid() || !tile.getPos().equals(position));
  }

  public TileEntity getTile(){
    return tile;
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+"}";
  }

}
