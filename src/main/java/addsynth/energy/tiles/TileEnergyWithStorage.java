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

public abstract class TileEnergyWithStorage extends TileMachine implements ITickableTileEntity {

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
  }

  public TileEnergyWithStorage(final TileEntityType type, final SlotData[] slots, final int output_slots, final Energy energy){
    super(type, slots, output_slots);
    this.energy = energy;
  }

  public TileEnergyWithStorage(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final Energy energy){
    super(type, input_slots, filter, output_slots);
    this.energy = energy;
  }

  @Override
  public void tick(){
    if(world.isRemote == false){
      get_energy_networks();
    }
  }

  private final void get_energy_networks(){ // Temporary solution, maybe
    final ArrayList<EnergyNetwork> list = new ArrayList<>();
    BlockPos position;
    BlockNetwork network;
    for(final Direction direction : Direction.values()){
      position = pos.offset(direction);
      network = BlockNetwork.getNetwork(world, position);
      if(network != null){
        if(network instanceof EnergyNetwork){
          if(list.contains(network) == false){
            list.add((EnergyNetwork)network);
          }
        }
      }
    }
    number_of_energy_networks = list.size();
  }

  @Override
  public void read(CompoundNBT nbt){
    super.read(nbt);
    if(energy != null){
      energy.readFromNBT(nbt);
    }
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt){
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
  public Energy getEnergy(){
    return this.energy;
  }

  /**
   * Uses current energy level and energy capacity and returns a percentage float.
   * 
   * @return energy level percentage AS A FLOAT!
   */
  public final float getEnergyPercentage(){
    float return_value = 0.0f;
    if(energy != null){
      return_value = energy.getEnergyPercentage();
    }
    return return_value;
  }

  public final double getRequestedEnergy(){
    if(energy != null){
      if(number_of_energy_networks > 0){
        return energy.getRequestedEnergy() * number_of_energy_networks;
      }
    }
    return 0;
  }

}
