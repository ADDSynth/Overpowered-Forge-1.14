package addsynth.core.util.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import addsynth.core.ADDSynthCore;
import addsynth.core.block_network.Node;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockUtil {

  /** Standard search algorithm. Checks current block and adjacent blocks against the predicate you specify.
   *  If the predicate returns true, the {@link Node} is added to a list and then the list is returned.
   * @param from Starting Position. Predicate must return true otherwise an empty list is returned.
   * @param world
   * @param predicate The function that tests the Node.
   */
  public static final HashSet<Node> find_blocks(final BlockPos from, final World world, final Predicate<Node> predicate){
    return find_blocks(from, world, predicate, null);
  }

  /** Standard search algorithm. Checks current block and adjacent blocks against the predicate you specify.
   *  If the predicate returns true, the {@link Node} is added to a list and then the list is returned. This
   *  version has an additional Consumer argument which allows you to run additional code on all blocks searched.
   * @param from Starting Position. Predicate must return true otherwise an empty list is returned.
   * @param world
   * @param predicate The function that tests the Node.
   * @param consumer Supply a function that takes a Node as an argument. Allows you to run additional code on all Nodes searched.
   */
  public static final HashSet<Node> find_blocks(final BlockPos from, final World world, final Predicate<Node> predicate, final Consumer<Node> consumer){
    final HashSet<Node> list = new HashSet<>(100);
    try{
      final ArrayList<BlockPos> searched = new ArrayList<>(500);
      searched.add(from);
      if(check(from, list, world, predicate, consumer)){
        search(from, searched, list, world, predicate, consumer);
      }
    }
    catch(StackOverflowError e){
      ADDSynthCore.log.fatal("Search algorithm in "+BlockUtil.class.getName()+" looped forever! We're sorry about that! "+
        "(someone's code is doing something they're not supposed to.)", e);
    }
    catch(Exception e){
      ADDSynthCore.log.fatal("Error in "+BlockUtil.class.getSimpleName()+".search() algorithm.", e);
    }
    return list;
  }

  private static final void search
  (BlockPos from, ArrayList<BlockPos> searched, HashSet<Node> list, World world, Predicate<Node> predicate, Consumer<Node> consumer){
    BlockPos position;
    for(final Direction side : Direction.values()){
      position = from.offset(side);
      if(searched.contains(position) == false){
        searched.add(position);
        if(check(position, list, world, predicate, consumer)){
          search(position, searched, list, world, predicate, consumer);
        }
      }
    }
  }

  private static final boolean check(BlockPos position, HashSet<Node> list, World world, Predicate<Node> predicate, Consumer<Node> consumer){
    final Node node = new Node(position, world);
    if(consumer != null){
      consumer.accept(node);
    }
    if(predicate.test(node)){
      list.add(node);
      return true;
    }
    return false;
  }

}
