package addsynth.overpoweredmod.dimension;
/*
import java.util.Random;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public final class WeirdWorldChunkGenerator implements IChunkGenerator {

  private final World world;
  private final Random random;

  public WeirdWorldChunkGenerator(final World world){
    this.world = world;
    this.random = new Random(world.getSeed());
  }

  @Override
  public Chunk generateChunk(final int chunkX, final int chunkZ){
    ChunkPrimer primer = new ChunkPrimer();
    
    // generate
    int i;
    int j;
    for(j = 0; j < 16; j++){
      for(i = 0; i < 16; i++){
        primer.setBlockState(i, 0, j, Blocks.STONE.getDefaultState());
        primer.setBlockState(i, 1, j, Blocks.STONE.getDefaultState());
        primer.setBlockState(i, 2, j, Blocks.STONE.getDefaultState());
        primer.setBlockState(i, 3, j, Blocks.GRASS.getDefaultState());
      }
    }
    
    Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);
    chunk.generateSkylightMap();
    return chunk;
  }

  @Override
  public void populate(final int x, final int z){
    final BlockPos location = new BlockPos(x*16, 0, z*16);
    world.getBiome(location).decorate(world, random, location);
  }

  @Override
  public boolean generateStructures(Chunk chunkIn, int x, int z){
    return false;
  }

  @Override
  public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos){
    return null;
  }

  @Override
  public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean find_unexplored){
    return null;
  }

  @Override
  public void recreateStructures(Chunk chunkIn, int x, int z){
  }

  @Override
  public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos){
    return false;
  }

}
*/
