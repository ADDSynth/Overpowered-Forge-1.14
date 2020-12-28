package addsynth.overpoweredmod.machines.fusion.converter;

import java.util.ArrayList;
import addsynth.core.tiles.TileBase;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.main.Generator;
import addsynth.energy.main.IEnergyGenerator;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.machines.data_cable.DataCableNetwork;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public final class TileFusionEnergyConverter extends TileBase implements IEnergyGenerator, ITickableTileEntity {

  private final Generator energy = new Generator(MachineValues.fusion_energy_output_per_tick.get());
  private static final int sync_timer = 4; // TODO: remove sync timer in version 1.4
  private final ArrayList<DataCableNetwork> data_cable_networks = new ArrayList<>(1);
  private TileFusionChamber fusion_chamber;
  private boolean activated;
  private boolean valid;

  public TileFusionEnergyConverter(){
    super(Tiles.FUSION_ENERGY_CONVERTER);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      if(world.getGameTime() % sync_timer == 0){
        
        final BlockPos previous_position = fusion_chamber != null ? fusion_chamber.getPos() : null;
        final boolean previous_valid = valid;
        
        check_connection(); // keep up-to-date, always.
        activated = world.isBlockPowered(pos);
        
        if(fusion_chamber == null){
          if(previous_position != null){
            fusion_chamber = MinecraftUtility.getTileEntity(previous_position, world, TileFusionChamber.class);
          }
        }
        
        if(fusion_chamber != null){ // Cannot be valid without fusion chamber
          fusion_chamber.set_state(activated && valid); // keep fusion chamber up-to-date if it exists.
          if(activated && valid == false && previous_valid == true){
            // only explodes if valid goes from true to false. Loading a world is safe because it goes from false to true.
            fusion_chamber.explode();
            fusion_chamber = null;
          }
        }
      }
      // every tick
      if(activated && valid){
        energy.set_to_full();
      }
      energy.updateEnergyIO();
    }
  }

  // No data needs to be saved to disk.

  private final void check_connection(){
    get_networks();
    valid = false;
    fusion_chamber = null;
    BlockPos position;
    if(data_cable_networks.size() > 0){
      for(DataCableNetwork network : data_cable_networks){
        position = network.get_valid_fusion_container();
        if(position != null){
          fusion_chamber = (TileFusionChamber)world.getTileEntity(position);
          if(fusion_chamber != null){
            if(fusion_chamber.has_fusion_core()){
              valid = true;
              break;
            }
          }
        }
      }
    }
  }

  private final void get_networks(){
    data_cable_networks.clear();
    TileEntity tile;
    DataCableNetwork data_network;
    for(Direction facing : Direction.values()){
      tile = world.getTileEntity(pos.offset(facing));
      if(tile != null){
        if(tile instanceof TileDataCable){
          data_network = ((TileDataCable)tile).getBlockNetwork();
          if(data_network != null){
            if(data_cable_networks.contains(data_network) == false){
              data_cable_networks.add(data_network);
            }
          }
        }
      }
    }
  }

  @Override
  public final Generator getEnergy(){
    return energy;
  }

}
