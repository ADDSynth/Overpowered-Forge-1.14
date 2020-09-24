package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.block_network.NodeList;
import addsynth.core.util.NetworkUtil;
import addsynth.core.util.block.BlockMath;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.Energy;
import addsynth.energy.tiles.IEnergyUser;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.cannon.TileLaser;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LaserNetwork extends BlockNetwork<TileLaserHousing> implements IEnergyUser {

  private final NodeList lasers = new NodeList(27);
  private int number_of_lasers;
  private boolean activated;
  private int laser_distance;
  public final Energy energy = new Energy(0,1000);
  public boolean running;
  public boolean auto_shutoff;

  // https://stackoverflow.com/questions/4963300/which-notnull-java-annotation-should-i-use
  // https://blogs.oracle.com/java-platform-group/java-8s-new-type-annotations
  public LaserNetwork(final World world, final TileLaserHousing tile){
    super(world, tile);
    this.energy.set_receive_only();
    this.energy.addResponder(this);
  }

  @Override
  public final Energy getEnergy(){
    return energy;
  }
  
  @Override
  public final void onEnergyChanged(){
    updateLaserNetwork();
  }

  @Override
  protected void clear_custom_data(){
    lasers.clear();
  }

  @Override
  protected final void onUpdateNetworkFinished(){
    if(lasers.size() != number_of_lasers){
      number_of_lasers = lasers.size();
      update_energy_requirements();
      updateLaserNetwork(); // because energy was changed
    }
  }

  @Override
  protected final void customSearch(final BlockPos position, final Block block, final TileEntity tile){
    if(block instanceof LaserCannon){
      if(lasers.contains(position) == false){
        lasers.add(new Node(position, tile));
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    final TileLaser laser = MinecraftUtility.getTileEntity(position_of_neighbor, world, TileLaser.class);
    if(laser != null){
      if(lasers.contains(laser) == false){
        lasers.add(new Node(position_of_neighbor, laser));
      }
    }
    remove_invalid_nodes(lasers);
    onUpdateNetworkFinished();
  }

  public final int getLaserDistance(){
    return laser_distance;
  }

  public final void setLaserDistance(int laser_distance){
    this.laser_distance = laser_distance;
    update_energy_requirements();
    updateLaserNetwork(); // because energy and laser distance was changed.
  }

  /**
   *  The energy requirements of the whole LaserHousing network is calculated dynamically depending on how
   *  many lasers there are and what distance the lasers have to travel. It is recalculated whenever one of
   *  these variables change. It is calculated dynamically at runtime and does not need to be saved with the
   *  blocks, only the laser blocks and laser distance need to be saved.
   */
  private final void update_energy_requirements(){
    energy.setCapacity(
      (number_of_lasers * MachineValues.required_energy_per_laser.get()) +
      (number_of_lasers * laser_distance * MachineValues.required_energy_per_laser_distance.get())
    );
  }

  public final void updateLaserNetwork(){
    TileLaserHousing laser_housing;
    remove_invalid_nodes(blocks);
    for(final Node node : blocks){
      laser_housing = (TileLaserHousing)node.getTile();
      laser_housing.setDataDirectlyFromNetwork(energy, laser_distance, running, auto_shutoff); // updates server
      final LaserClientSyncMessage message = new LaserClientSyncMessage(node.position,number_of_lasers);
      NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message); // updates client
      // OPTIMIZE: Send 1 client Network message, containing the data, and all the Block Positions
      //           of the tiles we need to update. DO NOT send individual message for each TileEntity!
      //           Also do this to BridgeNetwork.
    }
  }

  /**
   * Only the first Laser Machine in this <code>LaserNetwork</code> calls the tick function,
   * and basically it checks if any {@link TileLaserHousing} is powered with redstone and calls the
   * {@link #fire_lasers()} function, if certain conditions are met.
   * @param tile
   */
  @Override
  public final void tick(final TileLaserHousing tile){
    if(tile == first_tile){
      if(is_redstone_powered()){
        if(activated == false){
          if(lasers.size() > 0 && laser_distance > 0){
            if(energy.isFull()){
              fire_lasers();
            }
          }
        }
        activated = true;
      }
      else{
        activated = false;
      }
      energy.update(world);
      energy.setEnergyIn(0);
      energy.setEnergyOut(0);
    }
  }

  private final void fire_lasers(){
    final double[] center_position = BlockMath.getExactCenter(blocks.getPositions());
    remove_invalid_nodes(lasers);
    for(Node node : lasers){
      ((TileLaser)node.getTile()).activate(this.laser_distance);
    }
    world.playSound(null, center_position[0], center_position[1], center_position[2], Sounds.laser_fire_sound, SoundCategory.AMBIENT, 2.0f, 1.0f);
    this.energy.extract_all_energy();
    if(auto_shutoff){
      running = false;
    }
    updateLaserNetwork();
  }

}
