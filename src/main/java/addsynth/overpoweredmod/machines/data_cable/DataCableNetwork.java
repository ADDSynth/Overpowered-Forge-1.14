package addsynth.overpoweredmod.machines.data_cable;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class DataCableNetwork extends BlockNetwork<TileDataCable> {

  /** All scanning units that are connected to this Data Cable network. */
  private final ArrayList<BlockPos> scanning_units = new ArrayList<>(6);
  private final ArrayList<BlockPos> fusion_energy_converters = new ArrayList<>(1);

  private static final class SingularityContainmentStructure {
    public final BlockPos position;
    private boolean[] side = new boolean[] {false, false, false, false, false, false};
    public SingularityContainmentStructure(final BlockPos position_of_singularity_container){
      position = position_of_singularity_container;
    }
    public final void add_scanning_unit(final Direction direction){
      // yeah it's actually the opposite side, but it's still only valid if it has all 6 sides.
      side[direction.ordinal()] = true;
    }
    public final boolean is_valid(){
      return side[0] && side[1] && side[2] && side[3] && side[4] && side[5];
    }
  }

  public DataCableNetwork(final World world, final Block block, final TileDataCable data_cable){
    super(world, block, data_cable);
  }

  @Override
  protected final void clear_custom_data(){
    scanning_units.clear();
    fusion_energy_converters.clear();
  }

  @Override
  protected final void customSearch(final Block block, final BlockPos position){
    if(block == Machines.fusion_control_unit){
      if(scanning_units.contains(position) == false){
        scanning_units.add(position);
      }
    }
    if(block == Machines.fusion_converter){
      if(fusion_energy_converters.contains(position) == false){
        fusion_energy_converters.add(position);
      }
    }
  }

  @Override
  public final void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    Block block = world.getBlockState(position_of_neighbor).getBlock();
    if(block == Machines.fusion_converter || block == Machines.fusion_control_unit){
      updateNetwork(current_position);
      return;
    }
    boolean update = false;
    if(update == false){
      for(BlockPos position : scanning_units){
        if(world.getBlockState(position).getBlock() != Machines.fusion_control_unit){
          update = true;
          break;
        }
      }
    }
    if(update == false){
      for(BlockPos position : fusion_energy_converters){
        if(world.getBlockState(position).getBlock() != Machines.fusion_converter){
          update = true;
          break;
        }
      }
    }
    if(update){
      updateNetwork(current_position); // run update outside of for loop to avoid ConcurrentModificationException's.
    }
  }

  @Override
  protected final void onUpdateNetworkFinished(final BlockPos position){
    // What we're doing here is, even if the player has a valid fusion chamber constructed properly,
    //   its energy output can be divided amongst multiple Fusion Energy Converter machines.
    // check_singularity_container();
    if(fusion_energy_converters.size() > 0){
      final int actual_energy = Math.round((float)Values.fusion_energy_output_per_tick.get() / fusion_energy_converters.size());
      TileFusionEnergyConverter tile;
      for(BlockPos tile_position : fusion_energy_converters){
        tile = (TileFusionEnergyConverter)world.getTileEntity(tile_position);
        tile.getEnergy().setCapacity(actual_energy); // MAYBE: merhaps I can move this to the TileFusionEnergyConverter class.
      }
    }
  }

  /** THIS, is the algorithm that goes through all the scanning units, and performs an arsenal of
   *  validity checks on them! It ends as soon as it finds 1 valid Singularity Containment structure!
   */
  public final BlockPos get_valid_singularity_container(){
    BlockPos valid_singularity_container = null;
    if(scanning_units.size() >= 6){
      boolean found_valid = false;
      SingularityContainmentStructure structure = null;
      BlockState block_state;
      BlockPos position;
      for(BlockPos scanning_unit : scanning_units){
        for(Direction side : Direction.values()){
          block_state = world.getBlockState(scanning_unit.offset(side));
          if(block_state.getBlock() == Machines.fusion_control_laser){
            if(block_state.get(LaserCannon.FACING) == side){
              position = scanning_unit.offset(side, TileFusionChamber.container_radius);
              if(world.getBlockState(position).getBlock() == Machines.fusion_chamber){
                // FIX: we need to keep a list of singularity containers, otherwise, if the scanning units are out of order, we immediately replace the current one.
                if(structure == null){
                  structure = new SingularityContainmentStructure(position);
                }
                else{
                  if(position.equals(structure.position) == false){
                    structure = new SingularityContainmentStructure(position);
                  }
                }
                structure.add_scanning_unit(side);
                if(structure.is_valid()){
                  found_valid = true;
                  valid_singularity_container = structure.position;
                  break;
                }
              }
            }
          }
        }
        if(found_valid){
          break;
        }
      }
    }
    return valid_singularity_container;
  }

}
