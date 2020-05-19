package addsynth.overpoweredmod.machines.magic_infuser;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.material.MaterialsUtil;
import addsynth.core.util.JavaUtils;
import addsynth.core.util.StringUtil;
import addsynth.energy.tiles.machines.TileWorkMachine;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.item.ItemStack;

public final class TileMagicInfuser extends TileWorkMachine implements INamedContainerProvider {

  public static final Item[] getFilter(){
    return MaterialsUtil.getFilter(
      MaterialsUtil.getRubies(), MaterialsUtil.getTopaz(), MaterialsUtil.getCitrine(), MaterialsUtil.getEmeralds(),
      MaterialsUtil.getDiamonds(), MaterialsUtil.getSapphires(), MaterialsUtil.getAmethysts(), MaterialsUtil.getQuartz()
    );
  }

  public TileMagicInfuser(){
    super(
      Tiles.MAGIC_INFUSER,
      new SlotData[]{ // FUTURE now the SlotData needs to be constructed every time, because of the Item Tags in getFilter().
        // Have slots automatically rebuild their filter by calling a build filter function? Make a Filter its own class object?
        new SlotData(Items.BOOK),
        new SlotData(JavaUtils.combine_arrays(getFilter(), new Item[]{Init.energy_crystal, Init.void_crystal}))
      },
      1,
      MachineValues.magic_infuser
    );
  }

  @Override
  protected final void test_condition(){
    can_run = !input_inventory.getStackInSlot(0).isEmpty() &&
              !input_inventory.getStackInSlot(1).isEmpty() &&
               output_inventory.getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void perform_work(){
    final Enchantment enchantment = get_enchantment();
    if(enchantment != null){
      final ItemStack enchant_book = new ItemStack(Items.ENCHANTED_BOOK, 1);
      enchant_book.addEnchantment(enchantment, enchantment == Enchantments.FORTUNE ? 2 : 1);
      output_inventory.setStackInSlot(0, enchant_book);
    }
    working_inventory.setEmpty();
  }

  private final Enchantment get_enchantment(){
    // https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments
    Enchantment enchantment = null;
    final Item item = working_inventory.getStackInSlot(1).getItem();
    final Random random = new Random();
    // 1 in 50 chance you get either Curse of Binding or Curse of Vanishing
    if(MaterialsUtil.match(item, MaterialsUtil.getRubies())){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.POWER; break;
      case 1: enchantment = Enchantments.PUNCH; break;
      // TODO: DO NOT use a switch statement for this, instead, move enchantments in an array (pass in T[] array), and call a Utility function that will automatically return a random object from the array.
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getTopaz())){
      switch(random.nextInt(4)){
      case 0: enchantment = Enchantments.BLAST_PROTECTION; break;
      case 1: enchantment = Enchantments.FIRE_ASPECT; break;
      case 2: enchantment = Enchantments.FIRE_PROTECTION; break;
      case 3: enchantment = Enchantments.FLAME; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getCitrine())){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.SHARPNESS; break;
      case 1: enchantment = Enchantments.BANE_OF_ARTHROPODS; break;
      case 2: enchantment = Enchantments.SMITE; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getEmeralds())){
      switch(random.nextInt(2)){
      case 0: enchantment = Enchantments.FEATHER_FALLING; break;
      case 1: enchantment = Enchantments.RESPIRATION; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getDiamonds())){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.PROJECTILE_PROTECTION; break;
      case 1: enchantment = Enchantments.PROTECTION; break;
      case 2: enchantment = Enchantments.THORNS; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getSapphires())){
      switch(random.nextInt(5)){
      case 0: enchantment = Enchantments.DEPTH_STRIDER; break;
      case 1: enchantment = Enchantments.AQUA_AFFINITY; break;
      case 2: enchantment = Enchantments.FROST_WALKER; break;
      case 3: enchantment = Enchantments.LUCK_OF_THE_SEA; break;
      case 4: enchantment = Enchantments.LURE; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getAmethysts())){
      switch(random.nextInt(3)){
      case 0: enchantment = Enchantments.LOOTING; break;
      case 1: enchantment = Enchantments.KNOCKBACK; break;
      case 2: enchantment = Enchantments.SWEEPING; break;
      }
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getQuartz())){
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
      OverpoweredMod.log.error("function get_enchantment() in "+TileMagicInfuser.class.getSimpleName()+" returned a null enchantment! With "+StringUtil.getName(item)+" as input.");
      Thread.dumpStack();
    }
    return enchantment;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerMagicInfuser(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
