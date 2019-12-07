package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.entity.player.PlayerInventory;

public final class ContainerCrystalGenerator extends BaseContainer {

  public ContainerCrystalGenerator(final int id, final PlayerInventory player_inventory){
    super(Containers.CRYSTAL_MATTER_GENERATOR, id, player_inventory, Machines.crystal_matter_generator);
    make_player_inventory(player_inventory,8,110);
    int i;
    for(i = 0; i < 8; i++){
      addSlot(new OutputSlot(null,i,8 + (i*18),54));
    }
  }

}
