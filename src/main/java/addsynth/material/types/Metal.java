package addsynth.material.types;

import addsynth.material.MaterialItem;
import addsynth.material.MiningStrength;
import addsynth.material.blocks.MetalBlock;
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
    super(name);
    this.ingot = null;
    this.nugget = null;
    this.plating = null;
  }

  /** Manufactured Metal */
  protected Metal(final String unlocalized_name, final MaterialColor color){
    super(unlocalized_name, new MaterialItem(unlocalized_name+"_ingot"), new MetalBlock(unlocalized_name+"_block", color));
    this.ingot = this.item;
    this.nugget = new MaterialItem(unlocalized_name+"_nugget");
    this.plating = new MaterialItem(unlocalized_name+"_plate");
  }

  /** Custom Metal */
  public Metal(final String unlocalized_name, final MaterialColor color, final MiningStrength strength){
    super(unlocalized_name, new MaterialItem(unlocalized_name+"_ingot"), new MetalBlock(unlocalized_name+"_block", color),
      strength, 0, 0);
    this.ingot = this.item;
    this.nugget = new MaterialItem(unlocalized_name+"_nugget");
    this.plating = new MaterialItem(unlocalized_name+"_plate");
  }

  /** Vanilla Material */
  public Metal(final String name, final Item ingot, final Block block, final Block ore, final Item nugget){
    super(name, ingot, block, ore);
    this.ingot = this.item;
    this.nugget = nugget != null ? nugget : new MaterialItem(name+"_nugget");
    this.plating = new MaterialItem(name+"_plate");
  }

}
