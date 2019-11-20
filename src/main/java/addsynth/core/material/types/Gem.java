package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.GemBlock;
import addsynth.core.util.RecipeUtil;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public final class Gem extends OreMaterial {

  public final Item gem;
  public final Item shard;
  public final IRecipe shards_to_gem_recipe;
  public final IRecipe gem_to_shards_recipe;
  public final IRecipe gems_to_block_recipe; // MAYBE: move to BaseMaterial, but the recipes use the OreDictionary name.
  public final IRecipe block_to_gems_recipe;

  /** Custom Gem Material */
  public Gem(final String unlocalized_name, final MaterialColor color){
    super(unlocalized_name,
      new CoreItem(unlocalized_name),
      new GemBlock(unlocalized_name+"_block", color),
      OreType.ITEM, MiningStrength.IRON);
    this.gem = this.item;
    this.shard = new CoreItem(unlocalized_name+"_shard");
    final String gem_name = "gem"+this.name;
    final String shard_name = "shard"+this.name;
    shards_to_gem_recipe = RecipeUtil.create("Shards to Gem", new ItemStack(gem, 1), shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name);
    gem_to_shards_recipe = RecipeUtil.create("Gem Shards", new ItemStack(shard,9), gem_name);
    gems_to_block_recipe = RecipeUtil.create("Gem Blocks", new ItemStack(block_item,1), gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name);
    block_to_gems_recipe = RecipeUtil.create("Block to Gems", new ItemStack(gem, 9), "block"+this.name);
  }

  /** Vanilla Material */
  public Gem(final String unlocalized_name, final Item gem, final Block block, final Block ore){
    super(unlocalized_name, gem, block, ore);
    this.gem = this.item;
    this.shard = new CoreItem(unlocalized_name+"_shard");
    final String gem_name = "gem"+this.name;
    final String shard_name = "shard"+this.name;
    shards_to_gem_recipe = RecipeUtil.create("Shards to Gem", new ItemStack(gem, 1), shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name, shard_name);
    gem_to_shards_recipe = RecipeUtil.create("Gem Shards", new ItemStack(shard,9), gem_name);
    gems_to_block_recipe = RecipeUtil.create("Gem Blocks", new ItemStack(block_item,1), gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name, gem_name);
    block_to_gems_recipe = RecipeUtil.create("Block to Gems", new ItemStack(gem, 9), "block"+this.name);
  }

}
