package addsynth.energy.gameplay.machines.generator;

import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.energy.TileStandardGenerator;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;

public final class TileGenerator extends TileStandardGenerator implements INamedContainerProvider {

  public TileGenerator(){
    super(Tiles.GENERATOR, null);
    input_inventory.isItemStackValid = (Integer slot, ItemStack stack) -> {
      return AbstractFurnaceTileEntity.isFuel(stack) && stack.getItem() != Items.LAVA_BUCKET;
    };
  }

  @Override
  protected void setGeneratorData(){
    final ItemStack item = input_inventory.extractItem(0, 1, false);
    final int burn_time = ForgeHooks.getBurnTime(item);
    if(burn_time > 0){
      // 1 Coal/Charcoal should provide 8,000 units of energy and take 80 seconds to use up.
      energy.setEnergyAndCapacity(burn_time * 5);
      // Therefore, we should use up 5 energy each tick.
      energy.setMaxExtract(Math.max(5, (double)burn_time / 320));
    }
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerGenerator(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
