package addsynth.overpoweredmod.game.core;

import addsynth.core.game.Compatability;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.items.ArmorMaterial;
import addsynth.core.items.EquipmentType;
import addsynth.core.items.Toolset;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.tools.*;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.EnumHelper;

public final class Tools {

  static {
    Debug.log_setup_info("Begin loading Tools class...");
  }

  public static final ToolMaterial ENERGY = EnumHelper.addToolMaterial("ENERGY", 4, ToolMaterial.DIAMOND.getMaxUses()*3, 12.0f, 4.0f, 0);
  public static final ToolMaterial VOID   = EnumHelper.addToolMaterial("VOID",   4, ToolMaterial.DIAMOND.getMaxUses()*5, 12.0f, 5.0f, 0);
  // MAYBE: Add Unimatter Tools, THESE will have Integer.MAX_VALUE durability.

  public static final Toolset energy_tools = new Toolset( // MAYBE: should I automatically assign tools their names? and just provide the base name? I should also pass the Mod ID to register translation keys as well.
    new EnergySword("energy_sword"),
    new EnergyShovel("energy_shovel"),
    new EnergyPickaxe("energy_pickaxe"),
    new EnergyAxe("energy_axe"),
    new EnergyHoe("energy_hoe"),
    Init.energy_crystal
  );
    
  public static final ScytheTool energy_scythe = new ScytheTool("energy_scythe", ENERGY){
    @Override
    public boolean isEnchantable(final ItemStack stack){
      return false;
    }
  
    @Override
    public Rarity getRarity(final ItemStack stack){
      return Rarity.RARE;
    }
  };

  public static final Toolset void_toolset = new Toolset(
    new NullSword("void_sword"),
    new NullShovel("void_shovel"),
    new NullPickaxe("void_pickaxe"),
    new NullAxe("void_axe"),
    new NullHoe("void_hoe"),
    Init.void_crystal
  );

  public static final Item[][] unidentified_armor = new Item[5][4];

  static {
    for(ArmorMaterial material : ArmorMaterial.values()){
      for(EquipmentType type : EquipmentType.values()){
        unidentified_armor[material.ordinal()][type.ordinal()] = new UnidentifiedItem(material, type);
      }
    }
  }

  static {
    Debug.log_setup_info("Finished loading Tools class.");
  }

}
