package addsynth.overpoweredmod.tiles.machines.automatic;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.WorkMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.containers.ContainerCrystalGenerator;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.tiles.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCrystalMatterReplicator extends WorkMachine implements INamedContainerProvider {

  public TileCrystalMatterReplicator(){
    super(Tiles.CRYSTAL_MATTER_REPLICATOR, 0, null, 8,
      new CustomEnergyStorage(Values.crystal_matter_generator_required_energy.get()),
      Values.crystal_matter_generator_work_time.get());
  }

  @Override
  protected final void performWork(){
    final Random random = new Random();
    final int slot = random.nextInt(8);
    final ItemStack stack = new ItemStack(Gems.index[slot].shard,1);
    output_inventory.insertItem(slot, stack, false);
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerCrystalGenerator(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
