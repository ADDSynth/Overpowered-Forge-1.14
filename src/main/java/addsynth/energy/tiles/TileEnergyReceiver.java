package addsynth.energy.tiles;

import addsynth.core.inventory.SlotData;
import addsynth.energy.Energy;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEnergyReceiver extends TileEnergyWithStorage {

  public TileEnergyReceiver(final TileEntityType type, final SlotData[] slots, final int output_slots, final Energy energy){
    super(type, slots, output_slots, energy);
    if(energy != null){
      energy.set_receive_only();
    }
  }

  public TileEnergyReceiver(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final Energy energy){
    super(type, input_slots, filter, output_slots, energy);
    if(energy != null){
      energy.set_receive_only();
    }
  }

}
