package addsynth.overpoweredmod.tiles.machines.automatic;

import javax.annotation.Nullable;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.containers.ContainerInverter;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileInverter extends PassiveMachine implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[] {Init.energy_crystal, Init.void_crystal};
  private ItemStack result;

  public TileInverter(){
    super(Tiles.INVERTER,1,input_filter,1,new CustomEnergyStorage(Values.inverter_required_energy.get()),Values.inverter_work_time.get());
  }

  @Override
  protected final void test_condition(){
    final ItemStack input_stack = input_inventory.getStackInSlot(0);
    if(input_stack.isEmpty()){
      can_run = false;
    }
    else{
      final Item input_item = input_stack.getItem();
      if(input_item == Init.energy_crystal){ result = new ItemStack(Init.void_crystal,1); }
      if(input_item == Init.void_crystal){   result = new ItemStack(Init.energy_crystal,1); }
      can_run = output_inventory.can_add(0, result);
    }
  }

  @Override
  public final void performWork(){
    input_inventory.extractItem(0,1,false);
    output_inventory.insertItem(0, result, false); // No need to copy this one because we replace the ItemStack every time.
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerInverter(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
