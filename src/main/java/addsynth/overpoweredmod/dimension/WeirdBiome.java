package addsynth.overpoweredmod.dimension;
/*
import java.util.Random;
import addsynth.overpoweredmod.config.Values;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public final class WeirdBiome extends Biome {

  private static final BiomeProperties unknown_biome_properties;
  static {
    unknown_biome_properties = new BiomeProperties("unknown");
    unknown_biome_properties.setRainDisabled();
  }
  private static final int spawn_tree_chance = (int)Math.round(1 / Values.unknown_dimension_tree_spawn_chance.get());
  private static final WeirdTree weird_tree = new WeirdTree(false);

  public WeirdBiome() {
    super(unknown_biome_properties);
    setRegistryName("unknown"); // for some reason they added the @SideOnly(CLIENT) attribute in 1.12. so getBiomeName() would crash on servers.
    this.decorator.treesPerChunk = 1;
    this.spawnableMonsterList.clear();
    this.spawnableCreatureList.clear();
    this.spawnableWaterCreatureList.clear();
    this.spawnableCaveCreatureList.clear();
  }

  @Override
  public final void decorate(final World world, final Random random, final BlockPos chunk_position){
    if(random.nextInt(spawn_tree_chance) == 0){
      // world.getHeight() isn't working beause it doesn't think the chunk is loaded?
*/
      /*
      int y;
      BlockPos true_position = position;
      for(y = world.getHeight() - 1; y >= 0; y--){
        true_position = new BlockPos(position.getX(), y, position.getZ());
        if(world.isAirBlock(true_position) == false){
          weird_tree.generate(world, random, true_position.up());
          break;
        }
      }
      */
/*
      final int x = chunk_position.getX() + random.nextInt(16);
      final int z = chunk_position.getZ() + random.nextInt(16);
      final int y = world.getHeight(x, z);
      weird_tree.generate(world, random, new BlockPos(x,y,z));
    }
  }

  @Override
  public WorldGenAbstractTree getRandomTreeFeature(Random rand){
    return weird_tree;
  }

}
*/
