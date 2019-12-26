package addsynth.overpoweredmod.tiles.machines.automatic;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.containers.ContainerElectricFurnace;
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

  public static final Item[] furnace_input = get_furnace_input();
  private ItemStack result;

  public TileElectricFurnace(){
    super(Tiles.ELECTRIC_FURNACE, 1, furnace_input, 1, new CustomEnergyStorage(Values.electric_furnace_required_energy.get()), Values.electric_furnace_work_time.get());
  }

  @Override
  protected final void test_condition(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    final ItemStack output = output_inventory.getStackInSlot(0);
    result = ItemStack.EMPTY; // input == ItemStack.EMPTY ? null : FurnaceRecipes.instance().getSmeltingResult(input);
    can_run = (input != ItemStack.EMPTY && input.getCount() > 0) && (output == ItemStack.EMPTY || output_inventory.can_add(0, result));
  }

  @Override
  protected final void performWork(){
    input_inventory.decrease(0);
    output_inventory.insertItem(0, result.copy(), false);
  }

  /** Gets all of the input Items from Furnace recipes to use as an Item Filter. */
  private static final Item[] get_furnace_input(){ // MAYBE: eventually calculate this at a more stable time?
    final ArrayList<ItemStack> list = new ArrayList<ItemStack>(); // new ArrayList<>(FurnaceRecipes.instance().getSmeltingList().keySet());
    final int max = list.size();
    final Item[] input = new Item[max];
    int i;
    ItemStack stack;
    for(i = 0; i < max; i++){
      stack = list.get(i);
      if(stack != null){
        if(stack != ItemStack.EMPTY){
          input[i] = stack.getItem();
        }
      }
      // else{
      //   OverpoweredMod.log.error("Found a null key in the HashMap for Furnace recipes. I don't think "+
      //   "it's supposed to be there. No crash, you can keep playing.");
      // }
    }
    return input;
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
