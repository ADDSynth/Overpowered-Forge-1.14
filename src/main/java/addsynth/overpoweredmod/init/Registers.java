package addsynth.overpoweredmod.init;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.Compatability;
import addsynth.core.material.types.Gem;
import addsynth.core.material.types.Metal;
import addsynth.energy.gameplay.tiles.*; // FUTURE: Maybe these will soon be registered in an ADDSynthEnergy mod.
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.game.recipes.*;
import addsynth.overpoweredmod.items.BlackHoleItem;
import addsynth.overpoweredmod.tiles.TileDataCable;
import addsynth.overpoweredmod.tiles.machines.automatic.*;
import addsynth.overpoweredmod.tiles.machines.energy.*;
import addsynth.overpoweredmod.tiles.machines.fusion.*;
import addsynth.overpoweredmod.tiles.machines.laser.*;
import addsynth.overpoweredmod.tiles.machines.portal.*;
import addsynth.overpoweredmod.tiles.technical.*;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/** The annotation is identified at compile time, Forge finds this and runs this
 *  before running PreInit().
 */
@Mod.EventBusSubscriber(modid = OverpoweredMod.MOD_ID)
public final class Registers {

  static {
    Debug.log_setup_info("Registers class was loaded.");
  }

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    OverpoweredMod.log.info("Begin Block Registration Event...");
    
    if(Setup.config_loaded == false){
      Setup.init_config();
    }
    
    final IForgeRegistry<Block> game = event.getRegistry();
    
    for(Gem gem : Gems.index){
      if(gem.custom){
        game.register(gem.block);
        game.register(gem.ore);
      }
    }
    if(Features.light_block){ game.register(Init.light_block); }
    if(Features.null_block){ game.register(Init.null_block); }
    
    if(Features.iron_frame_block){ game.register(Init.iron_frame_block); }
    if(Features.black_hole){ game.register(Init.black_hole); }
    
    if(Features.trophies){
      if(Features.bronze_trophy){ game.register(Trophy.bronze); }
      if(Features.silver_trophy){ game.register(Trophy.silver); }
      if(Features.gold_trophy){ game.register(Trophy.gold); }
      if(Features.platinum_trophy){ game.register(Trophy.platinum); }
    }
    
    game.register(Wires.wire);
    game.register(Wires.data_cable);
    game.register(Machines.generator);
    if(Features.energy_storage_container){ game.register(Machines.energy_storage); }
    if(Features.universal_energy_interface){ game.register(Machines.universal_energy_machine); }
    if(Features.compressor){ game.register(Machines.compressor); }
    if(Features.electric_furnace){ game.register(Machines.electric_furnace); }
    if(Features.gem_converter){ game.register(Machines.gem_converter); }
    game.register(Machines.inverter);
    if(Features.magic_infuser){ game.register(Machines.magic_infuser); }
    if(Features.identifier){ game.register(Machines.identifier); }
    if(Features.portal){
      game.register(Machines.portal_control_panel);
      game.register(Machines.portal_frame);
      game.register(Portal.portal);
      game.register(Portal.unknown_wood);
      game.register(Portal.unknown_leaves);
    }
    if(Features.crystal_matter_generator){ game.register(Machines.crystal_matter_generator); }
    if(Features.advanced_ore_refinery){ game.register(Machines.advanced_ore_refinery); }
    if(Features.lasers){
      game.register(Machines.laser_housing);
      // MAYBE: everywhere I check for each laser is enabled, I should've had the config change an enabled boolean variable in the Laser class!
      if(Features.white_laser){   game.register(Laser.WHITE.cannon);   game.register(Laser.WHITE.beam);   }
      if(Features.red_laser){     game.register(Laser.RED.cannon);     game.register(Laser.RED.beam);     }
      if(Features.orange_laser){  game.register(Laser.ORANGE.cannon);  game.register(Laser.ORANGE.beam);  }
      if(Features.yellow_laser){  game.register(Laser.YELLOW.cannon);  game.register(Laser.YELLOW.beam);  }
      if(Features.green_laser){   game.register(Laser.GREEN.cannon);   game.register(Laser.GREEN.beam);   }
      if(Features.cyan_laser){    game.register(Laser.CYAN.cannon);    game.register(Laser.CYAN.beam);    }
      if(Features.blue_laser){    game.register(Laser.BLUE.cannon);    game.register(Laser.BLUE.beam);    }
      if(Features.magenta_laser){ game.register(Laser.MAGENTA.cannon); game.register(Laser.MAGENTA.beam); }
    }
    if(Features.fusion_container){
      game.register(Machines.fusion_converter);
      game.register(Machines.laser_scanning_unit);
      game.register(Machines.singularity_container);
      game.register(Machines.fusion_laser);
      game.register(Machines.fusion_control_laser_beam);
    }
    for(Metal metal : Metals.values){
      if(metal.custom){
        game.register(metal.block);
        if(metal.ore != null){ game.register(metal.ore); }
      }
    }
    
