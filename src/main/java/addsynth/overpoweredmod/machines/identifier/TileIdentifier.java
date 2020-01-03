package addsynth.overpoweredmod.machines.identifier;

import javax.annotation.Nullable;
import addsynth.core.game.Compatability;
import addsynth.core.inventory.SlotData;
import addsynth.core.items.ItemUtility;
import addsynth.core.util.JavaUtils;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
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
    super(Tiles.IDENTIFIER,new SlotData[]{new SlotData(input_filter,1)},0,new CustomEnergyStorage(Values.identifier_required_energy.get()),Values.identifier_work_time.get());
  }

  @Override
  protected final void test_condition(){ // The Identifier only has 1 Input slot and 0 Output slots. This must remain AS IS.
    can_run = false;
    final ItemStack input_stack = input_inventory.getStackInSlot(0);
    if(input_stack.isEmpty() == false){
      final Item input_item = input_stack.getItem();
      for(Item item : input_filter){
        if(input_item == item){
          can_run = true;
          break;
        }
      }
    }
  }

  @Override
  protected final void performWork(){
    final ItemStack input = input_inventory.getStackInSlot(0);
    if(input != null){
      if(input.getItem() instanceof UnidentifiedItem){
        final UnidentifiedItem item = (UnidentifiedItem)(input.getItem());
        final ItemStack stack = new ItemStack(ItemUtility.get_armor(item.armor_material, item.equipment_type),1);
        ArmorEffects.enchant(stack);
        input_inventory.setStackInSlot(0,stack);
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
