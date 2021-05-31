package addsynth.material.blocks;

import addsynth.core.util.math.random.RandomUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MiningStrength;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class OreBlock extends Block {

  private final int min_experience;
  private final int max_experience;

  /**
   * Use this constructor if this Ore Block should be mined and smelted in a Furnace. The Furnace gives experience to the player.
   * @param name
   * @param strength
   */
  public OreBlock(final String name, final MiningStrength strength){
    this(name, strength, 0, 0);
  }

  /**
   * Use this constructor if this Ore Block drops an item, such as Coal, Diamond, Lapis, Redstone, or Quartz.
   * @param name
   * @param strength
   * @param min_experience
   * @param max_experience
   */
  public OreBlock(final String name, final MiningStrength strength, int min_experience, int max_experience){
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(strength.ordinal()));
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    ADDSynthMaterials.registry.register_block(this, name, new Item.Properties().group(ADDSynthMaterials.creative_tab));
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }

  @Override
  public final int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch){
    return silktouch == 0 ? RandomUtil.RandomRange(min_experience, max_experience) : 0;
  }

}
