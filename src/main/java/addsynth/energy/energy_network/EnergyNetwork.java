package addsynth.energy.energy_network;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.Energy;
import addsynth.energy.energy_network.tiles.TileEnergyNetwork;
import addsynth.energy.gameplay.energy_wire.TileEnergyWire;
import addsynth.energy.tiles.TileEnergyWithStorage;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// original code from canitzp:
// https://github.com/canitzp/Metalworks/blob/master/src/main/java/de/canitzp/metalworks/block/cable/Network.java

/**
 *  EnergyWire type blocks should use this extended form of a BlockNetwork. This also keeps a list of all
 *  {@link TileEnergyWithStorage} Tile Entities that use a {@link Energy} and can accept
 *  energy.
 */
public final class EnergyNetwork extends BlockNetwork<TileEnergyNetwork> {

  public final ArrayList<EnergyNode> receivers = new ArrayList<>();
  public final ArrayList<EnergyNode> batteries = new ArrayList<>();
  public final ArrayList<EnergyNode> generators = new ArrayList<>();

  /* FUTURE Energy Future Notes: IMPORTANT!!!
     Machines that are not receivers or transmitters, they are batteries, MAKE THEM PASSTHROUGH!
     Make batteries part of the network.
     (will have to make Block Networks accept more than 1 type of block to use for the network.)
     Any battery the network detects acts as the network's internal energy storage.
     YES, make the network have an energy storage!
     Change the above variable 'receivers' into just energy_machines, that hold both receivers
     and transmitters.
     It is an interesting relationship:
     1 - Get as much energy as you possibly can from ALL Transmitters.
     2 - Push the Transmitters's energy into all the Receivers (as much as you can(.
     3 - If Receivers STILL need energy, try to get it from all Batteries.
     4 - If any energy was left over from Step 1 and 2 (which only happens if we managed to filled
         all the Receivers) push the Transmitter's remaining energy to the Batteries.
     !! When we check Transmitters, ask how many networks they are connected to.
     !! Receivers can receive as much energy as they want so we don't need to check them.
         - No, there's something else I want to change during the energy system rewrite.
     For Receivers: if we detect that we're not getting enough energy from one side, then add an
       offset to certain sides that are offering energy. This only needs to happen in one tick.
       Once we detect that the energy from all sides is equal, reset all offset variables.
  */

  public EnergyNetwork(final World world, final TileEnergyNetwork energy_wire){
    super(world, energy_wire);
  }

  @Override
  protected void clear_custom_data(){
    receivers.clear();
  }

  /** Checks if we already have an EnergyNode for this TileEntity by checking to see if we've
   *  already captured a reference to that TileEntity's {@link Energy} object.
   * @param node
   */
  private final void add_energy_node(final EnergyNode node){
    boolean exists = false;
    for(EnergyNode existing_node : receivers){
      if(existing_node.energy == node.energy){
        exists = true;
        break;
      }
    }
    if(exists == false){
      receivers.add(node);
    }
  }

  public final void update(final TileEntity tile){
    if(tile == first_tile){
    }
  }

  @Override
  protected final void customSearch(final BlockPos position, final Block block, final TileEntity t){
    final TileEnergyWithStorage tile = MinecraftUtility.getTileEntity(position, world, TileEnergyWithStorage.class);
    if(tile != null){
      final Energy energy = tile.getEnergy();
      if(energy != null){
        if(energy.canReceive()){
          add_energy_node(new EnergyNode(position, tile, energy));
        }
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    if(MinecraftUtility.getTileEntity(position_of_neighbor, world, TileEnergyWithStorage.class) != null){
      updateNetwork(current_position);
      return;
    }
    remove_invalid_nodes(receivers);
  }

  public void update(final TileEnergyWire tile){
    if(tile == first_tile){
    }
  }

}
