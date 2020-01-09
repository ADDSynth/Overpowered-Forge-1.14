package addsynth.overpoweredmod.machines.crystal_matter_generator;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerCrystalGenerator extends BaseContainer<TileCrystalMatterReplicator> {

  public ContainerCrystalGenerator(final int id, final PlayerInventory player_inventory, final TileCrystalMatterReplicator tile){
    super(Containers.CRYSTAL_MATTER_GENERATOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerCrystalGenerator(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.CRYSTAL_MATTER_GENERATOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,110);
    int i;
    for(i = 0; i < 8; i++){
      addSlot(new OutputSlot(tile,i,8 + (i*18),54));
    }
  }

}
