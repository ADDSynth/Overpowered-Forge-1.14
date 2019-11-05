package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.OreBlock;
import addsynth.core.material.blocks.ItemOreBlock;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class OreMaterial extends BaseMaterial {

  public final Block ore;

  /** Vanilla Material */
  public OreMaterial(final String name, final Item item, final Block block, final Block ore){
    super(false, name, item, block);
    this.ore = ore;
  }

  /** Null Ore Material */
  protected OreMaterial(final String name, final Item item, final Block block){
    super(true, name, item, block);
    this.ore = null;
  }

  /** Custom Material */
  public OreMaterial(final String name, final MapColor color, final OreType ore_type, final MiningStrength strength){
    super(true, name, new CoreItem(name), null); // TODO: needs generic block
    this.ore = NewOreBlock(name, ore_type, this.item, strength);
  }


  /** Specific Type Material */
  protected OreMaterial(final String name, final Item item, final Block block, final OreType ore_type, final MiningStrength strength){
    super(true, name, item, block);
    this.ore = ore_type == null ? null : NewOreBlock(name, ore_type, this.item, strength);
  }

  private static final Block NewOreBlock(final String name, final OreType ore_type, final Item item_drop, final MiningStrength strength){
    Block block = null;
    switch(ore_type){
    case BLOCK: block = new OreBlock(name+"_ore", strength); break;
    case ITEM:  block = new ItemOreBlock(name+"_ore", item_drop, strength); break;
    default: break;
    }
    return block;
  }

  @Override
  public void register_oredictionary_name(){
    final IForgeRegistry<Block> block_registry = ForgeRegistries.BLOCKS;
    final IForgeRegistry<Item>  item_registry  = ForgeRegistries.ITEMS;
    if(custom){
      if(this.item != null && item_registry.containsValue(this.item)){
        OreDictionary.registerOre("item"+this.name, this.item);
      }
      if(this.block != null && block_registry.containsValue(this.block)){
        OreDictionary.registerOre("block"+this.name, this.block);
      }
      if(this.ore != null && block_registry.containsValue(this.ore)){
        OreDictionary.registerOre("ore"+this.name, this.ore);
      }
    }
  }

  @Override
  public void debug(){
    ADDSynthCore.log.info("Material: "+name+", Type: Ore Material");
    ADDSynthCore.log.info("item"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("item"+name, false)));
    // FEATURE: maybe we should support dusts, because this does not detect Redstone Dust.
    ADDSynthCore.log.info("block"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("block"+name, false)));
    ADDSynthCore.log.info("ore"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("ore"+name, false)));
  }

}
