package addsynth.energy.tiles;

import addsynth.core.inventory.SlotData;
import addsynth.energy.CustomEnergyStorage;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEnergyReceiver extends TileEnergyWithStorage {

  protected boolean running = true;

  public TileEnergyReceiver(final TileEntityType type, final SlotData[] slots, final int output_slots, final CustomEnergyStorage energy){
    super(type, slots, output_slots, energy);
    if(energy != null){
      energy.set_receive_only();
    }
  }

  public TileEnergyReceiver(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final CustomEnergyStorage energy){
    super(type, input_slots, filter, output_slots, energy);
    if(energy != null){
      energy.set_receive_only();
    }
  }

  @Override
  public void read(CompoundNBT nbt){
    super.read(nbt);
    running = nbt.getBoolean("Running");
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt){
    super.write(nbt);
    nbt.putBoolean("Running",running);
    return nbt;
  }

  public void toggleRun(){
    running = !running;
    update_data();
  }

  public final boolean isRunning(){
    return running;
  }

  public String getStatus(){
    if(running){
      if(getEnergy().needsEnergy()){
        return "Charging";
      }
      return "Ready";
    }
    return "Off";
  }

  @Override
  public boolean needsEnergy(){
    if(running){
      return getEnergy().needsEnergy();
    }
    return false;
  }

}
