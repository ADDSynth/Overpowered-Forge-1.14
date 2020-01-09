package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerOreRefinery extends BaseContainer<TileAdvancedOreRefinery> {

  public ContainerOreRefinery(final int id, final PlayerInventory player_inventory, final TileAdvancedOreRefinery tile){
    super(Containers.ADVANCED_ORE_REFINERY, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerOreRefinery(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.ADVANCED_ORE_REFINERY, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,104);
    addSlot(new InputSlot(tile, 0, OreRefineryRecipes.get_input_filter(), 39, 43));
    addSlot(new OutputSlot(tile, 0, 94,43));
  }

}
