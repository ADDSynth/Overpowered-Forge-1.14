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

/** Doubles the output of any ores. Works with ores from other mods as well. Only works with ores. */
public final class TileAdvancedOreRefinery extends TileAlwaysOnMachine implements INamedContainerProvider {

  public TileAdvancedOreRefinery(){
    super(Tiles.ADVANCED_ORE_REFINERY, 1, OreRefineryRecipes.get_input_filter(), 1, MachineValues.advanced_ore_refinery);
    inventory.setRecipeProvider(OreRefineryRecipes::get_result);
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
