package addsynth.overpoweredmod.machines.magic_infuser;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.util.StringUtil;
import addsynth.core.util.math.random.RandomUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.item.ItemStack;

public final class TileMagicInfuser extends TileStandardWorkMachine implements INamedContainerProvider {

  // TODO: Change these into actual recipes, and show them in JEI.
  private static final Enchantment[] ruby_enchantments = new Enchantment[]{
    Enchantments.POWER,
    Enchantments.PUNCH
  };
  private static final Enchantment[] topaz_enchantments = new Enchantment[]{
    Enchantments.BLAST_PROTECTION,
    Enchantments.FIRE_ASPECT,
    Enchantments.FIRE_PROTECTION,
    Enchantments.FLAME
  };
  private static final Enchantment[] citrine_enchantments = new Enchantment[]{
    Enchantments.SHARPNESS,
    Enchantments.BANE_OF_ARTHROPODS,
    Enchantments.SMITE,
    Enchantments.QUICK_CHARGE,
    Enchantments.PIERCING
  };
  private static final Enchantment[] emerald_enchantments = new Enchantment[]{
    Enchantments.FEATHER_FALLING,
    Enchantments.RESPIRATION,
    Enchantments.LOYALTY,
    Enchantments.CHANNELING
  };
  private static final Enchantment[] diamond_enchantments = new Enchantment[]{
    Enchantments.PROJECTILE_PROTECTION,
    Enchantments.PROTECTION,
    Enchantments.THORNS
  };
  private static final Enchantment[] sapphire_enchantments = new Enchantment[]{
    Enchantments.DEPTH_STRIDER,
    Enchantments.AQUA_AFFINITY,
    Enchantments.FROST_WALKER,
    Enchantments.LUCK_OF_THE_SEA,
    Enchantments.LURE,
    Enchantments.RIPTIDE,
    Enchantments.IMPALING
  };
  private static final Enchantment[] amethyst_enchantments = new Enchantment[]{
    Enchantments.LOOTING,
    Enchantments.KNOCKBACK,
    Enchantments.SWEEPING,
    Enchantments.MULTISHOT
  };
  private static final Enchantment[] quartz_enchantments = new Enchantment[]{
    Enchantments.EFFICIENCY,
    Enchantments.UNBREAKING
  };
  private static final Enchantment[] celestial_enchantments = new Enchantment[]{
    Enchantments.FORTUNE,
    Enchantments.INFINITY,
    Enchantments.SILK_TOUCH,
    Enchantments.MENDING
  };
  private static final Enchantment[] void_crystal_enchantments = new Enchantment[]{
    Enchantments.BINDING_CURSE,
    Enchantments.VANISHING_CURSE
  };

  public TileMagicInfuser(){
    super(Tiles.MAGIC_INFUSER,
      new SlotData[]{
        new SlotData(Items.BOOK),
        new SlotData(Filters.magic_infuser)
      },
      1,
      MachineValues.magic_infuser
    );
  }

  @Override
  protected final boolean test_condition(){
    return !inventory.input_inventory.getStackInSlot(0).isEmpty() &&
           !inventory.input_inventory.getStackInSlot(1).isEmpty() &&
            inventory.output_inventory.getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void perform_work(){
    final Enchantment enchantment = get_enchantment();
    if(enchantment != null){
      final ItemStack enchant_book = new ItemStack(Items.ENCHANTED_BOOK, 1);
      final EnchantmentData enchantment_data = new EnchantmentData(enchantment, 1);
      EnchantedBookItem.addEnchantment(enchant_book, enchantment_data);
      inventory.output_inventory.setStackInSlot(0, enchant_book);
    }
  }

  private final Enchantment get_enchantment(){
    // https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments
    final Item item = inventory.working_inventory.getStackInSlot(1).getItem();
    final Random random = new Random();
    if(MaterialsUtil.match(item, MaterialsUtil.getRubies())){
      return RandomUtil.choose(random, ruby_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getTopaz())){
      return RandomUtil.choose(random, topaz_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getCitrine())){
      return RandomUtil.choose(random, citrine_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getEmeralds())){
      return RandomUtil.choose(random, emerald_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getDiamonds())){
      return RandomUtil.choose(random, diamond_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getSapphires())){
      return RandomUtil.choose(random, sapphire_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getAmethysts())){
      return RandomUtil.choose(random, amethyst_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getQuartz())){
      return RandomUtil.choose(random, quartz_enchantments);
    }
    if(item == Init.celestial_gem){
      return RandomUtil.choose(random, celestial_enchantments);
    }
    if(item == Init.void_crystal){
      return RandomUtil.choose(random, void_crystal_enchantments);
    }
    OverpoweredTechnology.log.error("function get_enchantment() in "+TileMagicInfuser.class.getSimpleName()+" returned a null enchantment! With "+StringUtil.getName(item)+" as input.");
    return null;
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
