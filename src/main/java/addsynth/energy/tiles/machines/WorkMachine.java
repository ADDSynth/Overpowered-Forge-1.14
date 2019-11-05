package addsynth.energy.tiles.machines;

import addsynth.core.inventory.SlotData;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyReceiver;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

/** WorkMachines check if their Energy Storage is full every tick, and if it is,
 *  it begins to increment a {@link #work_units} variable, then executes {@link #performWork()}
 *  when <code>work_units</code> has reached <code>work_units_required</code>.
 * @author ADDSynth
 */
public abstract class WorkMachine extends TileEnergyReceiver implements ITickable {

  protected int work_units;
  protected final int work_units_required;

  public WorkMachine(final SlotData[] slots, final int output_slots, final CustomEnergyStorage energy, final int work_units_required){
    super(slots, output_slots, energy);
    this.work_units_required = work_units_required;
  }

  public WorkMachine(final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy, final int work_units_required){
    super(input_slots, filter, output_slots, energy);
    this.work_units_required = work_units_required;
  }

  @Override
  public void readFromNBT(final NBTTagCompound nbt){
    super.readFromNBT(nbt);
    work_units = nbt.getInteger("WorkUnits");
  }

  @Override
  public NBTTagCompound writeToNBT(final NBTTagCompound nbt){
    super.writeToNBT(nbt);
    nbt.setInteger("WorkUnits",work_units);
    return nbt;
  }

  @Override
  public void update(){ // MAYBE: make the update function protected, everywhere.
    if(world != null){
      if(world.isRemote == false){
        if(running){
          machine_tick();
        }
      }
    }
    energy.update();
  }

  protected void machine_tick(){
    if(energy.isFull()){
      work_units += 1;
      if(work_units >= work_units_required){
        performWork();
        energy.setEmpty();
        work_units = 0;
      }
      update_data(); // only needs to be called when work_units change, also called when energy changed (TileEnergyTransmitter), and when run is toggled (TileEnergyReceiver)
    }
  }

  /** Returns a percentage float value between 0 and 1. */
  public final float getWorkTimePercentage(){
    if(work_units_required > 0){
      return (float)work_units / (float)work_units_required;
    }
    return 0.0f;
  }

  /** Returns time left to perform one operation in ticks. */
  public final int getTimeLeft(){
    return work_units_required - work_units;
  }

  /** Returns the total number of ticks needed to complete all the tasks this
   *  machine is currently working on. Machines with more than 1 Input slot must
   *  override this and implement their own way of determining total work time.
   */
  public int getTotalTimeLeft(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty() == false){
      return (stack.getCount() * work_units_required) - work_units;
    }
    return work_units_required - work_units;
  }

  /**
   * <b>Note:</b> You don't need to call update_data() in this function because super
   *              classes WorkMachine and PassiveMachine already does it for you.
   */
  protected abstract void performWork();

  @Override
  public String getStatus(){
    if(running){
      if(energy.needsEnergy()){
        return "Charging";
      }
      return "Working";
    }
    return "Off";
  }

}
