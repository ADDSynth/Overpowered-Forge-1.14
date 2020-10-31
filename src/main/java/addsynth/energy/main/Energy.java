package addsynth.energy.main;

import javax.annotation.Nonnegative;
import addsynth.core.util.math.DecimalNumber;
import net.minecraft.nbt.CompoundNBT;

// Original inspiration from CJMinecraft: https://github.com/CJMinecraft01/

/**
 * ADDSynth's own implementation of an Energy Storage object.
 * @author ADDSynth
 */
public class Energy {

  /** Is set to true whenever any variable changes to signal that various things
   *  need to be updated in the {@link Energy#tick} event.
   */
  protected boolean changed;

  protected final DecimalNumber energy     = new DecimalNumber();
  protected final DecimalNumber capacity   = new DecimalNumber();
  protected final DecimalNumber maxReceive = new DecimalNumber();
  protected final DecimalNumber maxExtract = new DecimalNumber();

  // protected final DecimalNumber previous_energy = new DecimalNumber(); DELETE all mentions of previous_energy
  /** Diagnostic variable that is recalculated every tick. Mainly used on Client side. */
  protected final DecimalNumber energy_in       = new DecimalNumber();
  /** Diagnostic variable that is recalculated every tick. Mainly used on Client side. */
  protected final DecimalNumber energy_out      = new DecimalNumber();
  /** This represents REAL transfer of energy, by using the {@link Energy#energy_in energy_in} and
   *  {@link Energy#energy_out energy_out} variables, which only change when REAL energy is transferred. **/
  protected final DecimalNumber difference      = new DecimalNumber();

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
    this.capacity.set(capacity);
    this.maxReceive.set(maxReceive);
    this.maxExtract.set(maxExtract);
    this.energy.set(initial_energy);
  }

// ================================= NBT READ / WRITE =================================

  /**
   * Read and set all values from the data inside the given {@link CompoundNBT}
   * 
   * @param nbt The {@link CompoundNBT} with all the data
   */
  public final void loadFromNBT(final CompoundNBT nbt){
    final CompoundNBT energy_tag = nbt.getCompound("EnergyStorage");
    this.energy.set(         energy_tag.getDouble("Energy")    );
    this.capacity.set(       energy_tag.getDouble("Capacity")  );
    this.maxReceive.set(     energy_tag.getDouble("MaxReceive"));
    this.maxExtract.set(     energy_tag.getDouble("MaxExtract"));
    // this.previous_energy.set(energy_tag.getDouble("Previous Energy"));
    this.energy_in.set(      energy_tag.getDouble("Energy In"));
    this.energy_out.set(     energy_tag.getDouble("Energy Out"));
    this.difference.set(     energy_tag.getDouble("Difference"));
  }

  /**
   * Write all of the data to the {@link CompoundNBT} provided
   * 
   * @param nbt The {@link CompoundNBT} to write to
   */
  public final void saveToNBT(final CompoundNBT nbt){
    difference.set(energy_in.get() - energy_out.get()); // record real energy difference

    final CompoundNBT energy_tag = new CompoundNBT();
	energy_tag.putDouble("Energy",     this.energy.get());
	energy_tag.putDouble("Capacity",   this.capacity.get());
	energy_tag.putDouble("MaxReceive", this.maxReceive.get());
	energy_tag.putDouble("MaxExtract", this.maxExtract.get());
	// energy_tag.putDouble("Previous Energy", this.previous_energy.get());
	energy_tag.putDouble("Energy In",  this.energy_in.get());
	energy_tag.putDouble("Energy Out", this.energy_out.get());
	energy_tag.putDouble("Difference", this.difference.get());
	nbt.put("EnergyStorage", energy_tag);
	
    if(energy_in.get() != 0 || energy_out.get() != 0){
      // previous_energy.set(energy.get());
      energy_in.set(0);
      energy_out.set(0);
      changed = true; // Update next frame to reset everything to 0.
    }
  }

