package addsynth.overpoweredmod.tiles.machines.automatic;

import java.util.Random;
import addsynth.core.inventory.SlotData;
import addsynth.core.util.JavaUtils;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.tiles.Tiles;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public final class TileMagicUnlocker extends PassiveMachine {

  public static final SlotData[] slot_data = new SlotData[]{
    new SlotData(Items.BOOK),
    new SlotData(JavaUtils.combine_arrays(Gems.gem_items, new Item[]{Init.energy_crystal, Init.void_crystal}))
  };

  public TileMagicUnlocker(){
    super(Tiles.MAGIC_INFUSER,slot_data,1,new CustomEnergyStorage(Values.magic_infuser_required_energy.get()),Values.magic_infuser_work_time.get());
  }

  @Override
  protected final void test_condition(){
    can_run = !input_inventory.getStackInSlot(0).isEmpty() &&
              !input_inventory.getStackInSlot(1).isEmpty() &&
               output_inventory.getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void performWork(){
    final Enchantment enchantment = get_enchantment();
    if(enchantment != null){
      ItemStack enchant_book = new ItemStack(Items.ENCHANTED_BOOK, 1);
      enchant_book.addEnchantment(enchantment, enchantment == Enchantments.FORTUNE ? 2 : 1);
      output_inventory.setStackInSlot(0, enchant_book);
      input_inventory.decrease(0);
      input_inventory.decrease(1);
    }
  }

  private final Enchantment get_enchantment(){
    // https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments
    Enchantment enchantment = null;
    final Item item = input_inventory.getStackInSlot(1).getItem();
    final Random random = new Random();
    // 1 in 50 chance you get either Curse of Binding or Curse of Vanishing
    if(item == Gems.ruby){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.POWER; break;
      case 1: enchantment = Enchantments.PUNCH; break;
      }
    }
    if(item == Gems.topaz){
      switch(random.nextInt(4)){
      case 0: enchantment = Enchantments.BLAST_PROTECTION; break;
      case 1: enchantment = Enchantments.FIRE_ASPECT; break;
      case 2: enchantment = Enchantments.FIRE_PROTECTION; break;
      case 3: enchantment = Enchantments.FLAME; break;
      }
    }
    if(item == Gems.citrine){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.SHARPNESS; break;
      case 1: enchantment = Enchantments.BANE_OF_ARTHROPODS; break;
      case 2: enchantment = Enchantments.SMITE; break;
      }
    }
    if(item == Gems.emerald){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.FEATHER_FALLING; break;
      case 1: enchantment = Enchantments.RESPIRATION; break;
      }
    }
    if(item == Gems.diamond){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.PROJECTILE_PROTECTION; break;
      case 1: enchantment = Enchantments.PROTECTION; break;
      case 2: enchantment = Enchantments.THORNS; break;
      }
    }
    if(item == Gems.sapphire){
      switch(random.nextInt(5)){
      case 0: enchantment = Enchantments.DEPTH_STRIDER; break;
      case 1: enchantment = Enchantments.AQUA_AFFINITY; break;
      case 2: enchantment = Enchantments.FROST_WALKER; break;
      case 3: enchantment = Enchantments.LUCK_OF_THE_SEA; break;
      case 4: enchantment = Enchantments.LURE; break;
      }
    }
    if(item == Gems.amethyst){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.LOOTING; break;
      case 1: enchantment = Enchantments.KNOCKBACK; break;
      case 2: enchantment = Enchantments.SWEEPING; break;
      }
    }
    if(item == Gems.quartz){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.EFFICIENCY; break;
      case 1: enchantment = Enchantments.UNBREAKING; break;
      }
    }
    if(item == Init.energy_crystal){
      switch(random.nextInt(4)){
      case 0: enchantment = Enchantments.FORTUNE; break;
      case 1: enchantment = Enchantments.INFINITY; break;
      case 2: enchantment = Enchantments.SILK_TOUCH; break;
      case 3: enchantment = Enchantments.MENDING; break;
      }
    }
    if(item == Init.void_crystal){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.BINDING_CURSE; break;
      case 1: enchantment = Enchantments.VANISHING_CURSE; break;
      }
    }
    if(enchantment == null){
      OverpoweredMod.log.error("function get_enchantment() in TileMagicUnlocker returned a null enchantment! With "+item.toString()+" as input.");
      Thread.dumpStack();
    }
    return enchantment;
  }

}
