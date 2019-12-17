package addsynth.overpoweredmod.containers;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.core.inventory.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import addsynth.overpoweredmod.tiles.machines.energy.TileEnergyGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerGenerator extends BaseContainer<TileEnergyGenerator> {

  public ContainerGenerator(final int id, final PlayerInventory player_inventory){
    super(Containers.GENERATOR, id, player_inventory);
  }

  public ContainerGenerator(final int id, final PlayerInventory player_inventory, final TileEnergyGenerator tile){
    super(Containers.GENERATOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerGenerator(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.GENERATOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,94);
    addSlot(new InputSlot(tile,0,TileEnergyGenerator.input_filter,53,20));
  }

}
