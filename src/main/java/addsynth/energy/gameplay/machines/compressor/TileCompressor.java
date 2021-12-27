package addsynth.energy.gameplay.machines.compressor;

import javax.annotation.Nullable;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCompressor extends TileStandardWorkMachine implements INamedContainerProvider {

  public TileCompressor(){
    super(Tiles.COMPRESSOR, 1, CompressorRecipes.INSTANCE.getFilter(), 1, Config.compressor_data);
    inventory.setRecipeProvider(CompressorRecipes.INSTANCE);
  }

  @Override
  protected final void perform_work(){
    world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.7f, 0.5f); // lowest pitch can be
    inventory.finish_work();
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
