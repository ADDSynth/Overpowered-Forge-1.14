package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.material.MiningStrength;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class OreBlock extends Block {

  public OreBlock(final String name, final MiningStrength strength){
    this(name, null, strength);
  }
  public OreBlock(final String name, final Item item_drop, final MiningStrength strength){
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(strength.ordinal()));
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    OverpoweredMod.registry.register_block(this, name,
      new Item.Properties().group(item_drop == null ? OverpoweredMod.metals_creative_tab : OverpoweredMod.gems_creative_tab));
  }

}
