package addsynth.overpoweredmod.machines.energy_extractor;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerCrystalEnergyExtractor extends TileEntityContainer<TileCrystalEnergyExtractor> {

  public ContainerCrystalEnergyExtractor(final int id, final PlayerInventory player_inventory, final TileCrystalEnergyExtractor tile){
    super(Containers.CRYSTAL_ENERGY_EXTRACTOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerCrystalEnergyExtractor(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.CRYSTAL_ENERGY_EXTRACTOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory, 12, 94);
    addSlot(new InputSlot(tile, 0, TileCrystalEnergyExtractor.input_filter, 57, 20));
  }

}
