package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import addsynth.overpoweredmod.tiles.machines.automatic.TileAdvancedOreRefinery;
import net.minecraft.inventory.IInventory;

public final class ContainerOreRefinery extends BaseContainer<TileAdvancedOreRefinery> {

  public ContainerOreRefinery(final IInventory player_inventory, final TileAdvancedOreRefinery tile){
    super(tile);
    make_player_inventory(player_inventory,8,104);
    addSlot(new InputSlot(tile, 0, OreRefineryRecipes.get_input(), 39, 43));
    addSlot(new OutputSlot(tile, 0, 94,43));
  }

}
