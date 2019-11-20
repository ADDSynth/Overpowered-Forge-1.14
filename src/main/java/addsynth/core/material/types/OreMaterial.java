package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.material.MiningStrength;
import addsynth.core.material.OreType;
import addsynth.core.material.blocks.OreBlock;
import addsynth.core.material.blocks.ItemOreBlock;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
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
  public OreMaterial(final String name, final MaterialColor color, final OreType ore_type, final MiningStrength strength){
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

}
