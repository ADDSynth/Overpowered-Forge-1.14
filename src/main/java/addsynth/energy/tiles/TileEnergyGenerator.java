package addsynth.energy.tiles;

import addsynth.energy.Energy;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEnergyGenerator extends TileEnergyWithStorage {

  public TileEnergyGenerator(final TileEntityType type, final Energy energy){
    super(type, energy);
    if(this.energy != null){
      this.energy.set_extract_only();
    }
  }

  public TileEnergyGenerator(final TileEntityType type, final int input_slots, final Item[] filter, final int output_slots, final Energy energy){
    super(type, input_slots, filter, output_slots, energy);
    if(this.energy != null){
      this.energy.set_extract_only();
    }
  }

}
