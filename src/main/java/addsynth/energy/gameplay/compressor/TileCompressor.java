package addsynth.energy.gameplay.compressor;

import javax.annotation.Nullable;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipes;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCompressor extends PassiveMachine implements INamedContainerProvider {

  private ItemStack result;

  public TileCompressor(){
    super(Tiles.COMPRESSOR,2,null,1,new CustomEnergyStorage(Values.compressor_required_energy.get()),Values.compressor_work_time.get());
    // has a high work time to give the user a chance to change the recipe. (same as furnace cook time.)
  }

  @Override
  protected final void test_condition(){
    final ItemStack[] input = {input_inventory.getStackInSlot(0), input_inventory.getStackInSlot(1)};
    result = CompressorRecipes.getResult(input, world);
    can_run = result != null ? output_inventory.can_add(0, result) : false;
  }

  @Override
  protected final void performWork(){
    world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.AMBIENT, 0.7f, 0.5f); // lowest pitch can be
    output_inventory.insertItem(0, result.copy(), false);
    input_inventory.decrease_input();
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerCompressor(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
