package addsynth.overpoweredmod.machines.inverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.energy.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
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

public final class TileInverter extends TileStandardWorkMachine implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[] {Init.energy_crystal, Init.void_crystal};

  public TileInverter(){
    super(Tiles.INVERTER, 1, input_filter, 1, MachineValues.inverter);
  }

  @Override
  protected final boolean test_condition(){
    final ItemStack input_stack = inventory.input_inventory.getStackInSlot(0);
    if(input_stack.isEmpty()){
      return false;
    }
    return inventory.output_inventory.can_add(0, getInverted(input_stack));
  }

  public static final @Nonnull ItemStack getInverted(final ItemStack input_stack){
    final Item item = input_stack.getItem();
    if(item == Init.energy_crystal){ return new ItemStack(Init.void_crystal,1); }
    if(item == Init.void_crystal){   return new ItemStack(Init.energy_crystal,1); }
    return ItemStack.EMPTY;
  }

  @Override
  public final void perform_work(){
    inventory.output_inventory.insertItem(0, getInverted(inventory.working_inventory.getStackInSlot(0)), false);
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
