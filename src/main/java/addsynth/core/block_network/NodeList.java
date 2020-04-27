package addsynth.core.block_network;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class NodeList extends HashSet<Node> {

  public NodeList(){
    super(100);
  }

  public final ArrayList<BlockPos> getPositions(){
    final ArrayList<BlockPos> positions = new ArrayList<>(100);
    for(final Node node : this){
      if(node.isInvalid() == false){
        positions.add(node.position);
      }
    }
    return positions;
  }

  public final ArrayList<TileEntity> getTileEntities(){
    final ArrayList<TileEntity> tiles = new ArrayList<>(100);
    for(final Node node : this){
      if(node.isInvalid() == false){
        tiles.add(node.getTile());
      }
    }
    return tiles;
  }

}
