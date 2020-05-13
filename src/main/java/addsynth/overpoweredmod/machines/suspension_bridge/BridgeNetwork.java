package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import addsynth.core.Constants;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.util.MathUtility;
import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.NetworkUtil;
import addsynth.core.util.WorldUtil;
import addsynth.energy.Energy;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BridgeNetwork extends BlockNetwork<TileSuspensionBridge> {

  public final Energy energy = new Energy(0,1000);

  private @Nonnull ItemStack lens = ItemStack.EMPTY;
  public int lens_index = -1;

  /** Whether this Energy Suspension Bridge COULD be active. */
  private boolean valid;
  /** Whether this Energy Suspension Bridge is active. */
  private boolean active;

  private boolean valid_shape;
  private int min_x;
  private int min_y;
  private int min_z;
  private int max_x;
  private int max_y;
  private int max_z;

  private final int max_distance = 300;
  private final BridgeMessage[] message = new BridgeMessage[6];
  @SuppressWarnings("unchecked")
  private ArrayList<BlockPos>[] area = new ArrayList[6];
  private Direction.Axis rotate_direction = Direction.Axis.X;

  public BridgeNetwork(final World world, final TileSuspensionBridge first_tile){
    super(world, first_tile);
    energy.set_receive_only();
  }

  public final int get_min_x(){ return min_x; }
  public final int get_min_y(){ return min_y; }
  public final int get_min_z(){ return min_z; }
  public final int get_max_x(){ return max_x; }
  public final int get_max_y(){ return max_y; }
  public final int get_max_z(){ return max_z; }
  public final boolean is_active(){ return active; }

  @Override
  protected final void onUpdateNetworkFinished(){
    // turn off bridge if it's currently on.
    boolean current_state = active;
    set_active(false);

    // determine if we are a rectangle shape!
    check_shape();
    
    // check all directions for EMPTY SPACE and DESTINATION ENERGY BRIDGE
    check_directions();
    set_active(current_state);
  }

  private final void check_shape(){ // TODO: consider moving this to MathUtility, if I ever need to check if a list of positions is in a rectangle shape again in the future.
    valid_shape = true;
    final BlockPos[] positions = MathUtility.get_min_max_positions(blocks.getPositions());
    int x;
    int y;
    int z;
    min_x = positions[0].getX();
    min_y = positions[0].getY();
    min_z = positions[0].getZ();
    max_x = positions[1].getX();
    max_y = positions[1].getY();
    max_z = positions[1].getZ();
    for(z = min_z; z <= max_z && valid_shape; z++){
      for(y = min_y; y <= max_y && valid_shape; y++){
        for(x = min_x; x <= max_x && valid_shape; x++){
          if(world.getBlockState(new BlockPos(x,y,z)).getBlock() != first_tile.getBlockState().getBlock()){
            valid_shape = false;
          }
        }
      }
    }
  }

  private final void check_directions(){
    message[0] = BridgeMessage.PENDING;
    message[1] = BridgeMessage.PENDING;
    message[2] = BridgeMessage.PENDING;
    message[3] = BridgeMessage.PENDING;
    message[4] = BridgeMessage.PENDING;
    message[5] = BridgeMessage.PENDING;
    if(valid_shape){
      int x;
      int y;
      int z;
      final boolean[] obstructed = new boolean[6];

      int index = Constants.DOWN;
      area[index] = new ArrayList<>(max_distance * 2);
      int start_x = min_x;
      int end_x   = max_x;
      int start_y = min_y - 1;
      int end_y   = Math.max(min_y - max_distance, 0);
      int start_z = min_z;
      int end_z   = max_z;
      for(y = start_y; y >= end_y && message[index] == BridgeMessage.PENDING; y--){
        for(z = start_z; z <= end_z && message[index] == BridgeMessage.PENDING; z++){
          for(x = start_x; x <= end_x && message[index] == BridgeMessage.PENDING; x++){
            check_position(index, obstructed, new BlockPos(x,y,z));
          }
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }

      index = Constants.UP;
      area[index] = new ArrayList<>(max_distance * 2);
      start_x = min_x;
      end_x   = max_x;
      start_y = max_y + 1;
      end_y   = Math.min(max_y + max_distance, Constants.world_height - 1);
      start_z = min_z;
      end_z   = max_z;
      for(y = start_y; y <= end_y && message[index] == BridgeMessage.PENDING; y++){
        for(z = start_z; z <= end_z && message[index] == BridgeMessage.PENDING; z++){
          for(x = start_x; x <= end_x && message[index] == BridgeMessage.PENDING; x++){
            check_position(index, obstructed, new BlockPos(x,y,z));
          }
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }

      // The rest of the directions are horizontal. Set y to the maximum height.
      y = max_y;

      index = Constants.NORTH;
      area[index] = new ArrayList<>(max_distance * 2);
      start_x = min_x;
      end_x   = max_x;
      start_z = min_z - 1;
      end_z   = min_z - max_distance;
      for(z = start_z; z >= end_z && message[index] == BridgeMessage.PENDING; z--){
        for(x = start_x; x <= end_x && message[index] == BridgeMessage.PENDING; x++){
          check_position(index, obstructed, new BlockPos(x,y,z));
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }

      index = Constants.SOUTH;
      area[index] = new ArrayList<>(max_distance * 2);
      start_x = min_x;
      end_x   = max_x;
      start_z = max_z + 1;
      end_z   = max_z + max_distance;
      for(z = start_z; z <= end_z && message[index] == BridgeMessage.PENDING; z++){
        for(x = start_x; x <= end_x && message[index] == BridgeMessage.PENDING; x++){
          check_position(index, obstructed, new BlockPos(x,y,z));
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }

      index = Constants.WEST;
      area[index] = new ArrayList<>(max_distance * 2);
      start_x = min_x - 1;
      end_x   = min_x - max_distance;
      start_z = min_z;
      end_z   = max_z;
      for(x = start_x; x >= end_x && message[index] == BridgeMessage.PENDING; x--){
        for(z = start_z; z <= end_z && message[index] == BridgeMessage.PENDING; z++){
          check_position(index, obstructed, new BlockPos(x,y,z));
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }

      index = Constants.EAST;
      area[index] = new ArrayList<>(max_distance * 2);
      start_x = max_x + 1;
      end_x   = max_x + max_distance;
      start_z = min_z;
      end_z   = max_z;
      for(x = start_x; x <= end_x && message[index] == BridgeMessage.PENDING; x++){
        for(z = start_z; z <= end_z && message[index] == BridgeMessage.PENDING; z++){
          check_position(index, obstructed, new BlockPos(x,y,z));
        }
      }
      if(message[index] == BridgeMessage.PENDING){
        message[index] = BridgeMessage.NO_BRIDGE;
      }
    }

    updateBridgeNetwork();
  }

  private final void check_position(final int index, final boolean[] obstructed, final BlockPos position){
    final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(position, world, TileSuspensionBridge.class);
    if(tile == null){
      final Block block = world.getBlockState(position).getBlock();
      if(block == Blocks.AIR || (valid && block instanceof EnergyBridge)){
        area[index].add(position);
      }
      else{
        obstructed[index] = true;
      }
    }
    else{
      final BridgeNetwork other_network = tile.getBlockNetwork();
      if(other_network.check(index, min_x, max_x, min_z, max_z)){
        if(obstructed[index]){
          message[index] = BridgeMessage.OBSTRUCTED;
        }
        else{
          message[index] = BridgeMessage.OKAY;
        }
      }
      else{
        message[index] = BridgeMessage.INVALID_BRIDGE;
      }
    }
  }

  public final boolean check(final int direction, final int min_x, final int max_x, final int min_z, final int max_z){
    if(valid_shape == false){ return false; }
    final boolean length = this.min_z == min_z && this.max_z == max_z;
    final boolean width  = this.min_x == min_x && this.max_x == max_x;
    if(direction == Constants.DOWN || direction == Constants.UP){
      return width && length;
    }
    if(direction == Constants.WEST || direction == Constants.EAST){
      return length;
    }
    if(direction == Constants.NORTH || direction == Constants.SOUTH){
      return width;
    }
    return false;
  }

  /** Called whenever a player inserts or removes a Lens to/from the TileEntity. */
  public final void update_lens(final ItemStack stack){
    // THIS IS ESSENTIAL!!! Otherwise, when we update the TileEntities, and set their InputInventory stack,
    //   that also triggers the onContentsChanged() function!
    if(stack.isItemEqual(this.lens) == false){
      this.lens = stack;
      if(stack.isEmpty()){
        lens_index = -1;
      }
      else{
        lens_index = ((LensItem)stack.getItem()).index;
      }
      updateBridgeNetwork();
    }
  }

  /** This updates all TileEntities in the network whenever something changes that must be propogated to the rest of them. */
  private final void updateBridgeNetwork(){
    TileSuspensionBridge tile;
    remove_invalid_nodes(blocks);
    for(final Node node : blocks){
      tile = (TileSuspensionBridge)node.getTile();
      tile.getInputInventory().setStackInSlot(0, lens);
      final SyncClientBridgeMessage network_message = new SyncClientBridgeMessage(node.position, this.message);
      NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, network_message);
    }
  }

  /** This is run every tick, by every Bridge Tile in the network. But we check that only first_tile executes code. */
  @Override
  public final void tick(final TileSuspensionBridge tile){
    if(tile == first_tile){
      check_shape();
      valid = valid_shape && lens_index >= 0;
      final boolean powered = valid && is_redstone_powered();
      if(powered != active){
        check_directions();
        set_active(powered);
      }
    }
  }

  public final void set_active(final boolean active){
    this.active = active;
    int index;
    for(Direction direction : Direction.values()){
      index = direction.ordinal();
      if(active){
        if(this.message[index] == BridgeMessage.OKAY){
          for(BlockPos position : area[index]){
            if(direction.getAxis() == Direction.Axis.Y){
              switch(lens_index){
              case 0: world.setBlockState(position, Machines.white_energy_bridge.getRotated(rotate_direction));   break;
              case 1: world.setBlockState(position, Machines.red_energy_bridge.getRotated(rotate_direction));     break;
              case 2: world.setBlockState(position, Machines.orange_energy_bridge.getRotated(rotate_direction));  break;
              case 3: world.setBlockState(position, Machines.yellow_energy_bridge.getRotated(rotate_direction));  break;
              case 4: world.setBlockState(position, Machines.green_energy_bridge.getRotated(rotate_direction));   break;
              case 5: world.setBlockState(position, Machines.cyan_energy_bridge.getRotated(rotate_direction));    break;
              case 6: world.setBlockState(position, Machines.blue_energy_bridge.getRotated(rotate_direction));    break;
              case 7: world.setBlockState(position, Machines.magenta_energy_bridge.getRotated(rotate_direction)); break;
              }
            }
            else{
              switch(lens_index){
              case 0: world.setBlockState(position, Machines.white_energy_bridge.getDefaultState());   break;
              case 1: world.setBlockState(position, Machines.red_energy_bridge.getDefaultState());     break;
              case 2: world.setBlockState(position, Machines.orange_energy_bridge.getDefaultState());  break;
              case 3: world.setBlockState(position, Machines.yellow_energy_bridge.getDefaultState());  break;
              case 4: world.setBlockState(position, Machines.green_energy_bridge.getDefaultState());   break;
              case 5: world.setBlockState(position, Machines.cyan_energy_bridge.getDefaultState());    break;
              case 6: world.setBlockState(position, Machines.blue_energy_bridge.getDefaultState());    break;
              case 7: world.setBlockState(position, Machines.magenta_energy_bridge.getDefaultState()); break;
              }
            }
          }
        }
      }
      else{
        if(area[index] != null && message[index] == BridgeMessage.OKAY){
          for(BlockPos position : area[index]){
            WorldUtil.delete_block(world, position);
          }
        }
      }
    }
  }

  @SuppressWarnings("incomplete-switch")
  public final void rotate(){
    switch(rotate_direction){
    case X: rotate_direction = Direction.Axis.Z; break;
    case Z: rotate_direction = Direction.Axis.X; break;
    }
    set_active(active);
  }

  @Override
  protected final void clear_custom_data(){
  }

  @Override
  protected final void customSearch(final BlockPos position, final Block block, final TileEntity tile){
  }

  @Override
  public final void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
  }

}
