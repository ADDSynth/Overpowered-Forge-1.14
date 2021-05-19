package addsynth.energy.gameplay.machines.energy_storage;

import javax.annotation.Nullable;
import addsynth.energy.gameplay.Config;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.tiles.energy.TileEnergyBattery;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileEnergyStorage extends TileEnergyBattery implements INamedContainerProvider {

  public TileEnergyStorage(){
    super(Tiles.ENERGY_CONTAINER,
      new Energy(
        Config.energy_storage_container_capacity.get(),
        Config.energy_storage_container_extract_rate.get()
      )
    );
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerEnergyStorage(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
