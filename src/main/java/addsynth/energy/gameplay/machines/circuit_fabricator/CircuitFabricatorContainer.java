package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class CircuitFabricatorContainer extends TileEntityContainer<TileCircuitFabricator> {

  public CircuitFabricatorContainer(int id, PlayerInventory player_inventory, PacketBuffer data){
    super(Containers.CIRCUIT_FABRICATOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  public CircuitFabricatorContainer(int id, PlayerInventory player_inventory, TileCircuitFabricator tile){
    super(Containers.CIRCUIT_FABRICATOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public static final int input_slot_x = 76;
  public static final int input_slot_y = 48;

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory, 31, 140);
    for(InputSlot slot : tile.getInputSlots()){
      addSlot(slot);
    }
    addSlot(new OutputSlot(tile, 0, 182, 74));
  }

}
