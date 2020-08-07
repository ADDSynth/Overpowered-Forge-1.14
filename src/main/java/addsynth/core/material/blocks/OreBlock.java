package addsynth.core.material.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.material.MiningStrength;
import addsynth.core.util.math.MathUtility;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class OreBlock extends Block {

  private final int min_experience;
  private final int max_experience;

  /**
   * Use this constructor if this Ore Block should be mined and smelted in a Furnace. The Furnace givex experience to the player.
   * @param name
   * @param strength
   * @param group
   */
  public OreBlock(final String name, final MiningStrength strength, final ItemGroup group){
    this(name, strength, group, 0, 0);
  }

  /**
   * Use this constructor if this Ore Block drops an item, such as Coal, Diamond, Lapis, Redstone, or Quartz.
   * @param name
   * @param strength
   * @param group
   * @param min_experience
   * @param max_experience
   */
  public OreBlock(final String name, final MiningStrength strength, final ItemGroup group, int min_experience, int max_experience){
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(strength.ordinal()));
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(group));
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }

  @Override
  public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch){
    return silktouch == 0 ? MathUtility.RandomRange(min_experience, max_experience) : 0;
  }

}
