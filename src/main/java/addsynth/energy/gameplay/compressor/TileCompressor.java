package addsynth.energy.gameplay.compressor;

import javax.annotation.Nullable;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipes;
import addsynth.energy.registers.Tiles;
import addsynth.energy.tiles.machines.TileWorkMachine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCompressor extends TileWorkMachine implements INamedContainerProvider {

  private ItemStack result;

  public TileCompressor(){
    super(Tiles.COMPRESSOR,2,null,1,Config.compressor_data);
  }

  @Override
  protected final boolean test_condition(){
    final ItemStack[] input = {input_inventory.getStackInSlot(0), input_inventory.getStackInSlot(1)};
    result = CompressorRecipes.getResult(input, world);
    return output_inventory.can_add(0, result);
  }

  @Override
  protected final void perform_work(){
    world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.7f, 0.5f); // lowest pitch can be
    output_inventory.insertItem(0, result.copy(), false);
    working_inventory.setEmpty();
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
