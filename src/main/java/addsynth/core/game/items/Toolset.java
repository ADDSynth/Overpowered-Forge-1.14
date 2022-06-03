package addsynth.core.game.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

public final class Toolset {

  public final SwordItem sword;
  public final ShovelItem shovel;
  public final PickaxeItem pickaxe;
  public final AxeItem axe;
  public final HoeItem hoe;
  
  public final Item[] tools;

  public Toolset(final SwordItem sword, final ShovelItem shovel, final PickaxeItem pickaxe,
                 final AxeItem axe, final HoeItem hoe, final Object material){
    this(sword, shovel, pickaxe, axe, hoe, material, "stickWood");
  }

  public Toolset(final SwordItem sword, final ShovelItem shovel, final PickaxeItem pickaxe,
                 final AxeItem axe, final HoeItem hoe, final Object material, final Object handle){
    this.sword = sword;
    this.shovel = shovel;
    this.pickaxe = pickaxe;
    this.axe = axe;
    this.hoe = hoe;
    tools = new Item[] {sword, shovel, pickaxe, axe, hoe};
  }

}
