package addsynth.overpoweredmod.machines.generator;

import javax.annotation.Nullable;
import addsynth.energy.Energy;
import addsynth.energy.tiles.TileEnergyTransmitter;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileEnergyGenerator extends TileEnergyTransmitter implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[] {
    Init.energy_crystal_shards, Init.energy_crystal, Item.BLOCK_TO_ITEM.get(Init.light_block)
  };

  public TileEnergyGenerator(){
    super(Tiles.GENERATOR, 1,input_filter,0,new Energy());
  }

  private final void setGeneratorData(final Item item){
    if(item == Init.energy_crystal){
      energy.setEnergyLevel(MachineValues.energy_crystal_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_max_extract.get());
    }
    if(item == Init.energy_crystal_shards){
      energy.setEnergyLevel(MachineValues.energy_crystal_shards_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_shards_max_extract.get());
    }
    if(item == OverpoweredMod.registry.getItemBlock(Init.light_block)){
      energy.setEnergyLevel(MachineValues.light_block_energy.get());
      energy.setMaxExtract(MachineValues.light_block_max_extract.get());
    }
  }

  @Override
  public final void tick(){
    if(world != null){
      if(world.isRemote == false){
        if(energy.isEmpty()){
          if(input_inventory.getStackInSlot(0).isEmpty() == false){ // is there an item in the slot?
            final ItemStack stack = input_inventory.extractItem(0,1,false);
            setGeneratorData(stack.getItem()); // set energy units and extraction rate
            update_data();
          }
        }
        energy.update(world);
      }
    }
  }

  @Override
  @Nullable
  public Container createMenu(final int windowID, final PlayerInventory player_inventory, final PlayerEntity player){
    return new ContainerGenerator(windowID, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
