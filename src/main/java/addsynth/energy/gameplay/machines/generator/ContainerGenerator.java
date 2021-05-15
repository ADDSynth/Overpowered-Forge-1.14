package addsynth.energy.gameplay.machines.generator;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.FuelSlot;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerGenerator extends TileEntityContainer<TileGenerator> {

  public ContainerGenerator(int id, PlayerInventory player_inventory, TileGenerator tile){
    super(Containers.GENERATOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerGenerator(int id, PlayerInventory player_inventory, PacketBuffer data){
    super(Containers.GENERATOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory, 12, 94);
    addSlot(new FuelSlot(tile, 0, 57, 20));
  }

}
