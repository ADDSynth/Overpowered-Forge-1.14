package addsynth.energy.tiles;

import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.tiles.TileMachine;
import addsynth.energy.CustomEnergyStorage;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class TileEnergyWithStorage extends TileMachine implements ITickable {

  protected final CustomEnergyStorage energy;

  /** This constructor is used for energy machines that don't have
   *  an inventory such as the Battery or Universal Energy Interface.
   *  @param energy
   */
  public TileEnergyWithStorage(final CustomEnergyStorage energy){
    super(0, null, 0);
    this.energy = energy;
  }

  public TileEnergyWithStorage(final SlotData[] slots, final int output_slots, final CustomEnergyStorage energy){
    super(slots, output_slots);
    this.energy = energy;
  }

  public TileEnergyWithStorage(final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy){
    super(input_slots, filter, output_slots);
    this.energy = energy;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt){
    super.readFromNBT(nbt);
    if(energy != null){
      energy.readFromNBT(nbt);
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt){
    super.writeToNBT(nbt);
    if(energy != null){
      energy.writeToNBT(nbt);
    }
    return nbt;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(final Capability<T> capability, final @Nullable EnumFacing facing){
    if(capability == CapabilityEnergy.ENERGY){
      return (T)getEnergy();
    }
    return super.getCapability(capability, facing);
  }
  
  @Override
  public boolean hasCapability(final Capability<?> capability, final @Nullable EnumFacing facing){
    if(capability == CapabilityEnergy.ENERGY){
      return getEnergy() != null;
    }
    return super.hasCapability(capability, facing);
  }

  @Override
  public void update(){
    if(energy != null){
      energy.update();
    }
  }

  /**
   * I suppose I can use this within my own code, like in guis and stuff, as long as I know that tile has
   * a Custom Energy Storage.
   */
  public CustomEnergyStorage getEnergy(){
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

  public boolean needsEnergy(){
    if(energy.canReceive()){
      return energy.needsEnergy();
    }
    return false;
  }

}
