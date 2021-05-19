package addsynth.material.worldgen;

import addsynth.core.Constants;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.Material;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.types.OreMaterial;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public final class OreGenerator {

  public static final void register(){
    // This is how it's done now apparently.
    for(Biome biome : ForgeRegistries.BIOMES){
      if(biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND){

        if(WorldgenConfig.generate_ruby.get()){
          generate_single_ore(biome, Material.RUBY, WorldgenConfig.ruby_spawn_tries.get(),
                           WorldgenConfig.ruby_min_height.get(), WorldgenConfig.ruby_max_height.get());
        }
        if(WorldgenConfig.generate_topaz.get()){
          generate_single_ore(biome, Material.TOPAZ, WorldgenConfig.topaz_spawn_tries.get(),
                           WorldgenConfig.topaz_min_height.get(), WorldgenConfig.topaz_max_height.get());
        }
        if(WorldgenConfig.generate_citrine.get()){
          generate_single_ore(biome, Material.CITRINE, WorldgenConfig.citrine_spawn_tries.get(),
                           WorldgenConfig.citrine_min_height.get(), WorldgenConfig.citrine_max_height.get());
        }
        if(WorldgenConfig.generate_emerald.get()){
          generate_single_ore(biome, Material.EMERALD, WorldgenConfig.emerald_spawn_tries.get(),
                           WorldgenConfig.emerald_min_height.get(), WorldgenConfig.emerald_max_height.get());
        }
        if(WorldgenConfig.generate_sapphire.get()){
          generate_single_ore(biome, Material.SAPPHIRE, WorldgenConfig.sapphire_spawn_tries.get(),
                           WorldgenConfig.sapphire_min_height.get(), WorldgenConfig.sapphire_max_height.get());
        }
        if(WorldgenConfig.generate_amethyst.get()){
          generate_single_ore(biome, Material.AMETHYST, WorldgenConfig.amethyst_spawn_tries.get(),
                           WorldgenConfig.amethyst_min_height.get(), WorldgenConfig.amethyst_max_height.get());
        }
        
        if(WorldgenConfig.generate_tin.get()){
          generate_ore(biome, Material.TIN, WorldgenConfig.tin_ore_size.get(), WorldgenConfig.tin_spawn_tries.get(),
                             WorldgenConfig.tin_min_height.get(), WorldgenConfig.tin_max_height.get());
        }
        if(WorldgenConfig.generate_copper.get()){
          generate_ore(biome, Material.COPPER, WorldgenConfig.copper_ore_size.get(), WorldgenConfig.copper_spawn_tries.get(),
                             WorldgenConfig.copper_min_height.get(), WorldgenConfig.copper_max_height.get());
        }
        if(WorldgenConfig.generate_aluminum.get()){
          generate_ore(biome, Material.ALUMINUM, WorldgenConfig.aluminum_ore_size.get(), WorldgenConfig.aluminum_spawn_tries.get(),
                             WorldgenConfig.aluminum_min_height.get(), WorldgenConfig.aluminum_max_height.get());
        }
        if(WorldgenConfig.generate_silver.get()){
          generate_ore(biome, Material.SILVER, WorldgenConfig.silver_ore_size.get(), WorldgenConfig.silver_spawn_tries.get(),
                             WorldgenConfig.silver_min_height.get(), WorldgenConfig.silver_max_height.get());
        }
        if(WorldgenConfig.generate_platinum.get()){
          generate_ore(biome, Material.PLATINUM, WorldgenConfig.platinum_ore_size.get(), WorldgenConfig.platinum_spawn_tries.get(),
                             WorldgenConfig.platinum_min_height.get(), WorldgenConfig.platinum_max_height.get());
        }
        if(WorldgenConfig.generate_titanium.get()){
          generate_ore(biome, Material.TITANIUM, WorldgenConfig.titanium_ore_size.get(), WorldgenConfig.titanium_spawn_tries.get(),
                             WorldgenConfig.titanium_min_height.get(), WorldgenConfig.titanium_max_height.get());
        }
        
        if(WorldgenConfig.generate_silicon.get()){
          generate_ore(biome, Material.SILICON, WorldgenConfig.silicon_ore_size.get(), WorldgenConfig.silicon_spawn_tries.get(),
                             WorldgenConfig.silicon_min_height.get(), WorldgenConfig.silicon_max_height.get());
        }
        
        // Rose Quartz is the most rare. Make sure it is the last one generated
        if(WorldgenConfig.generate_rose_quartz.get()){
          generate_single_ore(biome, Material.ROSE_QUARTZ, WorldgenConfig.rose_quartz_spawn_tries.get(),
                           WorldgenConfig.rose_quartz_min_height.get(), WorldgenConfig.rose_quartz_max_height.get());
        }
      }
    }
  }

  private static final boolean valid_min_max_values(final String name, final int min, final int max){
    final boolean pass = min >= 0 && max >= 0 && min < Constants.world_height && max < Constants.world_height;
    if(pass == false){
      ADDSynthMaterials.log.error("Invalid Worldgen Min/Max values: Min: "+min+", Max: "+max+", while generating Ores for "+name+".");
    }
    return pass;
  }

  private static final void generate_single_ore(final Biome biome, final OreMaterial material, final int tries, final int min, final int max){
    if(valid_min_max_values(material.name, min, max)){
      final int min_depth = Math.min(min, max);
      final int max_depth = Math.max(min, max);
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
        Feature.EMERALD_ORE,
        new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), material.ore.getDefaultState()),
        Placement.COUNT_RANGE, new CountRangeConfig(tries, min_depth, min_depth, max_depth)
      ));
    }
  }

  private static final void generate_ore(final Biome biome, final OreMaterial material, final int size, final int tries, final int min, final int max){
    if(valid_min_max_values(material.name, min, max)){
      final int min_depth = Math.min(min, max);
      final int max_depth = Math.max(min, max);
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
        Feature.ORE,
        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, material.ore.getDefaultState(), size),
        Placement.COUNT_RANGE, new CountRangeConfig(tries, min_depth, min_depth, max_depth)
      ));
    }
  }

}
