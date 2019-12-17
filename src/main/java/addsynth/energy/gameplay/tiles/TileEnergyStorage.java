package addsynth.energy.gameplay.tiles;

import javax.annotation.Nullable;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.containers.ContainerEnergyStorage;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileEnergyStorage extends TileEnergyBattery implements INamedContainerProvider {

  public TileEnergyStorage(){
    super(Tiles.ENERGY_CONTAINER, new CustomEnergyStorage(Values.energy_storage_container_capacity.get(),Values.energy_storage_container_extract_rate.get()));
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
