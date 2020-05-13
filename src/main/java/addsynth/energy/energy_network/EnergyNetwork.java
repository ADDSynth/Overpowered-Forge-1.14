package addsynth.energy.energy_network;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.TimeUtil;
import addsynth.energy.Energy;
import addsynth.energy.EnergyUtil;
import addsynth.energy.energy_network.tiles.TileEnergyBattery;
import addsynth.energy.energy_network.tiles.TileEnergyNetwork;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.energy.tiles.TileEnergyTransmitter;
import addsynth.energy.tiles.TileEnergyWithStorage;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// original code from canitzp:
// https://github.com/canitzp/Metalworks/blob/master/src/main/java/de/canitzp/metalworks/block/cable/Network.java

/** The EnergyNetwork is responsible for transferring energy to and from machines. It only keeps a list
 *  of receivers, batteries, and generators, and no other data. Certain Batteries acts as part of the
 *  network, so when it performs an Update search, it passes through the batteries, and works on all
 *  batteries at the same time, preventing energy waterfall effects.
 */
public final class EnergyNetwork extends BlockNetwork<TileEnergyNetwork> {

  public long tick_time;

  public final ArrayList<EnergyNode> receivers = new ArrayList<>();
  public final ArrayList<EnergyNode> batteries = new ArrayList<>();
  public final ArrayList<EnergyNode> generators = new ArrayList<>();

  public EnergyNetwork(final World world, final TileEnergyNetwork energy_wire){
    super(world, energy_wire);
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
  public final void tick(final TileEnergyNetwork tile){
    if(tile == first_tile){
      final long start = TimeUtil.get_start_time();
      
      remove_invalid_nodes(batteries);
      remove_invalid_nodes(receivers);
      remove_invalid_nodes(generators);
      
      // Step 1: subtract as much energy as we can from the generators:
      EnergyUtil.transfer_energy(generators, receivers);

      // Step 2: if receivers still need energy, subtract it from batteries.
      EnergyUtil.transfer_energy(batteries, receivers);

      // Step 3: put remaining energy from Step 1 into batteries.
      EnergyUtil.transfer_energy(generators, batteries);
      
      tick_time = TimeUtil.get_elapsed_time(start);
    }
  }

  @Override
  protected final void customSearch(final BlockPos position, final Block block, final TileEntity tile){
    if(tile != null){
      if(tile instanceof TileEnergyBattery){
        add_energy_node(batteries, new EnergyNode(position, (TileEnergyBattery)tile));
      }
      if(tile instanceof TileEnergyReceiver){
        add_energy_node(receivers, new EnergyNode(position, (TileEnergyReceiver)tile));
        return;
      }
      if(tile instanceof TileEnergyTransmitter){
        add_energy_node(generators, new EnergyNode(position, (TileEnergyTransmitter)tile));
        return;
      }
      if(tile instanceof TileEnergyWithStorage){
        add_energy_node(batteries, new EnergyNode(position, (TileEnergyWithStorage)tile));
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    final TileEntity tile = world.getTileEntity(position_of_neighbor);
    if(tile != null){
      if(tile instanceof TileEnergyWithStorage || tile instanceof TileEnergyBattery){
        updateBlockNetwork(current_position);
      }
    }
  }

}
