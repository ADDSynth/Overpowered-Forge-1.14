package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.MetalBlock;
import addsynth.core.util.RecipeUtil;
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
  public final IRecipe nuggets_to_ingot_recipe;
  public final IRecipe ingot_to_nuggets_recipe;
  public final IRecipe ingots_to_block_recipe; // MAYBE: move to BaseMaterial, but the recipes use the OreDictionary name.
  public final IRecipe block_to_ingots_recipe;

  /** Placeholder Metal */
  public Metal(final String name){
    super(name, null, null);
    this.ingot = null;
    this.nugget = null;
    this.plating = null;
    nuggets_to_ingot_recipe = null;
    ingot_to_nuggets_recipe = null;
    ingots_to_block_recipe = null;
    block_to_ingots_recipe = null;
  }

  /** Manufactured Metal */
  protected Metal(final String unlocalized_name, final MaterialColor color){
    super(unlocalized_name, new CoreItem(unlocalized_name+"_ingot"), new MetalBlock(unlocalized_name+"_block", color));
    this.ingot = this.item;
    this.nugget = new CoreItem(unlocalized_name+"_nugget");
    this.plating = new CoreItem(unlocalized_name+"_plate");
    final String ingot_name = "ingot"+this.name;
    final String nugget_name = "nugget"+this.name;
    nuggets_to_ingot_recipe = RecipeUtil.create("Nuggets to Ingot", new ItemStack(this.ingot, 1), nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name);
    ingot_to_nuggets_recipe = RecipeUtil.create("Nuggets", new ItemStack(this.nugget,9), ingot_name);
    ingots_to_block_recipe = RecipeUtil.create("Metal Blocks", new ItemStack(block_item,1), ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name);
    block_to_ingots_recipe = RecipeUtil.create("Ingots to Blocks", new ItemStack(this.ingot,9), "block"+this.name);
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
    nuggets_to_ingot_recipe = RecipeUtil.create("Nuggets to Ingot", new ItemStack(this.ingot, 1), nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name, nugget_name);
    ingot_to_nuggets_recipe = RecipeUtil.create("Nuggets", new ItemStack(this.nugget,9), ingot_name);
    ingots_to_block_recipe = RecipeUtil.create("Metal Blocks", new ItemStack(block_item,1), ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name, ingot_name);
    block_to_ingots_recipe = RecipeUtil.create("Ingots to Blocks", new ItemStack(this.ingot,9), "block"+this.name);
  }

  /** Vanilla Material */
  public Metal(final String name, final Item ingot, final Block block, final Block ore, final Item nugget){
    super(name, ingot, block, ore);
    this.ingot = this.item;
    this.nugget = nugget;
    this.plating = new CoreItem(name+"_plate");
    nuggets_to_ingot_recipe = null;
    ingot_to_nuggets_recipe = null;
    ingots_to_block_recipe = null;
    block_to_ingots_recipe = null;
  }

}
