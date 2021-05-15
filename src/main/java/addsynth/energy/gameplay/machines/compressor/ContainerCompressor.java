package addsynth.energy.gameplay.machines.compressor;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerCompressor extends TileEntityContainer<TileCompressor> {

  public ContainerCompressor(final int id, final PlayerInventory player_inventory, final TileCompressor tile){
    super(Containers.COMPRESSOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerCompressor(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.COMPRESSOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,100);
    addSlot(new InputSlot( tile, 0, CompressorRecipes.INSTANCE.filter, 32, 42));
    addSlot(new OutputSlot(tile, 0, 128, 42));
  }

}
