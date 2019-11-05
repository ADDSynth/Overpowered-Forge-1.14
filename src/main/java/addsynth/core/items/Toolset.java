package addsynth.core.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public final class Toolset {

  public final ItemSword sword;
  public final ItemSpade shovel;
  public final ItemPickaxe pickaxe;
  public final ItemAxe axe;
  public final ItemHoe hoe;
  
  public final Item[] tools;

  public Toolset(final ItemSword sword, final ItemSpade shovel, final ItemPickaxe pickaxe,
                 final ItemAxe axe, final ItemHoe hoe, final Object material){
    this(sword, shovel, pickaxe, axe, hoe, material, "stickWood");
  }

  public Toolset(final ItemSword sword, final ItemSpade shovel, final ItemPickaxe pickaxe,
                 final ItemAxe axe, final ItemHoe hoe, final Object material, final Object handle){
    this.sword = sword;
    this.shovel = shovel;
    this.pickaxe = pickaxe;
    this.axe = axe;
    this.hoe = hoe;
    tools = new Item[] {sword, shovel, pickaxe, axe, hoe};
  }

}
