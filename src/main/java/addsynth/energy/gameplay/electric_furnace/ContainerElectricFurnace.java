package addsynth.energy.gameplay.electric_furnace;

import addsynth.core.container.MachineContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerElectricFurnace extends MachineContainer<TileElectricFurnace> {

  public ContainerElectricFurnace(final int id, final PlayerInventory player_inventory, final TileElectricFurnace tile){
    super(Containers.ELECTRIC_FURNACE, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerElectricFurnace(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.ELECTRIC_FURNACE, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,90);
    addSlot(new InputSlot(tile, 0, TileElectricFurnace.get_filter(),32,40));
    addSlot(new OutputSlot(tile,0,128,40));
  }

}
