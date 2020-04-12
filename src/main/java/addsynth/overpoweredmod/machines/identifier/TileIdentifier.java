package addsynth.overpoweredmod.machines.identifier;

import javax.annotation.Nullable;
import addsynth.core.game.Compatability;
import addsynth.core.inventory.SlotData;
import addsynth.core.items.ItemUtility;
import addsynth.core.util.JavaUtils;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Tools;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileIdentifier extends PassiveMachine implements INamedContainerProvider {

  public static final Item[] input_filter = JavaUtils.combine_arrays(
    Tools.unidentified_armor[0],
    Tools.unidentified_armor[1],
    Tools.unidentified_armor[2],
    Tools.unidentified_armor[3],
    Tools.unidentified_armor[4]
  );

  public TileIdentifier(){
    super(Tiles.IDENTIFIER, 1, input_filter, 1, new CustomEnergyStorage(
      MachineValues.identifier_required_energy.get()), MachineValues.identifier_work_time.get());
  }

  @Override
  protected final void test_condition(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    final ItemStack output = output_inventory.getStackInSlot(0);
    can_run = input.isEmpty() == false && output.isEmpty();
  }

  @Override
  protected final void performWork(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    if(input.isEmpty() == false){
      if(input.getItem() instanceof UnidentifiedItem){
        final UnidentifiedItem item = (UnidentifiedItem)(input.getItem());
        final ItemStack stack = new ItemStack(ItemUtility.get_armor(item.armor_material, item.equipment_type),1);
        ArmorEffects.enchant(stack);
        input_inventory.decrease(0);
        output_inventory.setStackInSlot(0, stack);
      }
    }
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerIdentifier(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
