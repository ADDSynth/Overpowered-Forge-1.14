package addsynth.energy;

import javax.annotation.Nonnegative;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

// Original version from CJMinecraft: https://github.com/CJMinecraft01/

/**
 * ADDSynth's own implementation of an Energy Storage object.
 * @author ADDSynth
 */
public class Energy { // NOTE: Convert to long if double becomes a problem

    protected double energy;
    @Nonnegative
    protected double capacity;
    @Nonnegative
    protected double maxReceive;
    @Nonnegative
    protected double maxExtract;

    private double previous_energy;
    private double energy_in;
    private double energy_out;
    private double difference;

// ================================ CONSTRUCTORS ====================================

    public Energy(){
      this(0,0,0,0);
    }

    public Energy(final double capacity){
      this(0, capacity, capacity, capacity);
    }

    public Energy(final double capacity, final double maxTransferRate){
      this(0, capacity, maxTransferRate, maxTransferRate);
    }

    public Energy(final double capacity, final double maxReceive, final double maxExtract){
      this(0, capacity, maxReceive, maxExtract);
    }

    /**
     * @param initial_energy
     * @param capacity
     * @param maxReceive
     * @param maxExtract
     */
    public Energy(final double initial_energy, final double capacity, final double maxReceive, final double maxExtract){
      this.capacity = capacity;
      this.maxReceive = maxReceive;
      this.maxExtract = maxExtract;
      this.energy = initial_energy;
    }

// ================================= NBT READ / WRITE =================================

      /**
	 * Read and set all values from the data inside the given {@link CompoundNBT}
	 * 
	 * @param nbt The {@link CompoundNBT} with all the data
	 */
	public final void readFromNBT(final CompoundNBT nbt){
        CompoundNBT energy_tag = nbt.getCompound("EnergyStorage");
		this.energy     = energy_tag.getDouble("Energy");
		this.capacity   = energy_tag.getInt("Capacity");
		this.maxReceive = energy_tag.getDouble("MaxReceive");
		this.maxExtract = energy_tag.getDouble("MaxExtract");
	}

	/**
	 * Write all of the data to the {@link CompoundNBT} provided
	 * 
	 * @param nbt The {@link CompoundNBT} to write to
	 */
	public final void writeToNBT(final CompoundNBT nbt){
	    CompoundNBT energy_tag = new CompoundNBT();
		energy_tag.putDouble("Energy",     this.energy);
		energy_tag.putDouble("Capacity",   this.capacity);
		energy_tag.putDouble("MaxReceive", this.maxReceive);
		energy_tag.putDouble("MaxExtract", this.maxExtract);
		nbt.put("EnergyStorage", energy_tag);
	}

// =========================== TRANSMIT / RECEIVE ===================================

  /** You may want to push a large amount of energy into this object, but this is
   *  the actual energy it will receive when respecting its maxReceive variable. 
   */
  public final double simulateReceive(final double energy){
    return Math.min(energy, getRequestedEnergy());
  }

  /** You may want to extract a large amount of energy, but this is the actual
   *  amount of energy that will be extracted.
   */
  public final double simulateExtract(final double energy){
    return Math.min(energy, getAvailableEnergy());
  }

  /** Adds energy to this object. */
  public final void receiveEnergy(final double energy_to_add){
    final double actual_energy = simulateReceive(energy_to_add);
    energy += actual_energy;
    energy_in += actual_energy;
  }

  /** Extracts energy and returns the amount extracted. */
  public final double extractEnergy(final double energy_requested){
    final double actual_energy = simulateExtract(energy_requested);
    energy -= actual_energy;
    energy_out += actual_energy;
    return actual_energy;
  }

  /** Extracts the most amount of energy that can be extracted, respecting the maxExtract variable. */
  public final double extractAvailableEnergy(){
    final double actual_energy = getAvailableEnergy();
    energy -= actual_energy;
    energy_out += actual_energy;
    return actual_energy;
  }

  /** Returns maximum amount of energy we can extract, restricted by the maxExtract variable. */
  public final double getAvailableEnergy(){
    return Math.min(Math.max(energy, 0), maxExtract);
  }

  /** Returns the maximum energy it can receive (restricted by the maxReceive variable)
   *  or returns the last bit of energy needed to reach capacity.
   */
  public final double getRequestedEnergy(){
    return Math.min(maxReceive, getEnergyNeeded());
  }

