package addsynth.core.block_network;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class NodeList extends HashSet<Node> {

  public NodeList(){
    super(100);
  }

  public NodeList(final int size){
    super(size);
  }

  public final void setFrom(final HashSet<Node> hash_set){
    addAll(hash_set);
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
    TileEntity tile;
    for(final Node node : this){
      tile = node.getTile();
      if(tile != null){
        if(node.isInvalid() == false){
          tiles.add(tile);
        }
      }
    }
    return tiles;
  }

  public final boolean contains(final TileEntity tile){
    for(final Node node : this){
      if(node.getTile() == tile){
        return true;
      }
    }
    return false;
  }

  public final boolean contains(final BlockPos position){
    for(final Node node : this){
      if(node.position.equals(position)){
        return true;
      }
    }
    return false;
  }

  public final void remove_invalid(){
    removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  @SuppressWarnings({"unchecked", "null"})
  public final void setBlockNetwork(final BlockNetwork network){
    remove_invalid();
    forEach((Node node) -> {
      if(node.getTile() != null){
        ((IBlockNetworkUser)node.getTile()).setBlockNetwork(network);
      }
    });
  }

}
