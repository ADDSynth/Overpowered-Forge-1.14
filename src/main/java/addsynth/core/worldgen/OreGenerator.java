package addsynth.core.worldgen;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import addsynth.core.config.WorldgenConfig;
import addsynth.core.material.Material;
import addsynth.core.material.types.OreMaterial;
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

  public static final String REQUEST_ORE = "request_ore_to_generate";

  private static final ArrayList<OreMaterial> requested_ores = new ArrayList<>(20);

  public static boolean generate;
  private static boolean done;

  public static final void request_to_generate(final String mod_id, final OreMaterial material){
    if(done == false){
      if(requested_ores.contains(material) == false){
        requested_ores.add(material);
      }
    }
    else{
      ADDSynthCore.log.error("ADDSynthCore is done registering Ores to generate, but the mod '"+mod_id+
        "' has requested to register another ore. You should ONLY be registering Ores to generate using "+
        "Forge's Inter-Mod communications feature!");
    }
  }

  public static final void register(){
    if(done == false){
    
    ADDSynthCore.log.info("Begin registering all requested Ores...");
    done = true;
    
    // This is how it's done now apparently.
    for(Biome biome : ForgeRegistries.BIOMES){
      if(biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND){

        if(requested_ores.contains(Material.RUBY) && WorldgenConfig.generate_ruby.get()){
          generate_gem_ore(biome, Material.RUBY, WorldgenConfig.ruby_spawn_tries.get(),
                           WorldgenConfig.ruby_min_height.get(), WorldgenConfig.ruby_max_height.get());
        }
        if(requested_ores.contains(Material.TOPAZ) && WorldgenConfig.generate_topaz.get()){
          generate_gem_ore(biome, Material.TOPAZ, WorldgenConfig.topaz_spawn_tries.get(),
                           WorldgenConfig.topaz_min_height.get(), WorldgenConfig.topaz_max_height.get());
        }
        if(requested_ores.contains(Material.CITRINE) && WorldgenConfig.generate_citrine.get()){
          generate_gem_ore(biome, Material.CITRINE, WorldgenConfig.citrine_spawn_tries.get(),
                           WorldgenConfig.citrine_min_height.get(), WorldgenConfig.citrine_max_height.get());
        }
        if(requested_ores.contains(Material.EMERALD) && WorldgenConfig.generate_emerald.get()){
          generate_gem_ore(biome, Material.EMERALD, WorldgenConfig.emerald_spawn_tries.get(),
                           WorldgenConfig.emerald_min_height.get(), WorldgenConfig.emerald_max_height.get());
        }
        if(requested_ores.contains(Material.SAPPHIRE) && WorldgenConfig.generate_sapphire.get()){
          generate_gem_ore(biome, Material.SAPPHIRE, WorldgenConfig.sapphire_spawn_tries.get(),
                           WorldgenConfig.sapphire_min_height.get(), WorldgenConfig.sapphire_max_height.get());
        }
        if(requested_ores.contains(Material.AMETHYST) && WorldgenConfig.generate_amethyst.get()){
          generate_gem_ore(biome, Material.AMETHYST, WorldgenConfig.amethyst_spawn_tries.get(),
                           WorldgenConfig.amethyst_min_height.get(), WorldgenConfig.amethyst_max_height.get());
        }
        
        if(requested_ores.contains(Material.TIN) && WorldgenConfig.generate_tin.get()){
          generate_metal_ore(biome, Material.TIN, WorldgenConfig.tin_ore_size.get(), WorldgenConfig.tin_spawn_tries.get(),
                             WorldgenConfig.tin_min_height.get(), WorldgenConfig.tin_max_height.get());
        }
        if(requested_ores.contains(Material.COPPER) && WorldgenConfig.generate_copper.get()){
          generate_metal_ore(biome, Material.COPPER, WorldgenConfig.copper_ore_size.get(), WorldgenConfig.copper_spawn_tries.get(),
                             WorldgenConfig.copper_min_height.get(), WorldgenConfig.copper_max_height.get());
        }
        if(requested_ores.contains(Material.ALUMINUM) && WorldgenConfig.generate_aluminum.get()){
          generate_metal_ore(biome, Material.ALUMINUM, WorldgenConfig.aluminum_ore_size.get(), WorldgenConfig.aluminum_spawn_tries.get(),
                             WorldgenConfig.aluminum_min_height.get(), WorldgenConfig.aluminum_max_height.get());
        }
        if(requested_ores.contains(Material.SILVER) && WorldgenConfig.generate_silver.get()){
          generate_metal_ore(biome, Material.SILVER, WorldgenConfig.silver_ore_size.get(), WorldgenConfig.silver_spawn_tries.get(),
                             WorldgenConfig.silver_min_height.get(), WorldgenConfig.silver_max_height.get());
        }
        if(requested_ores.contains(Material.PLATINUM) && WorldgenConfig.generate_platinum.get()){
          generate_metal_ore(biome, Material.PLATINUM, WorldgenConfig.platinum_ore_size.get(), WorldgenConfig.platinum_spawn_tries.get(),
                             WorldgenConfig.platinum_min_height.get(), WorldgenConfig.platinum_max_height.get());
        }
        if(requested_ores.contains(Material.TITANIUM) && WorldgenConfig.generate_titanium.get()){
          generate_metal_ore(biome, Material.TITANIUM, WorldgenConfig.titanium_ore_size.get(), WorldgenConfig.titanium_spawn_tries.get(),
                             WorldgenConfig.titanium_min_height.get(), WorldgenConfig.titanium_max_height.get());
        }
      }
    }
    
    ADDSynthCore.log.info("Done registering requested Ores.");
    }
  }

  private static final boolean valid_min_max_values(final String name, final int min, final int max){
    final boolean pass = min >= 0 && max >= 0 && min < Constants.world_height && max < Constants.world_height;
    if(pass == false){
      ADDSynthCore.log.error("Invalid Worldgen Min/Max values: Min: "+min+", Max: "+max+", while generating Ores for "+name+".");
    }
    return pass;
  }

  private static final void generate_gem_ore(final Biome biome, final OreMaterial material, final int tries, final int min, final int max){
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

  private static final void generate_metal_ore(final Biome biome, final OreMaterial material, final int size, final int tries, final int min, final int max){
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
