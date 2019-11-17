package addsynth.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

// Original version from CJMinecraft: https://github.com/CJMinecraft01/

/**
 * An improved version of an {@link EnergyStorage}.
 * This class is not final so mods can further extend on this class if they need to.
 * 
 * @author ADDSynth
 */
public class CustomEnergyStorage extends EnergyStorage {

    private int previous_energy;
    private int energy_in;
    private int energy_out;
    private int difference;

    public CustomEnergyStorage(){
      super(0,0,0);
    }

    public CustomEnergyStorage(final int capacity){
      super(capacity, capacity, capacity);
    }

    public CustomEnergyStorage(final int capacity, final int maxTransferRate){
      super(capacity, maxTransferRate, maxTransferRate);
    }

    public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract){
      super(capacity, maxReceive, maxExtract);
    }

    /**
     * @param initial_energy
     * @param capacity
     * @param maxReceive
     * @param maxExtract
     */
    public CustomEnergyStorage(final int initial_energy, final int capacity, final int maxReceive, final int maxExtract){
      super(capacity, maxReceive, maxExtract);
      this.energy = initial_energy;
    }

      /**
	 * Read and set all values from the data inside the given {@link CompoundNBT}
	 * 
	 * @param nbt The {@link CompoundNBT} with all the data
	 */
	public final void readFromNBT(final CompoundNBT nbt){
        CompoundNBT energy_tag = nbt.getCompound("EnergyStorage");
		this.energy = energy_tag.getInt("Energy");
		this.capacity = energy_tag.getInt("Capacity");
		this.maxReceive = energy_tag.getInt("MaxReceive");
		this.maxExtract = energy_tag.getInt("MaxExtract");
	}

	/**
	 * Write all of the data to the {@link CompoundNBT} provided
	 * 
	 * @param nbt The {@link CompoundNBT} to write to
	 */
	public final void writeToNBT(final CompoundNBT nbt){
	    CompoundNBT energy_tag = new CompoundNBT();
		energy_tag.putInt("Energy", this.energy);
		energy_tag.putInt("Capacity", this.capacity);
		energy_tag.putInt("MaxReceive", this.maxReceive);
		energy_tag.putInt("MaxExtract", this.maxExtract);
		nbt.put("EnergyStorage", energy_tag);
	}

    public final void receiveEnergy(final int energy_to_add){
      super.receiveEnergy(energy_to_add, false);
      energy_in += energy_to_add;
    }

	/**
	 * Sets the current energy
	 * 
	 * @param energy The energy to set
	 */
	public final void setEnergy(final int energy){
		this.energy = energy;
	}

    /**
     * Sets the energy and capacity at once to the specified value.
     * @param energy
     */
    public final void setEnergyLevel(final int energy){
      this.energy = energy;
      this.capacity = energy;
    }

	/**
	 * Sets the total capacity.
	 * 
	 * @param capacity The capacity to set
	 */
	public final void setCapacity(final int capacity){
		this.capacity = capacity;
	}

	/**
	 * Sets the maximum transfer rate to and from this EnergyStorage.
	 * 
	 * @param transferRate The max transfer to set
	 */
	public final void setTransferRate(final int transferRate){
		this.maxReceive = transferRate;
		this.maxExtract = transferRate;
	}

	/**
	 * Set the current max receive
	 * 
	 * @param maxReceive The max receive to set
	 */
	public final void setMaxReceive(final int maxReceive){
		this.maxReceive = maxReceive;
	}

	/**
	 * Set the current max extract
	 * 
	 * @param maxExtract The max extract to set
	 */
	public final void setMaxExtract(final int maxExtract){
		this.maxExtract = maxExtract;
	}

    public final int getEnergy(){
      return super.getEnergyStored();
    }

    public final int getCapacity(){
      return super.getMaxEnergyStored();
    }

	/**
	 * Get the maximum energy this can receive
	 * 
	 * @return The maximum energy this can receive
	 */
	public final int getMaxReceive(){
		return this.maxReceive;
	}

	/**
	 * Get the maximum energy that can be extracted
	 * 
	 * @return The maximum energy that can be extracted
	 */
	public final int getMaxExtract(){
		return this.maxExtract;
	}

    public final void set_receive_only(){
      this.maxExtract = 0;
    }

    public final void set_extract_only(){
      this.maxReceive = 0;
    }

  /**
   * Uses current energy level and energy capacity and returns a percentage float.
   * 
   * @return energy level percentage AS A FLOAT!
   */
  public final float getEnergyPercentage(){
    float return_value = 0.0f;
    if(capacity > 0){ // prevents divide by 0 errors.
      return_value = (float)energy / (float)capacity;
    }
    return return_value;
  }


  /** @return true if energy is equal or greater than max capacity. */
  public final boolean isFull(){
    return energy >= capacity;
  }

  public final boolean isEmpty(){
    return energy <= 0;
  }

  public final void set_to_full(){
    energy = capacity;
  }

  public final void setEmpty(){
    energy = 0;
  }

  public final void extract_all_energy(){
    energy -= capacity;
  }

  public final boolean needsEnergy(){
    return energy < capacity; // && energy_acquired < maxReceive; DELETE
  }

  /** Returns the amount of energy needed to reach Capacity. */
  public final int getEnergyNeeded(){
    return energy < capacity ? capacity - energy : 0;
  }

  public final boolean hasEnergy(){
    return energy > 0;
  }

  public final void set(CustomEnergyStorage energy){
    this.energy = energy.getEnergyStored();
    this.capacity = energy.getMaxEnergyStored();
    this.maxExtract = energy.getMaxExtract();
    this.maxReceive = energy.getMaxReceive();
  }

  /* FUTURE: Energy system rewrite notes:
  Okay, I think I got it. For Transmitters, keep a list of Transmitter/Receiver pairs! on the Transmitter side, save a value of
  how much the Transmitter is willing to give to the Receiver.
  Do the same for Receivers! Have Receivers search the networks it is connected to and keep a list of Transmitter/Receiver pairs!
  And also keep a value of how much that Receiver is willing to receive from all detected transmitters, divided equally.
  Both devices attempt to transmit energy, BUT:
  Only transmit energy IF:
    The receiver shows up in the transmitter's list / The transmitter shows up in the Receiver list.
    Once transmitted energy, set a boolean value on both pairs.
    So on device update, the first thing we should do is set all booleans to false (for not yet transmitted energy.)
    AND! Once we've transmited energy (by subtracting the lowest number from each device) We tell the device that still has energy
    to re-distrubute the left-over energy.
  */

  /** This should be called in the TileEntity's update() function, as the last thing that executes in there.
   *  This should be called in the server AND the client.
   */
  public final void update(){
    difference = energy - previous_energy;
    previous_energy = energy;
    energy_in = 0;
    energy_out = 0;
  }

  public final int getEnergyDifference(){
    return difference;
  }

  @Override
  public String toString(){
    return "Energy: "+energy+"/"+capacity;
  }

}
