package addsynth.energy.tiles;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.inventory.SlotData;
import addsynth.core.tiles.TileMachine;
import addsynth.energy.Energy;
import addsynth.energy.energy_network.EnergyNetwork;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public abstract class TileEnergyWithStorage extends TileMachine implements ITickableTileEntity, IEnergyUser {

  // @Nullable
  protected final Energy energy;

  protected int number_of_energy_networks;

  /** This constructor is used for energy machines that don't have
   *  an inventory such as the Battery or Universal Energy Interface.
   *  @param energy
   */
  public TileEnergyWithStorage(final TileEntityType type, final Energy energy){
    super(type, 0, null, 0);
    this.energy = energy;
    this.energy.setResponder(this);
  }

  public TileEnergyWithStorage(final TileEntityType type, final SlotData[] slots, final int output_slots, final Energy energy){
    super(type, slots, output_slots);
    this.energy = energy;
    this.energy.setResponder(this);
  }

  public TileEnergyWithStorage(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final Energy energy){
    super(type, input_slots, filter, output_slots);
    this.energy = energy;
    this.energy.setResponder(this);
  }

  @Override
  public void tick(){
    if(world.isRemote == false){
      if(energy != null){
        get_energy_networks();
        energy.update(world);
      }
    }
  }

  // TODO: machines that TRANSMIT energy should determine how many EnergyNetworks they are connected to,
  //       and split their energy equally among all networks. But this applies to ALL machines that can
  //       transmit Energy, including Batteries, and TileEnergyWithStorage.
  private final void get_energy_networks(){ // Temporary solution, maybe
    final ArrayList<EnergyNetwork> list = new ArrayList<>();
    BlockPos position;
    BlockNetwork network;
    EnergyNetwork energy_network;
    for(final Direction direction : Direction.values()){
      position = pos.offset(direction);
      network = BlockNetwork.getNetwork(world, position);
      if(network != null){
        if(network instanceof EnergyNetwork){
          energy_network = (EnergyNetwork)network;
          if(list.contains(energy_network) == false){
            list.add(energy_network);
          }
        }
      }
    }
    number_of_energy_networks = list.size();
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(energy != null){
      energy.readFromNBT(nbt);
    }
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(energy != null){
      energy.writeToNBT(nbt);
    }
    return nbt;
  }

  // @Override    DELETE
  // public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
  //   if(capability == CapabilityEnergy.ENERGY){
  //     return (LazyOptional.of(()->energy)).cast();
  //   }
  //   return super.getCapability(capability, facing);
  // }
  
  /**
   * I suppose I can use this within my own code, like in guis and stuff, as long as I know that tile has
   * a Custom Energy Storage.
   */
  @Override
  public Energy getEnergy(){
    return this.energy;
  }

  @Override
  public void onEnergyChanged(){
    update_data();
  }

  public final double getRequestedEnergy(){ // DELETE
    if(energy != null){
      if(number_of_energy_networks > 0){
        return energy.getRequestedEnergy() * number_of_energy_networks;
      }
    }
    return 0;
  }

}