// =========================== TRANSMIT / RECEIVE ===================================

  /** You may want to push a large amount of energy into this object, but this is
   *  the actual energy it will receive when respecting its maxReceive variable. 
   */
  public final double simulateReceive(final double energy){
    return Math.min(DecimalNumber.align_to_accuracy(energy), getRequestedEnergy());
  }

  /** You may want to extract a large amount of energy, but this is the actual
   *  amount of energy that will be extracted.
   */
  public final double simulateExtract(final double energy){
    return Math.min(DecimalNumber.align_to_accuracy(energy), getAvailableEnergy());
  }

  /** Adds energy to this object. Only inserts as much as possible, respecting maxReceive
   *  and maximum Capacity variables. */
  public final void receiveEnergy(final double energy_to_add){
    final double actual_energy = simulateReceive(energy_to_add);
    energy.add(actual_energy);
    energy_in.add(actual_energy);
    changed = true;
  }

  /** Extracts energy and returns the amount extracted. */
  public final double extractEnergy(final double energy_requested){
    final double actual_energy = simulateExtract(energy_requested);
    energy.subtract(actual_energy);
    energy_out.add(actual_energy);
    changed = true;
    return actual_energy;
  }

  /** Extracts the most amount of energy that can be extracted, respecting the maxExtract variable. */
  public final double extractAvailableEnergy(){
    final double actual_energy = getAvailableEnergy();
    energy.subtract(actual_energy);
    energy_out.add(actual_energy);
    changed = true;
    return actual_energy;
  }

  /** Returns maximum amount of energy we can extract, restricted by the maxExtract variable. */
  public final double getAvailableEnergy(){
    return Math.min( Math.max( energy.get(), 0) , maxExtract.get() );
  }

  /** Returns the maximum energy it can receive (restricted by the maxReceive variable)
   *  or returns the last bit of energy needed to reach capacity.
   */
  public final double getRequestedEnergy(){
    return Math.min(maxReceive.get(), getEnergyNeeded());
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
    this.energy.set(energy);
    changed = true;
  }

  /**
   * Sets the energy and capacity at once to the specified value.
   * @param energy
   */
  public final void setEnergyLevel(final int energy){
    this.energy.set(energy);
    this.capacity.set(energy);
    changed = true;
  }

  /**
   * Sets the total capacity.
   * @param capacity The capacity to set
   */
  public final void setCapacity(final int capacity){
    this.capacity.set(capacity);
    changed = true;
  }

  /**
   * Sets the maximum transfer rate to and from this EnergyStorage.
   * @param transferRate The max transfer to set
   */
  public void setTransferRate(final int transferRate){
    this.maxReceive.set(transferRate);
    this.maxExtract.set(transferRate);
    changed = true;
  }

  /**
   * Set the current max receive
   * @param maxReceive The max receive to set
   */
  public void setMaxReceive(final int maxReceive){
    this.maxReceive.set(maxReceive);
    changed = true;
  }

  /**
   * Set the current max extract
   * @param maxExtract The max extract to set
   */
  public void setMaxExtract(final int maxExtract){
    this.maxExtract.set(maxExtract);
    changed = true;
  }

  public final void set(final Energy energy){
    this.energy.set(     energy.getEnergy()     );
    this.capacity.set(   energy.getCapacity()   );
    this.maxExtract.set( energy.getMaxExtract() );
    this.maxReceive.set( energy.getMaxReceive() );
    this.energy_in.set(  energy.get_energy_in() );
    this.energy_out.set( energy.get_energy_out());
    changed = true;
  }

  /** Use this to manually set the energy_in variable, which is just an indicator
   *  of how much energy was inserted into this object. It is reset to 0 every tick.
   *  It is recommended you don't use this and use the receiveEnergy() methods instead.
   * @param energy_in
   */
  public final void setEnergyIn(final @Nonnegative double energy_in){
    this.energy_in.set(energy_in);
    changed = true;
  }
  
  /** Use this to manually set the energy_out variable, which is just an indicator
   *  of how much energy this Energy object received. It is reset to 0 every tick.
   *  It is recommended you don't use this and use the extractEnergy() methods instead.
   * @param energy_out
   */
  public final void setEnergyOut(final @Nonnegative double energy_out){
    this.energy_out.set(energy_out);
    changed = true;
  }

