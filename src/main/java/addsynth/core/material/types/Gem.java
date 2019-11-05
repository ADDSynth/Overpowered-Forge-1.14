package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.GemBlock;
import addsynth.core.util.RecipeUtil;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public final class Gem extends OreMaterial {

  public final Item gem;
  public final Item shard;
  public final IRecipe shards_to_gem_recipe;
  public final IRecipe gem_to_shards_recipe;
  public final IRecipe gems_to_block_recipe; // MAYBE: move to BaseMaterial, but the recipes use the OreDictionary name.
  public final IRecipe block_to_gems_recipe;

  /** Custom Gem Material */
  public Gem(final String unlocalized_name, final MapColor color){
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

  @Override
  public final void register_oredictionary_name(){
    final IForgeRegistry<Block> block_registry = ForgeRegistries.BLOCKS;
    final IForgeRegistry<Item>  item_registry  = ForgeRegistries.ITEMS;
    if(custom){
      if(this.gem != null && item_registry.containsValue(this.gem)){
        OreDictionary.registerOre("gem"+this.name, this.gem);
      }
      if(this.block != null && block_registry.containsValue(this.block)){
        OreDictionary.registerOre("block"+this.name, this.block);
      }
      if(this.ore != null && block_registry.containsValue(this.ore)){
        OreDictionary.registerOre("ore"+this.name, this.ore);
      }
    }
    if(this.shard != null && item_registry.containsValue(this.shard)){
      OreDictionary.registerOre("shard"+this.name, this.shard);
    }
  }

  @Override
  public void debug(){
    ADDSynthCore.log.info("Material: "+name+", Type: Gem");
    ADDSynthCore.log.info("gem"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("gem"+name, false)));
    ADDSynthCore.log.info("shard"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("shard"+name, false)));
    ADDSynthCore.log.info("block"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("block"+name, false)));
    ADDSynthCore.log.info("ore"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("ore"+name, false)));
  }

}
