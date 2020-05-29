package addsynth.energy.gameplay.electric_furnace;

import javax.annotation.Nullable;
import addsynth.core.util.RecipeUtil;
import addsynth.energy.registers.Tiles;
import addsynth.energy.tiles.machines.MachineData;
import addsynth.energy.tiles.machines.MachineType;
import addsynth.energy.tiles.machines.TileWorkMachine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileElectricFurnace extends TileWorkMachine implements INamedContainerProvider {

  private ItemStack result;

  public TileElectricFurnace(){
    super(Tiles.ELECTRIC_FURNACE, 1, get_filter(), 1, new MachineData(MachineType.ALWAYS_ON, 200, 5, 0, 0));
  }

  public static final Item[] get_filter(){
    return RecipeUtil.getFurnaceIngredients();
  }

  @Override
  protected final boolean test_condition(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    final ItemStack output = output_inventory.getStackInSlot(0);
    result = input.isEmpty() ? null : RecipeUtil.getFurnaceResult(input);
    return (input != ItemStack.EMPTY && input.getCount() > 0) && (output == ItemStack.EMPTY || output_inventory.can_add(0, result));
  }

  @Override
  protected final void perform_work(){
    working_inventory.setEmpty();
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
