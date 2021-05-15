package addsynth.energy.api.energy_network;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.util.java.TimeUtil;
import addsynth.core.util.math.DecimalNumber;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.api.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.api.main.Energy;
import addsynth.energy.api.main.ICustomEnergyUser;
import addsynth.energy.api.main.IEnergyConsumer;
import addsynth.energy.api.main.IEnergyGenerator;
import addsynth.energy.api.main.IEnergyUser;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// original inspiration from canitzp:
// https://github.com/canitzp/Metalworks/blob/master/src/main/java/de/canitzp/metalworks/block/cable/Network.java

/** The EnergyNetwork is responsible for transferring energy to and from machines. It only keeps a list
 *  of receivers, batteries, and generators, and no other data. Certain Batteries acts as part of the
 *  network, so when it performs an Update search, it passes through the batteries, and works on all
 *  batteries at the same time, preventing energy waterfall effects.
 */
public final class EnergyNetwork extends BlockNetwork<AbstractEnergyNetworkTile> {

  public long tick_time;

  private final ArrayList<EnergyNode> receivers = new ArrayList<>();
  private final ArrayList<EnergyNode> batteries = new ArrayList<>();
  private final ArrayList<EnergyNode> generators = new ArrayList<>();

  public EnergyNetwork(final World world, final AbstractEnergyNetworkTile energy_network_tile){
    super(world, energy_network_tile);
  }

  @Override
  protected void clear_custom_data(){
    generators.clear();
    receivers.clear();
    batteries.clear();
  }

  /** Checks if we already have an EnergyNode for this TileEntity by checking to see if we've
   *  already captured a reference to that TileEntity's {@link Energy} object.
   * @param node
   */
  private static final void add_energy_node(final ArrayList<EnergyNode> list, final EnergyNode node){
    boolean exists = false;
    for(EnergyNode existing_node : list){
      if(existing_node.energy == node.energy){
        exists = true;
        break;
      }
    }
    if(exists == false){
      list.add(node);
    }
  }

  @Override
  public final void tick(final AbstractEnergyNetworkTile tile){
    if(tile == first_tile){
      final long start = TimeUtil.get_start_time();
      
      remove_invalid_nodes(batteries);
      remove_invalid_nodes(receivers);
      remove_invalid_nodes(generators);
      
      try{
        // Step 1: subtract as much energy as we can from the generators.
        EnergyUtil.transfer_energy(generators, receivers);
        
        // Step 2: if receivers still need energy, subtract it from batteries.
        EnergyUtil.transfer_energy(batteries, receivers);
        
        // Step 3: put remaining energy from generators into batteries.
        EnergyUtil.transfer_energy(generators, batteries);
        
        // Step 4: balance all batteries
        if(batteries.size() >= 2){
          EnergyUtil.balance_batteries(batteries);
        }
      }
      catch(Exception e){
        ADDSynthEnergy.log.fatal("Encountered a fatal error during Energy Network Tick.", e);
      }
      
      tick_time = TimeUtil.get_elapsed_time(start);
    }
  }

  public final void drain_battery(final Energy battery){
    remove_invalid_nodes(batteries);
    if(batteries.size() >= 1){
      EnergyUtil.balance_batteries(batteries, (long)(battery.getEnergy() * DecimalNumber.DECIMAL_ACCURACY));
    }
  }

  @Override
  protected final void customSearch(final Node node){
    final TileEntity tile = node.getTile();
    if(tile != null){
      if(tile instanceof ICustomEnergyUser){
        add_energy_node(generators, new EnergyNode(tile, ((IEnergyUser)tile).getEnergy()));
        add_energy_node(receivers, new EnergyNode(tile, ((IEnergyUser)tile).getEnergy()));
        return;
      }
      if(tile instanceof IEnergyConsumer){
        add_energy_node(receivers, new EnergyNode(tile, ((IEnergyUser)tile).getEnergy()));
        return;
      }
      if(tile instanceof IEnergyGenerator){
        add_energy_node(generators, new EnergyNode(tile, ((IEnergyUser)tile).getEnergy()));
        return;
      }
      if(tile instanceof IEnergyUser){
        add_energy_node(batteries, new EnergyNode(tile, ((IEnergyUser)tile).getEnergy()));
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    final TileEntity tile = world.getTileEntity(position_of_neighbor);
    if(tile != null){
      if(tile instanceof IEnergyUser){
        updateBlockNetwork(current_position);
      }
    }
  }

}
