package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.Node;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.math.BlockMath;
import addsynth.core.util.network.NetworkUtil;
import addsynth.core.util.world.WorldConstants;
import addsynth.core.util.world.WorldUtil;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BridgeNetwork extends BlockNetwork<TileSuspensionBridge> {

  public final Receiver energy = new Receiver(0, 1000);

  public int lens_index = -1;

  private boolean valid_shape;
  private boolean powered;
  /** Whether this Energy Suspension Bridge is active. */
  private boolean active;
  private boolean[] bridge_active = new boolean[6];


  private int min_x;
  private int min_y;
  private int min_z;
  private int max_x;
  private int max_y;
  private int max_z;

  private BridgeMessage bridge_message;
  private final BridgeMessage[] message = {
    BridgeMessage.PENDING, BridgeMessage.PENDING, BridgeMessage.PENDING,
    BridgeMessage.PENDING, BridgeMessage.PENDING, BridgeMessage.PENDING
  };
  private final BridgeNetwork[] other_bridge = new BridgeNetwork[6];
  @SuppressWarnings("unchecked")
  private ArrayList<BlockPos>[] area = new ArrayList[6];
  private final boolean[] obstructed = new boolean[6];
  private Direction.Axis rotate_direction = Direction.Axis.X;

  public BridgeNetwork(final World world, final TileSuspensionBridge first_tile){
    super(world, first_tile);
  }

  public final int get_min_x(){ return min_x; }
  public final int get_min_y(){ return min_y; }
  public final int get_min_z(){ return min_z; }
  public final int get_max_x(){ return max_x; }
  public final int get_max_y(){ return max_y; }
  public final int get_max_z(){ return max_z; }

  @Override
  protected final void onUpdateNetworkFinished(){
    // most likely the shape changed, which invalidates the bridge, so turn off
    // the CURRENT area of blocks first, then reevaluate.
    set_active(false);
    check_and_update();
    check_neighbor_bridges();
  }

  /** Main check function. */
  public final void check_and_update(){
    bridge_message = BridgeMessage.PENDING;
    check_shape();
    if(valid_shape){
      check_all_directions();
    }
    update_active_state();
    if(bridge_message == BridgeMessage.PENDING){
      if(lens_index == -1){
        bridge_message = BridgeMessage.NO_LENS;
      }
      else{
        if(active){
          bridge_message = BridgeMessage.ACTIVE;
        }
        else{
          bridge_message = BridgeMessage.OFF;
        }
      }
    }
    updateBridgeNetwork();
  }

  /** Sets the <code>valid_shape</code> variable. */
  private final void check_shape(){
    // This is a copy of the BlockMath.is_full_rectangle() function, but
    // keep this as is because we set important variables for this class.
    valid_shape = true;
    final BlockPos[] positions = BlockMath.get_min_max_positions(blocks.getPositions());
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
            bridge_message = BridgeMessage.INVALID_SHAPE;
          }
        }
      }
    }
  }

  private final void check_all_directions(){
    check_down();
    check_up();
    check_north();
    check_south();
    check_west();
    check_east();
  }

  private final void check_direction(final int direction){
    if(direction == DirectionConstant.DOWN ){ check_down();  return; }
    if(direction == DirectionConstant.UP   ){ check_up();    return; }
    if(direction == DirectionConstant.NORTH){ check_north(); return; }
    if(direction == DirectionConstant.SOUTH){ check_south(); return; }
    if(direction == DirectionConstant.WEST ){ check_west();  return; }
    if(direction == DirectionConstant.EAST ){ check_east();  return; }
  }

  private final void check_down(){
    final int direction = DirectionConstant.DOWN;
    // OPTIMIZE, reuse code by calling a common_reset(int direction) function for all these check_direction() functions.
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int y;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_y = min_y - 1;
    final int end_y   = Math.max(min_y - Config.energy_bridge_max_distance.get(), 0);
    final int start_z = min_z;
    final int end_z   = max_z;
    for(y = start_y; y >= end_y && message[direction] == BridgeMessage.PENDING; y--){
      for(z = start_z; z <= end_z && message[direction] == BridgeMessage.PENDING; z++){
        for(x = start_x; x <= end_x && message[direction] == BridgeMessage.PENDING; x++){
          check_position(direction, new BlockPos(x,y,z));
        }
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }
  
  private final void check_up(){
    final int direction = DirectionConstant.UP;
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int y;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_y = max_y + 1;
    final int end_y   = Math.min(max_y + Config.energy_bridge_max_distance.get(), WorldConstants.world_height - 1);
    final int start_z = min_z;
    final int end_z   = max_z;
    for(y = start_y; y <= end_y && message[direction] == BridgeMessage.PENDING; y++){
      for(z = start_z; z <= end_z && message[direction] == BridgeMessage.PENDING; z++){
        for(x = start_x; x <= end_x && message[direction] == BridgeMessage.PENDING; x++){
          check_position(direction, new BlockPos(x,y,z));
        }
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }
  
  private final void check_north(){
    final int direction = DirectionConstant.NORTH;
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_z = min_z - 1;
    final int end_z   = min_z - Config.energy_bridge_max_distance.get();
    for(z = start_z; z >= end_z && message[direction] == BridgeMessage.PENDING; z--){
      for(x = start_x; x <= end_x && message[direction] == BridgeMessage.PENDING; x++){
        check_position(direction, new BlockPos(x,max_y,z));
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }
  
  private final void check_south(){
    final int direction = DirectionConstant.SOUTH;
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_z = max_z + 1;
    final int end_z   = max_z + Config.energy_bridge_max_distance.get();
    for(z = start_z; z <= end_z && message[direction] == BridgeMessage.PENDING; z++){
      for(x = start_x; x <= end_x && message[direction] == BridgeMessage.PENDING; x++){
        check_position(direction, new BlockPos(x,max_y,z));
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }
  
  private final void check_west(){
    final int direction = DirectionConstant.WEST;
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int z;
    final int start_x = min_x - 1;
    final int end_x   = min_x - Config.energy_bridge_max_distance.get();
    final int start_z = min_z;
    final int end_z   = max_z;
    for(x = start_x; x >= end_x && message[direction] == BridgeMessage.PENDING; x--){
      for(z = start_z; z <= end_z && message[direction] == BridgeMessage.PENDING; z++){
        check_position(direction, new BlockPos(x,max_y,z));
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }
  
  private final void check_east(){
    final int direction = DirectionConstant.EAST;
    message[direction] = BridgeMessage.PENDING;
    other_bridge[direction] = null;
    area[direction] = new ArrayList<>(Config.energy_bridge_max_distance.get() * 2);
    obstructed[direction] = false;
    int x;
    int z;
    final int start_x = max_x + 1;
    final int end_x   = max_x + Config.energy_bridge_max_distance.get();
    final int start_z = min_z;
    final int end_z   = max_z;
    for(x = start_x; x <= end_x && message[direction] == BridgeMessage.PENDING; x++){
      for(z = start_z; z <= end_z && message[direction] == BridgeMessage.PENDING; z++){
        check_position(direction, new BlockPos(x,max_y,z));
      }
    }
    if(message[direction] == BridgeMessage.PENDING){
      message[direction] = BridgeMessage.NO_BRIDGE;
    }
  }

  private final void check_position(final int direction, final BlockPos position){
    final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(position, world, TileSuspensionBridge.class);
    if(tile == null){
      final BlockState state = world.getBlockState(position);
      if(state.getMaterial().isReplaceable() || state.getBlock() instanceof EnergyBridge){
        area[direction].add(position);
      }
      else{
        obstructed[direction] = true;
      }
    }
    else{
      if(tile.getBlockNetwork() == null){
        BlockNetworkUtil.createBlockNetwork(world, tile, BridgeNetwork::new);
      }
      other_bridge[direction] = tile.getBlockNetwork();
      if(other_bridge[direction].check(direction, min_x, max_x, min_z, max_z)){
        if(obstructed[direction]){
          message[direction] = BridgeMessage.OBSTRUCTED;
        }
        else{
          message[direction] = BridgeMessage.OKAY;
        }
      }
      else{
        message[direction] = BridgeMessage.INVALID_BRIDGE;
      }
    }
  }

  /** This is an internal method. Only OTHER Bridge Networks should be calling this. */
  private final boolean check(final int direction, final int min_x, final int max_x, final int min_z, final int max_z){
    check_shape();
    if(valid_shape == false){ return false; }
    final boolean length = this.min_z == min_z && this.max_z == max_z;
    final boolean width  = this.min_x == min_x && this.max_x == max_x;
    if(direction == DirectionConstant.DOWN || direction == DirectionConstant.UP){
      return width && length;
    }
    if(direction == DirectionConstant.WEST || direction == DirectionConstant.EAST){
      return length;
    }
    if(direction == DirectionConstant.NORTH || direction == DirectionConstant.SOUTH){
      return width;
    }
    return false;
  }

  private final void check_neighbor_bridges(){
    int direction;
    int opposite;
    BridgeNetwork bridge;
    for(direction = 0; direction < 6; direction++){
      bridge = other_bridge[direction];
      if(bridge != null){
        opposite = DirectionConstant.getOppositeDirection(direction);
        bridge.check_direction(opposite); // updates messages, and bridge area.
        bridge.updateBridgeNetwork();
        bridge.update_direction(opposite);
      }
    }
  }

  /** Called whenever a player inserts or removes a Lens to/from the TileEntity. */
  public final void update_lens(final ItemStack stack){
    final int index = Lens.get_index(stack);
    if(index != lens_index){
      this.lens_index = index;
      check_and_update();
    }
  }

  /** This updates all TileEntities in the network whenever something changes that must be propogated to the rest of them. */
  private final void updateBridgeNetwork(){
    remove_invalid_nodes(blocks);
    TileSuspensionBridge tile;
    for(final Node node : blocks){
      tile = (TileSuspensionBridge)node.getTile();
      if(tile != null){
        tile.getInputInventory().setStackInSlot(0, lens_index < 0 ? ItemStack.EMPTY : new ItemStack(Lens.index[lens_index]));
        tile.update_data();
      }
    }
    final SyncClientBridgeMessage msg = new SyncClientBridgeMessage(blocks.getPositions(), bridge_message, message);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, msg);
  }

  /** This is run every tick, by every Bridge Tile in the network. But we check that only first_tile executes code. */
  @Override
  public final void tick(final TileSuspensionBridge tile){
    if(tile == first_tile){
      final boolean power_check = is_redstone_powered();
      if(power_check != powered){
        powered = power_check;
        if(powered){
          check_and_update();
        }
        else{
          set_active(false);
          if(bridge_message == BridgeMessage.ACTIVE){
            bridge_message = BridgeMessage.OFF;
            updateBridgeNetwork();
          }
        }
      }
      if(active){
        maintain_bridge(); // for every tick the bridge is powered and active, enure NOTHING erases the bridge.
      }
    }
  }

  private final void update_active_state(){
    final boolean valid = valid_shape && lens_index >= 0;
    set_active(valid && powered);
  }

  public final void set_active(final boolean active){
    this.active = active;
    update_direction(0);
    update_direction(1);
    update_direction(2);
    update_direction(3);
    update_direction(4);
    update_direction(5);
  }

  private final void update_direction(final int direction){
    final int opposite = DirectionConstant.getOppositeDirection(direction);
    if(message[direction] == BridgeMessage.OKAY){
    // a message of OKAY means we already know WE'RE valid, and valid in that direction,
    // so we're free to manipulate blocks in that area.
      if(active){
        bridge_active[direction] = true;
        other_bridge[direction].bridge_active[opposite] = true;
        for(final BlockPos position : area[direction]){
          set_energy_block(direction, position);
        }
      }
      else{
        bridge_active[direction] = false;
        other_bridge[direction].bridge_active[opposite] = false;
        for(final BlockPos position : area[direction]){
          if(world.getBlockState(position).getBlock() instanceof EnergyBridge){
            WorldUtil.delete_block(world, position);
          }
        }
      }
    }
    else{
      if(bridge_active[direction]){ // if we suddenly become invalid, but the bridge is on.
        for(final BlockPos position : area[direction]){
          WorldUtil.delete_block(world, position);
        }
        bridge_active[direction] = false;
      }
    }
  }

  private final void set_energy_block(final int direction, final BlockPos position){
    if(direction == DirectionConstant.DOWN || direction == DirectionConstant.UP){
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

  private final void maintain_bridge(){
    int direction;
    Block block;
    for(direction = 0; direction < 6; direction++){
      if(message[direction] == BridgeMessage.OKAY){
        for(final BlockPos position : area[direction]){
          block = world.getBlockState(position).getBlock();
          if(block instanceof EnergyBridge == false){
            set_energy_block(direction, position);
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
    update_direction(DirectionConstant.DOWN);
    update_direction(DirectionConstant.UP);
  }

  @Override
  protected final void clear_custom_data(){
  }

}
