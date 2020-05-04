package addsynth.energy;

import addsynth.core.util.DecimalNumber;
import net.minecraft.nbt.CompoundNBT;

// Original version from CJMinecraft: https://github.com/CJMinecraft01/

/**
 * ADDSynth's own implementation of an Energy Storage object.
 * @author ADDSynth
 */
public class Energy {

  protected final DecimalNumber energy     = new DecimalNumber();
  protected final DecimalNumber capacity   = new DecimalNumber();
  protected final DecimalNumber maxReceive = new DecimalNumber();
  protected final DecimalNumber maxExtract = new DecimalNumber();

  protected final DecimalNumber previous_energy = new DecimalNumber();
  protected final DecimalNumber energy_in       = new DecimalNumber();
  protected final DecimalNumber energy_out      = new DecimalNumber();
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
  public final void readFromNBT(final CompoundNBT nbt){
    final CompoundNBT energy_tag = nbt.getCompound("EnergyStorage");
    this.energy.set(         energy_tag.getDouble("Energy")    );
    this.capacity.set(       energy_tag.getDouble("Capacity")  );
    this.maxReceive.set(     energy_tag.getDouble("MaxReceive"));
    this.maxExtract.set(     energy_tag.getDouble("MaxExtract"));
    this.previous_energy.set(energy_tag.getDouble("Previous Energy"));
    this.energy_in.set(      energy_tag.getDouble("Energy In"));
    this.energy_out.set(     energy_tag.getDouble("Energy Out"));
    this.difference.set(     energy_tag.getDouble("Difference"));
  }

  /**
   * Write all of the data to the {@link CompoundNBT} provided
   * 
   * @param nbt The {@link CompoundNBT} to write to
   */
  public final void writeToNBT(final CompoundNBT nbt){
    final CompoundNBT energy_tag = new CompoundNBT();
	energy_tag.putDouble("Energy",     this.energy.get());
	energy_tag.putDouble("Capacity",   this.capacity.get());
	energy_tag.putDouble("MaxReceive", this.maxReceive.get());
	energy_tag.putDouble("MaxExtract", this.maxExtract.get());
	energy_tag.putDouble("Previous Energy", this.previous_energy.get());
	energy_tag.putDouble("Energy In",  this.energy_in.get());
	energy_tag.putDouble("Energy Out", this.energy_out.get());
	energy_tag.putDouble("Difference", this.difference.get());
	nbt.put("EnergyStorage", energy_tag);
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

  /** Adds energy to this object. */
  public final void receiveEnergy(final double energy_to_add){
    final double actual_energy = simulateReceive(energy_to_add);
    energy.add(actual_energy);
    energy_in.add(actual_energy);
  }

  /** Extracts energy and returns the amount extracted. */
  public final double extractEnergy(final double energy_requested){
    final double actual_energy = simulateExtract(energy_requested);
    energy.subtract(actual_energy);
    energy_out.add(actual_energy);
    return actual_energy;
  }

  /** Extracts the most amount of energy that can be extracted, respecting the maxExtract variable. */
  public final double extractAvailableEnergy(){
    final double actual_energy = getAvailableEnergy();
    energy.subtract(actual_energy);
    energy_out.add(actual_energy);
    return actual_energy;
  }

  /** Returns maximum amount of energy we can extract, restricted by the maxExtract variable. */
  public final double getAvailableEnergy(){
    return Math.min(Math.max(energy.get(), 0), maxExtract.get());
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
  }

  /**
   * Sets the energy and capacity at once to the specified value.
   * @param energy
   */
  public final void setEnergyLevel(final int energy){
    this.energy.set(energy);
    this.capacity.set(energy);
  }

  /**
   * Sets the total capacity.
   * @param capacity The capacity to set
   */
  public final void setCapacity(final int capacity){
    this.capacity.set(capacity);
  }

  /**
   * Sets the maximum transfer rate to and from this EnergyStorage.
   * @param transferRate The max transfer to set
   */
  public final void setTransferRate(final int transferRate){
    this.maxReceive.set(transferRate);
    this.maxExtract.set(transferRate);
  }

  /**
   * Set the current max receive
   * @param maxReceive The max receive to set
   */
  public final void setMaxReceive(final int maxReceive){
    this.maxReceive.set(maxReceive);
  }

  /**
   * Set the current max extract
   * @param maxExtract The max extract to set
   */
  public final void setMaxExtract(final int maxExtract){
    this.maxExtract.set(maxExtract);
  }

  public final void set(Energy energy){
    this.energy.set(     energy.getEnergy()     );
    this.capacity.set(   energy.getCapacity()   );
    this.maxExtract.set( energy.getMaxExtract() );
    this.maxReceive.set( energy.getMaxReceive() );
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
  public final double getMaxReceive(){
    return this.maxReceive.get();
  }

  /**
   * Get the maximum energy that can be extracted
   * @return The maximum energy that can be extracted
   */
  public final double getMaxExtract(){
    return this.maxExtract.get();
  }

  /** Returns the amount of energy needed to reach Capacity. */
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

  public final void set_receive_only(){
    this.maxExtract.set(0);
  }

  public final void set_extract_only(){
    this.maxReceive.set(0);
  }

  public final void set_to_full(){
    energy.set(capacity.get());
  }

  public final void setEmpty(){
    energy.set(0);
  }

  /** Subtracts capacity from current energy level.<br />
   *  For example, if <code>energy</code> = 20 and <code>capacity</code> = 100,
   *  this function will set <code>energy</code> to -80.
   */
  public final void extract_all_energy(){
    energy.subtract(capacity.get());
  }

// =================================== QUERIES ======================================

  public boolean canExtract(){
    return maxExtract.get() > 0;
  }

  public boolean canReceive(){
    return maxReceive.get() > 0;
  }

  /** @return true if energy is equal or greater than max capacity. */
  public final boolean isFull(){
    return energy.get() >= capacity.get();
  }

  public final boolean isEmpty(){
    return energy.get() <= 0;
  }

  public final boolean needsEnergy(){
    return energy.get() < capacity.get();
  }

  public final boolean hasEnergy(){
    return energy.get() > 0;
  }

// ======================================== MISC =======================================

  /** This should be called in the TileEntity's update() function.
   */
  public final boolean update(){
    final double diff = energy.get() - previous_energy.get();
    // difference.set(diff);
    difference.set(energy_in.get() - energy_out.get());
    previous_energy.set(energy.get());
    energy_in.set(0);
    energy_out.set(0);
    return diff != 0;
  }

  public final double getEnergyDifference(){
    return difference.get();
  }

  @Override
  public String toString(){
    return "Energy: "+energy+"/"+capacity;
  }

}
