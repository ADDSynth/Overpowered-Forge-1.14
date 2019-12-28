package addsynth.overpoweredmod.game.core;

import addsynth.core.game.Compatability;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.items.ArmorMaterial;
import addsynth.core.items.EquipmentType;
import addsynth.core.items.Toolset;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.items.tools.*;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class Tools {

  static {
    Debug.log_setup_info("Begin loading Tools class...");
  }

  public static final Toolset energy_tools = new Toolset( // MAYBE: should I automatically assign tools their names? and just provide the base name? I should also pass the Mod ID to register translation keys as well.
    new EnergySword("energy_sword"),
    new EnergyShovel("energy_shovel"),
    new EnergyPickaxe("energy_pickaxe"),
    new EnergyAxe("energy_axe"),
    new EnergyHoe("energy_hoe"),
    Init.energy_crystal
  );
    
  public static final ScytheTool energy_scythe =
  new ScytheTool("energy_scythe", OverpoweredTiers.ENERGY, OverpoweredMod.registry, new Item.Properties().group(CreativeTabs.tools_creative_tab)){
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

  // TODO: in Overpowered version 1.5, add Rings back using the new Baubles API: Curios
  //         https://www.curseforge.com/minecraft/mc-mods/curios
  // available only for Minecraft versions 1.14 and up.

  static {
    Debug.log_setup_info("Finished loading Tools class.");
  }

}
