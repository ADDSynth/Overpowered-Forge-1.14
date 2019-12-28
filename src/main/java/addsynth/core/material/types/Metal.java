package addsynth.core.material.types;

import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.blocks.MetalBlock;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public class Metal extends OreMaterial {

  public final Item ingot;
  public final Item nugget;
  public final Item plating;
  // MAYBE dusts?

  /** Placeholder Metal */
  public Metal(final String name){
    super(name, null, null);
    this.ingot = null;
    this.nugget = null;
    this.plating = null;
  }

  /** Manufactured Metal */
  protected Metal(final String unlocalized_name, final MaterialColor color){
    super(unlocalized_name, new CoreItem(unlocalized_name+"_ingot", false), new MetalBlock(unlocalized_name+"_block", color));
    this.ingot = this.item;
    this.nugget = new CoreItem(unlocalized_name+"_nugget", false);
    this.plating = new CoreItem(unlocalized_name+"_plate", false);
  }

  /** Custom Metal */
  public Metal(final String unlocalized_name, final MaterialColor color, final MiningStrength strength){
    super(unlocalized_name, new CoreItem(unlocalized_name+"_ingot", false), new MetalBlock(unlocalized_name+"_block", color),
      strength, CreativeTabs.metals_creative_tab);
    this.ingot = this.item;
    this.nugget = new CoreItem(unlocalized_name+"_nugget", false);
    this.plating = new CoreItem(unlocalized_name+"_plate", false);
  }

  /** Vanilla Material */
  public Metal(final String name, final Item ingot, final Block block, final Block ore, final Item nugget){
    super(name, ingot, block, ore);
    this.ingot = this.item;
    this.nugget = nugget;
    this.plating = new CoreItem(name+"_plate", false);
  }

}
