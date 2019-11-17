package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class BaseMaterial extends AbstractMaterial {

  public final Item item;
  public final Block block;
  public final BlockItem block_item;

  /** Call this, only if you're unsure of the properties of the material.
   *  This acts as a placeholder until you can properly define all of the properties.
   *  In the meantime, this material will still be checked by name in certain instances.
   * @param name
   */
  public BaseMaterial(final String name){
    super(true, name);
    this.item = null;
    this.block = null;
    this.block_item = null;
  }

  public BaseMaterial(final boolean custom, final String name, final Item item, final Block block){
    super(custom, name);
    this.item = item;
    this.block = block;
    this.block_item = block == null ? null : ADDSynthCore.registry.getItemBlock(this.block); // FUTURE: Registerd to Overpowered Mod for now, but Common materials will probably be moved to ADDSynthCore in the future, and requested in the PreInit event, just like the WorldGenerators are.
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
    }
  }

  @Override
  public void debug(){
    ADDSynthCore.log.info("Material: "+name+", Type: Basic Material");
    ADDSynthCore.log.info("item"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("item"+name, false)));
    ADDSynthCore.log.info("block"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("block"+name, false)));
  }

}
