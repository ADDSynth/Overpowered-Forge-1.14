package addsynth.overpoweredmod.dimension;

import addsynth.overpoweredmod.config.Config;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public final class WeirdDimension {

  public static final int id = Config.unknown_dimension_id;
  // You can use DimensionManager.getNextFreeDimId() to automatically register with a non-conflicting id.
  public static final DimensionType weird_dimension =
    DimensionType.register("weird", "_weird", id, WeirdWorldProvider.class, false);
  /** only used once in WeirdWorldProvider and registered in Registries. */
  public static final WeirdBiome weird_biome = new WeirdBiome();

  public static final void register(){
    DimensionManager.registerDimension(id, weird_dimension);
  }

}