    OverpoweredMod.log.info("Finished Block Registration Event.");
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    OverpoweredMod.log.info("Begin Item Registration Event...");
    
    final IForgeRegistry<Item> game = event.getRegistry();

    if(Features.crystal_matter_generator){
      for(Gem gem : Gems.index){ game.register(gem.shard); }
    }
    for(Gem gem : Gems.index){ if(gem.custom){ game.register(gem.gem); } }
    for(Gem gem : Gems.index){ if(gem.custom){ game.register(gem.block_item); } }
    for(Gem gem : Gems.index){ if(gem.custom){ game.register(ADDSynthCore.registry.getItemBlock(gem.ore)); } }
    
    game.register(Init.energy_crystal_shards);
    game.register(Init.energy_crystal);
    if(Features.light_block){ game.register(OverpoweredMod.registry.getItemBlock(Init.light_block)); }
    game.register(Init.void_crystal);
    if(Features.null_block){ game.register(OverpoweredMod.registry.getItemBlock(Init.null_block)); }
    
    game.register(ModItems.power_core);
    game.register(ModItems.advanced_power_core);
    game.register(ModItems.energized_power_core);
    game.register(ModItems.nullified_power_core);
    game.register(ModItems.energy_grid);
    game.register(ModItems.vacuum_container);
    game.register(ModItems.beam_emitter);
    for(Lens lens : Lens.values()){
      game.register(lens.lens);
    }
    game.register(ModItems.unknown_technology);
    game.register(ModItems.fusion_core);
    if(Features.dimensional_anchor){ game.register(ModItems.dimensional_anchor); }
    
    if(Features.trophies){
      game.register(Trophy.trophy_base);
      if(Features.bronze_trophy){ game.register(Trophy.BRONZE.item_block); }
      if(Features.silver_trophy){ game.register(Trophy.SILVER.item_block); }
      if(Features.gold_trophy){ game.register(Trophy.GOLD.item_block); }
      if(Features.platinum_trophy){ game.register(Trophy.PLATINUM.item_block); }
    }
    