  /** Automatically extracts the most that we can from the supplied energy object,
   *  respecting the maxExtract of the energy, and the maxReceive of our energy.
   * @param energy_storage
   */
  public final void extract_from(final Energy energy_storage){
    final double actual_amount = Math.min(this.getRequestedEnergy(), energy_storage.getAvailableEnergy());
    receiveEnergy(actual_amount);
    energy_storage.extractEnergy(actual_amount);
  }

  /** Pushes as much energy as we can into the supplied energy object, respecting
   *  the maxExtract of our energy, and the maxReceive of the other energy.
   * @param energy_storage
   */
  public final void push_energy_into(final Energy energy_storage){
    final double actual_amount = Math.min(this.getAvailableEnergy(), energy_storage.getRequestedEnergy());
    extractEnergy(actual_amount);
    energy_storage.receiveEnergy(actual_amount);
  }

// ================================= SETTERS =====================================

	/**
	 * Sets the current energy
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
	 * @param capacity The capacity to set
	 */
	public final void setCapacity(final int capacity){
		this.capacity = capacity;
	}

	/**
	 * Sets the maximum transfer rate to and from this EnergyStorage.
	 * @param transferRate The max transfer to set
	 */
	public final void setTransferRate(final int transferRate){
		this.maxReceive = transferRate;
		this.maxExtract = transferRate;
	}

	/**
	 * Set the current max receive
	 * @param maxReceive The max receive to set
	 */
	public final void setMaxReceive(final int maxReceive){
		this.maxReceive = maxReceive;
	}

	/**
	 * Set the current max extract
	 * @param maxExtract The max extract to set
	 */
	public final void setMaxExtract(final int maxExtract){
		this.maxExtract = maxExtract;
	}

  public final void set(Energy energy){
    this.energy     = energy.getEnergy();
    this.capacity   = energy.getCapacity();
    this.maxExtract = energy.getMaxExtract();
    this.maxReceive = energy.getMaxReceive();
  }

// ================================== GETTERS =================================

    public final double getEnergy(){
      return energy;
    }

    public final double getCapacity(){
      return capacity;
    }

	/**
	 * Get the maximum energy this can receive
	 * @return The maximum energy this can receive
	 */
	public final double getMaxReceive(){
		return this.maxReceive;
	}

	/**
	 * Get the maximum energy that can be extracted
	 * @return The maximum energy that can be extracted
	 */
	public final double getMaxExtract(){
		return this.maxExtract;
	}

  /** Returns the amount of energy needed to reach Capacity. */
  public final double getEnergyNeeded(){
    return energy < capacity ? capacity - energy : 0;
  }

  /**
   * Uses current energy level and energy capacity and returns a percentage float.
   * @return energy level percentage AS A FLOAT!
   */
  public final float getEnergyPercentage(){
    double return_value = 0.0;
    if(capacity > 0){ // prevents divide by 0 errors.
      return_value = energy / capacity;
    }
    return (float)return_value;
  }

// ==================================== COMMANDS ====================================

    public final void set_receive_only(){
      this.maxExtract = 0;
    }

    public final void set_extract_only(){
      this.maxReceive = 0;
    }

  public final void set_to_full(){
    energy = capacity;
  }

  public final void setEmpty(){
    energy = 0;
  }

  /** Subtracts capacity from current energy level.<br />
   *  For example, if <code>energy</code> = 20 and <code>capacity</code> = 100,
   *  this function will set <code>energy</code> to -80.
   */
  public final void extract_all_energy(){
    energy -= capacity;
  }

// =================================== QUERIES ======================================

  public boolean canExtract(){
    return maxExtract > 0;
  }

  public boolean canReceive(){
    return maxReceive > 0;
  }

  /** @return true if energy is equal or greater than max capacity. */
  public final boolean isFull(){
    return energy >= capacity;
  }

  public final boolean isEmpty(){
    return energy <= 0;
  }

  public final boolean needsEnergy(){
    return energy < capacity;
  }

  public final boolean hasEnergy(){
    return energy > 0;
  }

// ======================================== MISC =======================================

  /** This should be called in the TileEntity's update() function, as the last thing that executes in there.
   *  This should be called in the server AND the client.
   */
  public final void update(){
    difference = energy - previous_energy;
    previous_energy = energy;
    energy_in = 0;
    energy_out = 0;
  }

  public final double getEnergyDifference(){
    return difference;
  }

  @Override
  public String toString(){
    return "Energy: "+energy+"/"+capacity;
  }

}
