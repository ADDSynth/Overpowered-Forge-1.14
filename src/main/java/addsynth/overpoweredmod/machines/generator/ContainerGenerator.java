package addsynth.overpoweredmod.machines.generator;

import addsynth.core.container.BaseContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerGenerator extends BaseContainer<TileCrystalEnergyGenerator> {

  public ContainerGenerator(final int id, final PlayerInventory player_inventory, final TileCrystalEnergyGenerator tile){
    super(Containers.GENERATOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerGenerator(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.GENERATOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,12,94);
    addSlot(new InputSlot(tile,0,TileCrystalEnergyGenerator.input_filter,57,20));
  }

}
