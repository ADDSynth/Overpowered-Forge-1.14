package addsynth.energy.tiles;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.util.MathUtility;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.EnergyNetwork;
import addsynth.energy.EnergyNode;
import addsynth.energy.tiles.machines.PassiveMachine;
import net.minecraft.item.Item;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;

public abstract class TileEnergyTransmitter extends TileEnergyWithStorage implements ITickableTileEntity {

  protected final ArrayList<EnergyNetwork>         networks = new ArrayList<>(1);
  protected final ArrayList<TileEnergyWithStorage> machines = new ArrayList<>(6);

  public TileEnergyTransmitter(final CustomEnergyStorage energy){
    super(energy);
    if(this.energy != null){
      if(this instanceof TileEnergyBattery == false){
        this.energy.set_extract_only();
      }
    }
  }

  public TileEnergyTransmitter(final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy){
    super(input_slots, filter, output_slots, energy);
    if(this.energy != null){
      if(this instanceof TileEnergyBattery == false){
        this.energy.set_extract_only();
      }
    }
  }

  /**
   * All abstracted classes (classes that extend EnergyTransmitter) should call this super.update() method
   * when they are ready to transmit energy.
   */
  @Override
  public void tick(){
    if(world != null){
      if(world.isRemote == false){
        if(energy != null){
          if(energy.hasEnergy()){
            get_machines_that_need_energy();
            if(machines.size() > 0){
              give_machines_energy();
              update_data();
            }
          }
        }
      }
    }
    energy.update();
  }

  protected void get_machines_that_need_energy(){
    machines.clear();
    getNetworks();
    if(networks.size() > 0){
      for(EnergyNetwork network : networks){
        for(EnergyNode node : network.receivers){
          if(node.energy.canReceive()){
            if(node.getTile().needsEnergy()){
              machines.add(node.getTile());
            }
          }
        }
      }
    }
  }

  /**
   *  Get all sides that have an Energy Wire, that are different networks!
   */
  protected final void getNetworks(){
    networks.clear();
    BlockNetwork network;
    EnergyNetwork energy_network;
    for(Direction side : Direction.values()){
      network = BlockNetwork.getNetwork(world, pos.offset(side));
      if(network != null){
        if(network instanceof EnergyNetwork){
          energy_network = (EnergyNetwork)network;
          if(networks.contains(energy_network) == false){
            networks.add(energy_network);
          }
        }
      }
    }
  }

  /**
   * This function will only activate if we have machines that CAN receive energy and NEED energy.
   */
  protected final void give_machines_energy(){
    int energy_draw = 0; // TODO FUTURE: We've updated our CustomEnergyStorage class now to properly track changes in energy on a per tick basis. Should I find a way to properly use our own receiverEnergy() and extractEnergy() methods? I'll probably work on this when it comes time to update our Energy system in the next update.
    final int total_energy = energy.extractEnergy(energy.getEnergy(),true); // simulate
    final int count = machines.size();
    final int[] energy = MathUtility.divide_evenly(total_energy, count);
    TileEnergyWithStorage tile;
    int i;
    for(i = 0; i < count; i++){
      tile = machines.get(i);
      if(tile instanceof PassiveMachine){
        if(((PassiveMachine)tile).getState() == PassiveMachine.State.IDLE){
          energy_draw += tile.getEnergy().receiveEnergy(1, false);
          tile.update_data();
          continue;
        }
      }
      energy_draw += tile.getEnergy().receiveEnergy(energy[i], false);
      tile.update_data();
    }
    this.energy.extractEnergy(energy_draw, false);
  }

}
