package addsynth.core.worldgen;

import java.util.ArrayList;
import java.util.Random;
import addsynth.core.ADDSynthCore;
import addsynth.core.config.WorldgenConfig;
import addsynth.core.material.Material;
import addsynth.core.material.types.OreMaterial;
import addsynth.core.util.MathUtility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class OreGenerator {

  private static final ArrayList<Block> requested_ores = new ArrayList<>(30);

  private static boolean generate_ruby;
  private static boolean generate_topaz;
  private static boolean generate_citrine;
  private static boolean generate_emerald;
  private static boolean generate_sapphire;
  private static boolean generate_amethyst;
  private static boolean generate_tin;
  private static boolean generate_copper;
  private static boolean generate_aluminum;
  private static boolean generate_silver;
  private static boolean generate_platinum;
  private static boolean generate_titanium;

  private static final WorldGenMinable tin_generator      = new WorldGenMinable(Material.TIN.ore.getDefaultState(),      WorldgenConfig.tin_ore_size);
  private static final WorldGenMinable copper_generator   = new WorldGenMinable(Material.COPPER.ore.getDefaultState(),   WorldgenConfig.copper_ore_size);
  private static final WorldGenMinable aluminum_generator = new WorldGenMinable(Material.ALUMINUM.ore.getDefaultState(), WorldgenConfig.aluminum_ore_size);
  private static final WorldGenMinable silver_generator   = new WorldGenMinable(Material.SILVER.ore.getDefaultState(),   WorldgenConfig.silver_ore_size);
  private static final WorldGenMinable platinum_generator = new WorldGenMinable(Material.PLATINUM.ore.getDefaultState(), WorldgenConfig.platinum_ore_size);
  private static final WorldGenMinable titanium_generator = new WorldGenMinable(Material.TITANIUM.ore.getDefaultState(), WorldgenConfig.titanium_ore_size);

  public static final void request_to_generate(final OreMaterial material){
    Block ore = material.ore;
    if(ore == null){
      ADDSynthCore.log.error("Material "+material.name+" does not have an Ore Block!");
      return;
    }
    // Add Material.
    if(requested_ores.contains(ore) == false){
      requested_ores.add(ore);
    }
  }

  // TEST: Maybe this should only execute on the server-side.
  public static final void initialize(){
    if(Loader.instance().getLoaderState() != LoaderState.INITIALIZATION){
      throw new Error("OreGenerators.initialize() should be called from within the Initialization Event.");
    }
    
    generate_tin      = WorldgenConfig.generate_tin      && requested_ores.contains(Material.TIN.ore);
    generate_aluminum = WorldgenConfig.generate_aluminum && requested_ores.contains(Material.ALUMINUM.ore);
    generate_copper   = WorldgenConfig.generate_copper   && requested_ores.contains(Material.COPPER.ore);
    generate_ruby     = WorldgenConfig.generate_ruby     && requested_ores.contains(Material.RUBY.ore);
    generate_topaz    = WorldgenConfig.generate_topaz    && requested_ores.contains(Material.TOPAZ.ore);
    generate_citrine  = WorldgenConfig.generate_citrine  && requested_ores.contains(Material.CITRINE.ore);
    generate_emerald  = WorldgenConfig.generate_emerald  && requested_ores.contains(Material.EMERALD.ore);
    generate_sapphire = WorldgenConfig.generate_sapphire && requested_ores.contains(Material.SAPPHIRE.ore);
    generate_amethyst = WorldgenConfig.generate_amethyst && requested_ores.contains(Material.AMETHYST.ore);
    generate_silver   = WorldgenConfig.generate_silver   && requested_ores.contains(Material.SILVER.ore);
    generate_platinum = WorldgenConfig.generate_platinum && requested_ores.contains(Material.PLATINUM.ore);
    generate_titanium = WorldgenConfig.generate_titanium && requested_ores.contains(Material.TITANIUM.ore);
    
    GameRegistry.registerWorldGenerator(new MasterOreGenerator(),0);
  }


/** All Ores have to be generated in the same generator class that implements IWolrdGenerator.
 *  If each ore had their own generator class, and registered to the generator list...
 *  Because for each IWorldGenerator in the list, Minecraft will reset the Random Number generator to the
 *  same chunk seed each time. Which means, for each generator, the first random coordinates picked will
 *  be the same one every time. So only do all ore generating in one generator.
 *  
 *  @see net.minecraftforge.fml.common.registry.GameRegistry#generateWorld(int, int, World, IChunkGenerator, IChunkProvider)
 */
  private static final class MasterOreGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
      if(world.provider.getDimension() == 0){
      
        int i;
        BlockPos position;
        
        // common metals
        if(generate_tin){
          for(i = 0; i < WorldgenConfig.tin_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.tin_min_height, WorldgenConfig.tin_max_height);
            tin_generator.generate(world, random, position);
          }
        }
        if(generate_copper){
          for(i = 0; i < WorldgenConfig.copper_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.copper_min_height, WorldgenConfig.copper_max_height);
            copper_generator.generate(world, random, position);
          }
        }
        if(generate_aluminum){
          for(i = 0; i < WorldgenConfig.aluminum_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.aluminum_min_height, WorldgenConfig.aluminum_max_height);
            aluminum_generator.generate(world, random, position);
          }
        }

        // gems
        if(generate_ruby){
          for(i = 0; i < WorldgenConfig.ruby_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.ruby_min_height, WorldgenConfig.ruby_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){ // returns Air block if position is outside build height.
              world.setBlockState(position,Material.RUBY.ore.getDefaultState(),2); // will simply fail if position is outside build height.
            }
          }
        }
        if(generate_topaz){
          for(i = 0; i < WorldgenConfig.topaz_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.topaz_min_height, WorldgenConfig.topaz_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){
              world.setBlockState(position,Material.TOPAZ.ore.getDefaultState(),2);
            }
          }
        }
        if(generate_citrine){
          for(i = 0; i < WorldgenConfig.citrine_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.citrine_min_height, WorldgenConfig.citrine_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){
              world.setBlockState(position,Material.CITRINE.ore.getDefaultState(),2);
            }
          }
        }
        if(generate_emerald){
          for(i = 0; i < WorldgenConfig.emerald_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.emerald_min_height, WorldgenConfig.emerald_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){
              world.setBlockState(position,Material.EMERALD.ore.getDefaultState(),2);
            }
          }
        }
        if(generate_sapphire){
          for(i = 0; i < WorldgenConfig.sapphire_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.sapphire_min_height, WorldgenConfig.sapphire_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){
              world.setBlockState(position,Material.SAPPHIRE.ore.getDefaultState(),2);
            }
          }
        }
        if(generate_amethyst){
          for(i = 0; i < WorldgenConfig.amethyst_spawn_tries; i++){
            position = MathUtility.get_custom_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.amethyst_min_height, WorldgenConfig.amethyst_max_height);
            if(world.getBlockState(position).getBlock() == Blocks.STONE){
              world.setBlockState(position,Material.AMETHYST.ore.getDefaultState(),2);
            }
          }
        }

        // semi rare metals
        if(generate_silver){
          for(i = 0; i < WorldgenConfig.silver_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.silver_min_height, WorldgenConfig.silver_max_height);
            silver_generator.generate(world, random, position);
          }
        }
        
        // rare metals
        if(generate_platinum){
          for(i = 0; i < WorldgenConfig.platinum_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.platinum_min_height, WorldgenConfig.platinum_max_height);
            platinum_generator.generate(world, random, position);
          }
        }
        if(generate_titanium){
          for(i = 0; i < WorldgenConfig.titanium_spawn_tries; i++){
            position = MathUtility.get_vanilla_worldgen_position(random, chunkX, chunkZ,
              WorldgenConfig.titanium_min_height, WorldgenConfig.titanium_max_height);
            titanium_generator.generate(world, random, position);
          }
        }
        
      } // end dimension id if statement
    } // end generate function
  } // end MasterGenerator class


}
