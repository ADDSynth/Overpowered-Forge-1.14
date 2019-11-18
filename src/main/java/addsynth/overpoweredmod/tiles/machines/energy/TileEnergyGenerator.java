package addsynth.overpoweredmod.tiles.machines.energy;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyTransmitter;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.tiles.Tiles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class TileEnergyGenerator extends TileEnergyTransmitter {

  public static final Item[] input_filter = new Item[] {
    Init.energy_crystal_shards, Init.energy_crystal, Item.BLOCK_TO_ITEM.get(Init.light_block)
  };

  public TileEnergyGenerator(){
    super(Tiles.GENERATOR, 1,input_filter,0,new CustomEnergyStorage());
  }

  private final void setGeneratorData(Item item){
    if(item == Init.energy_crystal){
      energy.setEnergyLevel(Values.energy_crystal_energy);
      energy.setMaxExtract(Values.energy_crystal_max_extract);
    }
    if(item == Init.energy_crystal_shards){
      energy.setEnergyLevel(Values.energy_crystal_shards_energy);
      energy.setMaxExtract(Values.energy_crystal_shards_max_extract);
    }
    if(item == OverpoweredMod.registry.getItemBlock(Init.light_block)){
      energy.setEnergyLevel(Values.light_block_energy);
      energy.setMaxExtract(Values.light_block_max_extract);
    }
  }

  @Override
  public final void tick(){
    if(world != null){
      if(world.isRemote == false){
        if(energy.isEmpty()){
          if(input_inventory.getStackInSlot(0) != null){ // is there an item in the slot?
            ItemStack stack = input_inventory.extractItem(0,1,false);
            setGeneratorData(stack.getItem()); // set energy units and extraction rate
            update_data();
          }
        }
        super.tick(); // Dispatch energy to the energy grid.
      }
      else{
        energy.update();
      }
    }
  }

}