    game.register(OverpoweredMod.registry.getItemBlock(Wires.wire));
    game.register(OverpoweredMod.registry.getItemBlock(Wires.data_cable));
    game.register(OverpoweredMod.registry.getItemBlock(Machines.generator));
    if(Features.energy_storage_container){ game.register(OverpoweredMod.registry.getItemBlock(Machines.energy_storage)); }
    if(Features.universal_energy_interface){ game.register(OverpoweredMod.registry.getItemBlock(Machines.universal_energy_machine)); }
    if(Features.compressor){       game.register(OverpoweredMod.registry.getItemBlock(Machines.compressor)); }
    if(Features.electric_furnace){ game.register(OverpoweredMod.registry.getItemBlock(Machines.electric_furnace)); }
    if(Features.gem_converter){    game.register(OverpoweredMod.registry.getItemBlock(Machines.gem_converter)); }
    game.register(OverpoweredMod.registry.getItemBlock(Machines.inverter));
    if(Features.magic_infuser){    game.register(OverpoweredMod.registry.getItemBlock(Machines.magic_infuser)); }
    if(Features.identifier){       game.register(OverpoweredMod.registry.getItemBlock(Machines.identifier)); }
    if(Features.portal){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.portal_control_panel));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.portal_frame));
      // MAYBE: register Item versions of the unknown / weird tree  (but item order is specific. don't register them here.)
    }
    if(Features.crystal_matter_generator){ game.register(OverpoweredMod.registry.getItemBlock(Machines.crystal_matter_generator)); }
    if(Features.advanced_ore_refinery){ game.register(OverpoweredMod.registry.getItemBlock(Machines.advanced_ore_refinery)); }
    
    if(Features.lasers){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.laser_housing));
      if(Features.white_laser){   game.register(OverpoweredMod.registry.getItemBlock(Laser.WHITE.cannon));   }
      if(Features.red_laser){     game.register(OverpoweredMod.registry.getItemBlock(Laser.RED.cannon));     }
      if(Features.orange_laser){  game.register(OverpoweredMod.registry.getItemBlock(Laser.ORANGE.cannon));  }
      if(Features.yellow_laser){  game.register(OverpoweredMod.registry.getItemBlock(Laser.YELLOW.cannon));  }
      if(Features.green_laser){   game.register(OverpoweredMod.registry.getItemBlock(Laser.GREEN.cannon));   }
      if(Features.cyan_laser){    game.register(OverpoweredMod.registry.getItemBlock(Laser.CYAN.cannon));    }
      if(Features.blue_laser){    game.register(OverpoweredMod.registry.getItemBlock(Laser.BLUE.cannon));    }
      if(Features.magenta_laser){ game.register(OverpoweredMod.registry.getItemBlock(Laser.MAGENTA.cannon)); }
    }
    
    if(Features.fusion_container){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_converter));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.laser_scanning_unit));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.singularity_container));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_laser));
    }
    
    if(Features.iron_frame_block){ game.register(OverpoweredMod.registry.getItemBlock(Init.iron_frame_block)); }
    if(Features.black_hole){       game.register(OverpoweredMod.registry.getItemBlock(Init.black_hole, BlackHoleItem.class)); }
    
    if(Features.energy_tools){
      for(Item tool : Tools.energy_tools.tools){ game.register(tool); }
      if(addsynth.core.config.Features.scythes){
        game.register(Tools.energy_scythe);
      }
    }
    if(Features.void_tools){
      for(Item tool : Tools.void_toolset.tools){ game.register(tool); }
    }
    if(Features.identifier){
      for(Item[] armor_set : Tools.unidentified_armor){
        for(Item armor : armor_set){ game.register(armor); }
      }
      if(Compatability.BAUBLES.loaded){
        for(Item ring : Tools.ring){        game.register(ring); }
        for(Item ring : Tools.magic_ring){  game.register(ring); }
      }
    }
    
    for(Metal metal : Metals.values){ if(metal.custom){ game.register(metal.ingot); } }
    for(Metal metal : Metals.values){ if(metal.custom){ game.register(ADDSynthCore.registry.getItemBlock(metal.block)); } }
    for(Metal metal : Metals.values){ if(metal.custom){ if(metal.ore != null){ game.register(ADDSynthCore.registry.getItemBlock(metal.ore)); } } }
    if(Features.compressor){
      for(Metal metal : Metals.values){ game.register(metal.plating); }
    }
    
    game.register(Portal.portal_image);

    Setup.items_registered = true;
    OverpoweredMod.log.info("Finished Item Registration Event.");
  }

  /**
   * <p>
   * Strings that are used in the ResourceLocation() function are used to find a file on disk, and all files must be
   * in lowercase snake_case, this will be enforced in Minecraft 1.11.
   * <p>
   * http://mcforge.readthedocs.io/en/latest/models/introduction/
   * <p>
   * This is because the 1.11 update has updated Resource Packs format to version 3, which requires all files to be in
   * lowercase. See here: https://minecraft.gamepedia.com/1.11#General_2
   */
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static final void registerModels(final ModelRegistryEvent event){
    Debug.log_setup_info("Begin Model Registry Event...");
    OverpoweredMod.registry.register_inventory_item_models();
    Debug.log_setup_info("Finished Model Registry Event.");
  }





  /**
   * Forge doesn't have a Registry Event for TileEntities.
   */
  public static final void registerTileEntities(){ // TEST: Maybe don't register TileEntities if they aren't enabled in the config.
    Debug.log_setup_info("Begin registering Tile Entities...");
    /*
      https://github.com/MinecraftForge/MinecraftForge/pull/4681#issuecomment-405115908
      NOTE: If anyone needs an example of how to fix the warning caused by this change without breaking old saved games,
      I (someone else) just updated all of McJty's mods to use a DataFixer to do so.
    */
    GameRegistry.registerTileEntity(TileEnergyGenerator.class,        new ResourceLocation(OverpoweredMod.MOD_ID,"tile_generator"));
    GameRegistry.registerTileEntity(TileEnergyWire.class,             new ResourceLocation(OverpoweredMod.MOD_ID,"tile_energy_wire"));
    GameRegistry.registerTileEntity(TileEnergyStorage.class,          new ResourceLocation(OverpoweredMod.MOD_ID,"tile_energy_storage"));
    GameRegistry.registerTileEntity(TileUniversalEnergyTransfer.class,new ResourceLocation(OverpoweredMod.MOD_ID,"tile_universal_energy_interface"));
    GameRegistry.registerTileEntity(TileCompressor.class,             new ResourceLocation(OverpoweredMod.MOD_ID,"tile_compressor"));
    GameRegistry.registerTileEntity(TileElectricFurnace.class,        new ResourceLocation(OverpoweredMod.MOD_ID,"tile_electric_furnace"));
    GameRegistry.registerTileEntity(TileGemConverter.class,           new ResourceLocation(OverpoweredMod.MOD_ID,"tile_gem_converter"));
    GameRegistry.registerTileEntity(TileInverter.class,               new ResourceLocation(OverpoweredMod.MOD_ID,"tile_inverter"));
    GameRegistry.registerTileEntity(TileMagicUnlocker.class,          new ResourceLocation(OverpoweredMod.MOD_ID,"tile_magic_infuser"));
    GameRegistry.registerTileEntity(TileIdentifier.class,             new ResourceLocation(OverpoweredMod.MOD_ID,"tile_identifier"));
    GameRegistry.registerTileEntity(TileLaserHousing.class,           new ResourceLocation(OverpoweredMod.MOD_ID,"tile_laser_housing"));
    GameRegistry.registerTileEntity(TileLaser.class,                  new ResourceLocation(OverpoweredMod.MOD_ID,"tile_laser_cannon"));
    GameRegistry.registerTileEntity(TileLaserBeam.class,              new ResourceLocation(OverpoweredMod.MOD_ID,"tile_laser_beam"));
    GameRegistry.registerTileEntity(TileDataCable.class,              new ResourceLocation(OverpoweredMod.MOD_ID,"tile_data_cable"));
    GameRegistry.registerTileEntity(TilePortalControlPanel.class,     new ResourceLocation(OverpoweredMod.MOD_ID,"tile_portal_control_panel"));
    GameRegistry.registerTileEntity(TilePortalFrame.class,            new ResourceLocation(OverpoweredMod.MOD_ID,"tile_portal_frame"));
    GameRegistry.registerTileEntity(TilePortal.class,                 new ResourceLocation(OverpoweredMod.MOD_ID,"tile_portal_block"));
    GameRegistry.registerTileEntity(TileCrystalMatterReplicator.class,new ResourceLocation(OverpoweredMod.MOD_ID,"tile_crystal_matter_generator"));
    GameRegistry.registerTileEntity(TileAdvancedOreRefinery.class,    new ResourceLocation(OverpoweredMod.MOD_ID,"tile_advanced_ore_refinery"));
    GameRegistry.registerTileEntity(TileFusionEnergyConverter.class,  new ResourceLocation(OverpoweredMod.MOD_ID,"tile_fusion_energy_converter"));
    GameRegistry.registerTileEntity(TileFusionChamber.class,          new ResourceLocation(OverpoweredMod.MOD_ID,"tile_singularity_container"));
    Debug.log_setup_info("Finished registering Tile Entities.");
  }

  @SubscribeEvent
  public static final void registerRecipes(final RegistryEvent.Register<IRecipe> event){
    OverpoweredMod.log.info("Begin registering All Recipes...");

    Recipes.register();
    FurnaceRecipes.register();
    if(Features.compressor){ // running this code doesn't hurt anything, but this boolean check is an optimization.
      CompressorRecipes.register();
    }
    Setup.registered_recipes = true;
    
    OverpoweredMod.log.info("Finished registering all Recipes.");
  }

  @SubscribeEvent
  public static final void registerSounds(final RegistryEvent.Register<SoundEvent> event){
    Debug.log_setup_info("Begin Sound Registry Event...");
    final IForgeRegistry<SoundEvent> game = event.getRegistry();
    game.register(Sounds.laser_fire_sound);
    Debug.log_setup_info("Finished Sound Registry Event.");
  }

  @SubscribeEvent
  public static final void registerBiomes(final RegistryEvent.Register<Biome> event){
    Debug.log_setup_info("Begin Biome Registry Event...");
    final IForgeRegistry<Biome> game = event.getRegistry();
    game.register(WeirdDimension.weird_biome);
    Debug.log_setup_info("Finished Biome Registry Event.");
  }

}
