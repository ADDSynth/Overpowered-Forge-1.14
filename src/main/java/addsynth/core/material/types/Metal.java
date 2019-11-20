package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.MetalBlock;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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
    super(unlocalized_name, new CoreItem(unlocalized_name+"_ingot"), new MetalBlock(unlocalized_name+"_block", color));
    this.ingot = this.item;
    this.nugget = new CoreItem(unlocalized_name+"_nugget");
    this.plating = new CoreItem(unlocalized_name+"_plate");
    final String ingot_name = "ingot"+this.name;
    final String nugget_name = "nugget"+this.name;
  }

  /** Custom Metal */
  public Metal(final String unlocalized_name, final MaterialColor color, final MiningStrength strength){
    super(unlocalized_name, new CoreItem(unlocalized_name+"_ingot"), new MetalBlock(unlocalized_name+"_block", color),
      OreType.BLOCK, strength);
    this.ingot = this.item;
    this.nugget = new CoreItem(unlocalized_name+"_nugget");
    this.plating = new CoreItem(unlocalized_name+"_plate");
    final String ingot_name = "ingot"+this.name;
    final String nugget_name = "nugget"+this.name;
  }

  /** Vanilla Material */
  public Metal(final String name, final Item ingot, final Block block, final Block ore, final Item nugget){
    super(name, ingot, block, ore);
    this.ingot = this.item;
    this.nugget = nugget;
    this.plating = new CoreItem(name+"_plate");
  }

}
