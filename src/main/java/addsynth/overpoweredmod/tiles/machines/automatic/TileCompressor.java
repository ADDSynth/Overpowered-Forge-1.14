package addsynth.overpoweredmod.tiles.machines.automatic;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.recipes.CompressorRecipes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public final class TileCompressor extends PassiveMachine {

  private ItemStack result;

  public TileCompressor(){
    super(2,null,1,new CustomEnergyStorage(Values.compressor_required_energy),Values.compressor_work_time);
    // has a high work time to give the user a chance to change the recipe. (same as furnace cook time.)
  }

  @Override
  protected final void test_condition(){
    final ItemStack[] input = new ItemStack[2];
      input[0] = input_inventory.getStackInSlot(0);
      input[1] = input_inventory.getStackInSlot(1);
    if(CompressorRecipes.match(input)){
      result = CompressorRecipes.getResult(input);
      can_run = output_inventory.can_add(0, result);
    }
    else{
      can_run = false;
    }
  }

  @Override
  protected final void performWork(){
    world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.AMBIENT, 0.7f, 0.5f); // lowest pitch can be
    output_inventory.insertItem(0, result.copy(), false);
    input_inventory.decrease_input();
  }

}
