package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.tiles.machines.energy.TileEnergyGenerator;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerGenerator extends BaseContainer {

  public ContainerGenerator(final int id, final PlayerInventory player_inventory){
    super(Containers.GENERATOR, id, player_inventory, Machines.generator);
    make_player_inventory(player_inventory,8,94);
    addSlot(new InputSlot(null,0,TileEnergyGenerator.input_filter,53,20));
  }

}
