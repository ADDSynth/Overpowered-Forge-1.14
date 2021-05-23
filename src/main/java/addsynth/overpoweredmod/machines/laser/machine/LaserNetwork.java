package addsynth.overpoweredmod.machines.laser.machine;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.block_network.NodeList;
import addsynth.core.util.data.AdvancementUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.math.BlockMath;
import addsynth.core.util.network.NetworkUtil;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.cannon.TileLaser;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LaserNetwork extends BlockNetwork<TileLaserHousing> {

  public static final int max_laser_distance = 1000;

  public boolean changed;
  private final NodeList lasers = new NodeList(27);
  private int number_of_lasers;
  private boolean activated;
  private int laser_distance;
  public final Receiver energy = new Receiver(0, MachineValues.laser_max_receive.get()){
    @Override
    public boolean canReceive(){
      return super.canReceive() && running;
    }
  };
  public boolean running;
  public boolean auto_shutoff;

  // https://stackoverflow.com/questions/4963300/which-notnull-java-annotation-should-i-use
  // https://blogs.oracle.com/java-platform-group/java-8s-new-type-annotations
  public LaserNetwork(final World world, final TileLaserHousing tile){
    super(world, tile);
  }

  @Override
  protected void clear_custom_data(){
    lasers.clear();
  }

  @Override
  protected final void onUpdateNetworkFinished(){
    check_if_lasers_changed();
  }

  @Override
  protected final void customSearch(final Node node){
    if(node.block instanceof LaserCannon){
      // FIX: only checks for LaserCannon block. Potential error if Laser is wrong type or laser is not attached to THIS laser network!
      if(lasers.contains(node.position) == false){
        lasers.add(node);
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    remove_invalid_nodes(lasers);

    final TileLaser laser = MinecraftUtility.getTileEntity(position_of_neighbor, world, TileLaser.class);
    if(laser != null){
      if(lasers.contains(laser) == false){
        lasers.add(new Node(position_of_neighbor, laser));
      }
    }
    check_if_lasers_changed();
  }

  public final void load_data(Energy energy, boolean power_switch, int laser_distance, boolean auto_shutoff){
    this.energy.set(energy);
    this.running = power_switch;
    this.laser_distance = laser_distance;
    this.auto_shutoff = auto_shutoff;
  }

  public final int getLaserDistance(){
    return laser_distance;
  }

  public final void setLaserDistance(int laser_distance){
    this.laser_distance = laser_distance;
    update_energy_requirements();
  }

  private final void check_if_lasers_changed(){
    if(lasers.size() != number_of_lasers){
      number_of_lasers = lasers.size();
      update_energy_requirements();
      updateClient();
    }
  }

  /**
   *  The energy requirements of the whole LaserHousing network is calculated dynamically depending on how
   *  many lasers there are and what distance the lasers have to travel. It is recalculated whenever one of
   *  these variables change.
   */
  private final void update_energy_requirements(){
    energy.setCapacity(
      (number_of_lasers * MachineValues.required_energy_per_laser.get()) +
      (number_of_lasers * laser_distance * MachineValues.required_energy_per_laser_distance.get())
    );
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
      if(energy.tick()){
        changed = true;
      }
      if(changed){
        updateLaserNetwork();
        changed = false;
      }
      energy.updateEnergyIO();
    }
  }

  /** updates server (needs to be saved to world data) */
  private final void updateLaserNetwork(){
    remove_invalid_nodes(blocks);
    TileLaserHousing laser_housing;
    for(final Node node : blocks){
      laser_housing = (TileLaserHousing)node.getTile();
      if(laser_housing != null){
        laser_housing.setDataDirectlyFromNetwork(energy, laser_distance, running, auto_shutoff);
      }
    }
  }
  
  public final void updateClient(){ // (client can determine the information)
    final LaserClientSyncMessage message = new LaserClientSyncMessage(blocks.getPositions(), number_of_lasers);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message);
  }

  private final void fire_lasers(){
    remove_invalid_nodes(blocks);
    remove_invalid_nodes(lasers);
    TileEntity tile;
    // OPTIMIZE: Node and NodeList by specifying a type parameter.
    for(Node node : lasers){
      tile = node.getTile();
      if(tile != null){
        ((TileLaser)tile).activate(this.laser_distance);
      }
    }
    final double[] center_position = BlockMath.getExactCenter(blocks.getPositions());
    world.playSound(null, center_position[0], center_position[1], center_position[2], Sounds.laser_fire_sound, SoundCategory.AMBIENT, 2.0f, 1.0f);
    final ArrayList<ServerPlayerEntity> players = new ArrayList<>();
    ServerPlayerEntity player;
    for(Node node : blocks){
      tile = node.getTile();
      if(tile != null){
        player = ((TileLaserHousing)tile).getPlayer();
        if(player != null){
          if(players.contains(player) == false){
            players.add(player);
          }
        }
      }
    }
    awardPlayers(players, this.laser_distance);
    this.energy.subtract_capacity();
    if(auto_shutoff){
      running = false;
    }
    changed = true;
  }

  private static final void awardPlayers(final ArrayList<ServerPlayerEntity> players, final int laser_distance){
    for(final ServerPlayerEntity player : players){
      AdvancementUtil.grantAdvancement(player, CustomAdvancements.FIRE_LASER);
      if(laser_distance >= LaserNetwork.max_laser_distance){
        AdvancementUtil.grantAdvancement(player, CustomAdvancements.FIRE_MAXIMUM_DISTANCE);
      }
      player.addStat(CustomStats.LASERS_FIRED);
    }
  }

}
