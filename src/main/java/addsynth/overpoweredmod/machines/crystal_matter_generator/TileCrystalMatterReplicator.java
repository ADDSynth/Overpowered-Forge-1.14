package addsynth.overpoweredmod.machines.crystal_matter_generator;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.energy.Energy;
import addsynth.energy.tiles.machines.TileWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCrystalMatterReplicator extends TileWorkMachine implements INamedContainerProvider {

  public TileCrystalMatterReplicator(){
    super(Tiles.CRYSTAL_MATTER_REPLICATOR, 0, null, 8, MachineValues.crystal_matter_generator);
  }

  @Override
  protected final void perform_work(){
    final int slot = (new Random()).nextInt(8);
    final ItemStack stack = new ItemStack(Gems.index[slot].shard,1);
    output_inventory.insertItem(slot, stack, false);
  }

  @Override
  protected void test_condition(){
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
