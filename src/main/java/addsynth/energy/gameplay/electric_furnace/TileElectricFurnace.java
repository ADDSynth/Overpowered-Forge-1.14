package addsynth.energy.gameplay.electric_furnace;

import javax.annotation.Nullable;
import addsynth.core.util.RecipeUtil;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileElectricFurnace extends PassiveMachine implements INamedContainerProvider {

  public static final Item[] furnace_input = (Item[])RecipeUtil.getFurnaceIngredients().toArray();
  private ItemStack result;

  public TileElectricFurnace(){
    super(Tiles.ELECTRIC_FURNACE, 1, furnace_input, 1, new CustomEnergyStorage(Values.electric_furnace_required_energy.get()), Values.electric_furnace_work_time.get());
  }

  @Override
  protected final void test_condition(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    final ItemStack output = output_inventory.getStackInSlot(0);
    result = input.isEmpty() ? null : RecipeUtil.getFurnaceResult(input);
    can_run = (input != ItemStack.EMPTY && input.getCount() > 0) && (output == ItemStack.EMPTY || output_inventory.can_add(0, result));
  }

  @Override
  protected final void performWork(){
    input_inventory.decrease(0);
    output_inventory.insertItem(0, result.copy(), false);
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerElectricFurnace(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
