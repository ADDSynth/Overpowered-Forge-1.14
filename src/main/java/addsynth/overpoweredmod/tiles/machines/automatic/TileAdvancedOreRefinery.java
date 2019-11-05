package addsynth.overpoweredmod.tiles.machines.automatic;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import net.minecraft.item.ItemStack;

/**
 *  The Advanced Ore Refinery sort of acts like a Furnace, in that it essentially smelts things.
 *  But it doesn't smelt everything like a Furnace does, it accepts all items but it will only
 *  work on Ores. After an ore is done being worked on it will return its smelted output in the
 *  multiplied factor amount.
 */
public final class TileAdvancedOreRefinery extends PassiveMachine {

  private ItemStack result;

  public TileAdvancedOreRefinery(){
    super(1,OreRefineryRecipes.get_input(),1,new CustomEnergyStorage(Values.advanced_ore_refinery_required_energy),Values.advanced_ore_refinery_work_time);
  }

  @Override
  protected final void test_condition(){
    can_run = false;
    final ItemStack input = input_inventory.getStackInSlot(0);
    if(input != null){
      if(OreRefineryRecipes.matches(input.getItem())){
        result = OreRefineryRecipes.get_result(input.getItem());
        can_run = output_inventory.can_add(0, result);
      }
    }
  }

  @Override
  protected final void performWork(){
    output_inventory.insertItem(0, result.copy(), false);
    input_inventory.extractItem(0, 1, false);
  }

}
