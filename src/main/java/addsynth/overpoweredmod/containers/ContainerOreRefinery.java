package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.recipes.OreRefineryRecipes;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerOreRefinery extends BaseContainer {

  public ContainerOreRefinery(final int id, final PlayerInventory player_inventory){
    super(Containers.ADVANCED_ORE_REFINERY, id, player_inventory, Machines.advanced_ore_refinery);
    make_player_inventory(player_inventory,8,104);
    addSlot(new InputSlot(null, 0, OreRefineryRecipes.get_input(), 39, 43));
    addSlot(new OutputSlot(null, 0, 94,43));
  }

}
