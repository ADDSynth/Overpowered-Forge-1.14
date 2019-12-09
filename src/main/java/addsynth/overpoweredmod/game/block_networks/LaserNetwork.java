package addsynth.overpoweredmod.game.block_networks;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.util.MathUtility;
import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.NetworkUtil;
import addsynth.energy.CustomEnergyStorage;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserCannon;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.network.NetworkHandler;
import addsynth.overpoweredmod.network.laser.LaserClientSyncMessage;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaser;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import net.minecraft.block.Block;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LaserNetwork extends BlockNetwork<TileLaserHousing> {

  private final ArrayList<Node> lasers = new ArrayList<>(9);
  private int number_of_lasers;
  private boolean activated;
  private int laser_distance;
  public final CustomEnergyStorage energy = new CustomEnergyStorage(0,1000);
  public boolean running;
  public boolean auto_shutoff;

  // https://stackoverflow.com/questions/4963300/which-notnull-java-annotation-should-i-use
  // https://blogs.oracle.com/java-platform-group/java-8s-new-type-annotations
  public LaserNetwork(final World world, @Nonnull final TileLaserHousing laser_house){
    super(world, laser_house.getBlockState().getBlock(), laser_house);
    this.energy.set_receive_only();
  }

  @Override
  protected void clear_custom_data(){
    lasers.clear();
  }

  @Override
  protected final void onUpdateNetworkFinished(final BlockPos position){
    if(lasers.size() != number_of_lasers){
      number_of_lasers = lasers.size();
      update_energy_requirements();
      updateLaserNetwork(); // because energy was changed
    }
  }

  @Override
  protected final void customSearch(final Block block, final BlockPos new_position){
    if(block instanceof LaserCannon){
      boolean exists = false;
      for(Node node : lasers){
        if(node.position == new_position){
          exists = true;
          break;
        }
      }
      if(exists == false){
        lasers.add(new Node(new_position, world.getTileEntity(new_position)));
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    if(MinecraftUtility.getTileEntity(position_of_neighbor, world, TileLaser.class) != null){
      updateNetwork(current_position);
      return;
    }
    remove_invalid_nodes(lasers);
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
      (number_of_lasers * Values.required_energy_per_laser.get()) +
      (number_of_lasers * laser_distance * Values.required_energy_per_laser_distance.get())
    );
  }

  public final void updateLaserNetwork(){
    TileLaserHousing laser_housing;
    for(BlockPos block_position : blocks){
      if(block_position != null){
        laser_housing = MinecraftUtility.getTileEntity(block_position, world, TileLaserHousing.class);
        if(laser_housing != null){
          laser_housing.setDataDirectlyFromNetwork(energy, laser_distance, running, auto_shutoff); // updates server
          final LaserClientSyncMessage message = new LaserClientSyncMessage(block_position,number_of_lasers);
          NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message); // updates client
          // FIX: Laser Networks don't initialize properly and instead initializes to default values, which sets
          //      everything to 0 or off. This will get fixed during the BlockNetwork rewrite in the next update.
        }
      }
    }
  }

  /**
   * Only the first Laser Machine in this <code>LaserNetwork</code> calls the update function,
   * and basically it checks if any LaserHouse is powered with redstone and calls the
   * {@link #fire_lasers()} function, if certain conditions are met.
   * @param tile
   */
  public final void update(final TileLaserHousing tile){
    if(tile == first_tile){
      if(tile.getWorld().isRemote == false){
        boolean block_powered = false;
        for(BlockPos position : blocks){
          if(world.isBlockPowered(position)){
            block_powered = true;
            break;
          }
        }
        if(block_powered){
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
      }
    }
  }

  private final void fire_lasers(){
    final double[] center_position = MathUtility.getExactCenter(blocks);
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
