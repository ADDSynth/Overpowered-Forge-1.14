package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.machines.TileAlwaysOnMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 *  The Advanced Ore Refinery sort of acts like a Furnace, in that it essentially smelts things.
 *  But it doesn't smelt everything like a Furnace does, it accepts all items but it will only
 *  work on Ores. After an ore is done being worked on it will return its smelted output in the
 *  multiplied factor amount.
 */
public final class TileAdvancedOreRefinery extends TileAlwaysOnMachine implements INamedContainerProvider {

  public TileAdvancedOreRefinery(){
    super(Tiles.ADVANCED_ORE_REFINERY,
      1, OreRefineryRecipes.get_input_filter(), 1,
      MachineValues.advanced_ore_refinery
    );
  }

  @Override
  protected final boolean test_condition(){
    return inventory.can_work(OreRefineryRecipes::get_result);
  }

  @Override
  protected final void perform_work(){
    inventory.output_result();
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerOreRefinery(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
