package addsynth.material.worldgen;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.ReplaceBlockFeature;

public final class Features { // UNUSED, here if I ever need it. But I need to register it in the CoreRegisters class.

  public static final Feature<ReplaceBlockConfig> GEM_ORE = create("gem_ore", new ReplaceBlockFeature(ReplaceBlockConfig::deserialize));
  
  private static final <T extends IFeatureConfig, C extends Feature<T>> Feature<T> create(String id, C feature_config){
    final C feature = feature_config;
    feature.setRegistryName(id);
    return feature;
  }

}
