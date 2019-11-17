package addsynth.overpoweredmod.tiles.machines.fusion;

import java.util.ArrayList;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyTransmitter;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.block_networks.DataCableNetwork;
import addsynth.overpoweredmod.tiles.TileDataCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public final class TileFusionEnergyConverter extends TileEnergyTransmitter {

  private static final int sync_timer = 10;
  private final ArrayList<DataCableNetwork> data_cable_networks = new ArrayList<>(1);
  private BlockPos singularity_container;
  private boolean activated;
  private boolean valid;

  public TileFusionEnergyConverter(){
    super(0, null, 0, new CustomEnergyStorage(0,Values.fusion_energy_output_per_tick));
  }

  @Override
  public final void update(){
    if(world.isRemote == false){
      if(world.getWorldTime() % sync_timer == 0){
        activated = world.isBlockPowered(pos);
        if(activated){
          check_connection();
        }
      }
      if(activated && valid){
        energy.set_to_full();
        super.update();
      }
      if(singularity_container != null){
        final TileFusionChamber tile = MinecraftUtility.getTileEntity(singularity_container, world, TileFusionChamber.class);
        if(tile != null){
          tile.keep_updated(activated && valid);
        }
      }
    }
    else{
      energy.update();
    }
  }

  private final void check_connection(){
    get_networks();
    valid = false;
    if(data_cable_networks.size() > 0){
      TileFusionChamber tile;
      for(DataCableNetwork network : data_cable_networks){
        singularity_container = network.get_valid_singularity_container();
        if(singularity_container != null){
          tile = (TileFusionChamber)world.getTileEntity(singularity_container);
          if(tile != null){
            if(tile.has_fusion_core()){
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
