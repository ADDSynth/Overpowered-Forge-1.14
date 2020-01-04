package addsynth.overpoweredmod.machines.fusion.converter;

import java.util.ArrayList;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyTransmitter;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.machines.data_cable.DataCableNetwork;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public final class TileFusionEnergyConverter extends TileEnergyTransmitter {

  private static final int sync_timer = 4; // TODO: remove sync timer in version 1.3
  private final ArrayList<DataCableNetwork> data_cable_networks = new ArrayList<>(1);
  private TileFusionChamber fusion_chamber;
  private boolean activated;
  private boolean valid;

  public TileFusionEnergyConverter(){
    super(Tiles.FUSION_ENERGY_CONVERTER, new CustomEnergyStorage(0,Values.fusion_energy_output_per_tick.get()));
  }

  @Override
  public final void tick(){
    if(world.isRemote == false){
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
            fusion_chamber.explode();
            fusion_chamber = null;
          }
        }
      }
      // every tick
      if(activated && valid){
        energy.set_to_full();
        super.tick();
      }
    }
    else{
      energy.update();
    }
  }

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
          data_network = ((TileDataCable)tile).getNetwork();
          if(data_network != null){
            if(data_cable_networks.contains(data_network) == false){
              data_cable_networks.add(data_network);
            }
          }
        }
      }
    }
  }

}