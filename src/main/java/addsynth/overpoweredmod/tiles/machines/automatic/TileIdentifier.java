package addsynth.overpoweredmod.tiles.machines.automatic;

import addsynth.core.game.Compatability;
import addsynth.core.inventory.SlotData;
import addsynth.core.items.ItemUtility;
import addsynth.core.util.JavaUtils;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.compatability.baubles.RingEffects;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.ArmorEffects;
import addsynth.overpoweredmod.game.core.Tools;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class TileIdentifier extends PassiveMachine {

  private static final Item[] unidentified_armor = JavaUtils.combine_arrays(
    Tools.unidentified_armor[0],
    Tools.unidentified_armor[1],
    Tools.unidentified_armor[2],
    Tools.unidentified_armor[3],
    Tools.unidentified_armor[4]
  );
  public static final Item[] input_filter = Compatability.BAUBLES.loaded ? JavaUtils.combine_arrays(unidentified_armor, Tools.ring) : unidentified_armor;

  public TileIdentifier(){
    super(new SlotData[]{new SlotData(input_filter,1)},0,new CustomEnergyStorage(Values.identifier_required_energy),Values.identifier_work_time);
  }

  @Override
  protected final void test_condition(){ // OPTIMIZE all versions
    can_run = false;
    final ItemStack input_stack = input_inventory.getStackInSlot(0);
    if(input_stack != null){
      final Item input_item = input_stack.getItem();
      for(Item item : input_filter){
        if(input_item == item && input_stack.getCount() == 1){
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
        ItemStack stack = null;
        if(item.armor_material == null){
          // unidentified Items of type Ring SHOULD NOT BE registered if Baubles isn't available, but still...
          int i;
          for(i = 0; i < 4; i++){
            if(item == Tools.ring[i]){
              stack = new ItemStack(Tools.magic_ring[i]);
              RingEffects.set_ring_effects(i,stack);
              break;
            }
          }
        }
        else{
          stack = new ItemStack(ItemUtility.get_armor(item.armor_material, item.equipment_type),1);
          ArmorEffects.enchant(stack);
        }
        if(stack != null){
          input_inventory.setStackInSlot(0,stack);
        }
      }
    }
    can_run = false;
  }

}
