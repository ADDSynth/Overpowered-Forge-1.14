package addsynth.overpoweredmod.dimension;
/*
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public final class WeirdWorldProvider extends WorldProvider {

  public static final BlockPos spawn = new BlockPos(0,4,0);
  private static final int height = 32;

  @Override
  public final ICapabilityProvider initCapabilities(){
    setAllowedSpawnTypes(false, false);
    setSpawnPoint(spawn);
    setWorldTime(6000); // noon
    return null;
  }

  @Override
  protected final void init(){
    this.biomeProvider = new BiomeProviderSingle(WeirdDimension.weird_biome);
    //this.hasNoSky = true;
  }

  @Override
  public final DimensionType getDimensionType(){
    return WeirdDimension.weird_dimension;
  }

  @Override
  public final IChunkGenerator createChunkGenerator(){
    return new WeirdWorldChunkGenerator(world);
  }

  @Override
  public final boolean isSurfaceWorld(){
    return false;
  }

  @Override
  public final BlockPos getSpawnPoint(){
    return spawn;
  }

  @Override
  public final boolean canRespawnHere(){
    return false;
  }

  @Override
  public final boolean canMineBlock(PlayerEntity player, BlockPos pos){
    return false;
  }

  @Override
  public final int getHeight(){
    return height;
  }

  @Override
  public final int getActualHeight(){
    return height;
  }

  @Override
  public final void updateWeather(){}

  @Override
  public final float getSunBrightnessFactor(float partialTicks){
    return 1.0f;
  }

  @Override
  public final boolean doesXZShowFog(int x, int z){
    return false;
  }
}
*/