// ================================== GETTERS =================================

  public final double getEnergy(){
    return energy.get();
  }

  public final double getCapacity(){
    return capacity.get();
  }

  /**
   * Get the maximum energy this can receive
   * @return The maximum energy this can receive
   */
  public double getMaxReceive(){
    return this.maxReceive.get();
  }

  /**
   * Get the maximum energy that can be extracted
   * @return The maximum energy that can be extracted
   */
  public double getMaxExtract(){
    return this.maxExtract.get();
  }

  /** Returns the amount of energy needed to reach Capacity. For normal transfer of energy
   *  please use {@link Energy#getRequestedEnergy() getRequestedEnergy} instead. */
  public final double getEnergyNeeded(){
    final double energy   = this.energy.get();
    final double capacity = this.capacity.get();
    return energy < capacity ? capacity - energy : 0;
  }

  public final double get_energy_in(){
    return energy_in.get();
  }

  public final double get_energy_out(){
    return energy_out.get();
  }

  public final double getDifference(){
    return difference.get();
  }

  /**
   * Uses current energy level and energy capacity and returns a percentage float.
   * @return energy level percentage AS A FLOAT!
   */
  public final float getEnergyPercentage(){
    double return_value = 0.0;
    if(capacity.get() > 0){ // prevents divide by 0 errors.
      return_value = energy.get() / capacity.get();
    }
    return (float)return_value;
  }

// ==================================== COMMANDS ====================================

  public final void set_to_full(){
    energy.set(capacity.get());
    changed = true;
  }

  public final void setEmpty(){
    energy.set(0);
    changed = true;
  }

  /** Subtracts capacity from current energy level.<br />
   *  For example, if <code>energy</code> = 100 and <code>capacity</code> = 80,
   *  this function will set <code>energy</code> to 20. Energy is never set below 0.
   */
  public final void subtract_capacity(){
    energy.subtract(Math.min(energy.get(), capacity.get()));
    changed = true;
  }

// =================================== SPECIAL ======================================

  /** Sets the new Energy level of this Energy object, ignoring the maxExtract and
   *  maxReceive variables. Counts as real energy transfer. This is only intended to
   *  be used in special circumstances. 
   * @param new_energy_level
   */
  public final void set_new_energy_level(final double new_energy_level){
    final double actual_energy = Math.min(Math.max(DecimalNumber.align_to_accuracy(new_energy_level), 0), capacity.get());
    final double difference = actual_energy - energy.get();
    if(difference != 0){
      energy.set(actual_energy);
      if(difference > 0){ energy_in.add(difference); }
      if(difference < 0){ energy_out.add(Math.abs(difference)); }
      changed = true;
    }
  }

  /** Extracts energy without being limited by the maxExtract variable.
   *  Counts as real transfer of energy. This is only intended to be used under
   *  special circumstances. Please use the standard extract functions above.
   * @param energy_to_extract
   */
  public final double extract_bypass(final double energy_to_extract){
    final double extracted_energy = Math.min(DecimalNumber.align_to_accuracy(energy_to_extract), energy.get());
    energy.subtract(extracted_energy);
    energy_out.add(extracted_energy);
    changed = true;
    return extracted_energy;
  }

  /** Gives energy to this energy object without being limited by the maxReceive
   *  variable. Counts as real transfer of energy. This is only intended to be used
   *  under special circumstances. Please use the standard receive methods above.
   * @param energy_to_receive
   */
  public final void insert_bypass(final double energy_to_receive){
    final double received_energy = Math.min(DecimalNumber.align_to_accuracy(energy_to_receive), getEnergyNeeded());
    energy.add(received_energy);
    energy_in.add(received_energy);
    changed = true;
  }

  /** Extracts all energy ignoring the maxExtract variable. Counts as real
   *  transfer of energy. This is only intended to be used in special
   *  circumstances. Please use the standard extract methods above.
   */
  public final double extract_all_energy(){
    final double extracted_energy = energy.get();
    energy.set(0);
    energy_out.add(extracted_energy);
    changed = true;
    return extracted_energy;
  }

// =================================== QUERIES ======================================

  public boolean canExtract(){
    return maxExtract.get() > 0;
  }

  public boolean canReceive(){
    return maxReceive.get() > 0;
  }

  /** Returns true if energy is equal or greater than max capacity. */
  public final boolean isFull(){
    return energy.get() >= capacity.get();
  }

  public final boolean isEmpty(){
    return energy.get() <= 0;
  }

  /** Returns whether this Energy object has NOT reached capacity. */
  public final boolean needsEnergy(){
    return energy.get() < capacity.get();
  }

  public final boolean hasEnergy(){
    return energy.get() > 0;
  }

// ======================================== MISC =======================================

  /** This should be called in your TileEntity's update() or tick() function. */
  public final boolean tick(){
    if(changed){
      changed = false;
      return true;
    }
    return false;
  }

  @Override
  public String toString(){
    return "Energy: "+energy+"/"+capacity;
  }

}