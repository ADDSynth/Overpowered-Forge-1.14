package addsynth.overpoweredmod.machines.crystal_matter_generator;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IOutputInventory;
import addsynth.core.game.inventory.OutputInventory;
import addsynth.energy.lib.tiles.machines.TilePassiveMachine;
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

public final class TileCrystalMatterGenerator extends TilePassiveMachine implements IOutputInventory, INamedContainerProvider {

  private final OutputInventory output_inventory;

  public TileCrystalMatterGenerator(){
    super(Tiles.CRYSTAL_MATTER_REPLICATOR, MachineValues.crystal_matter_generator);
    output_inventory = OutputInventory.create(this, 8);
  }

  @Override
  protected final void perform_work(){
    final int slot = (new Random()).nextInt(8);
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

  @Override
  public void onInventoryChanged(){
    // no need to react to inventory change
  }

  @Override
  public void drop_inventory(){
    output_inventory.drop_in_world(world, pos);
  }

  @Override
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
