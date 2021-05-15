package addsynth.overpoweredmod.registers;

import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.game.core.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = OverpoweredMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class Registers {

  static {
    Debug.log_setup_info("Registers class was loaded.");
  }

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    OverpoweredMod.log.info("Begin Block Registration Event...");
    
    final IForgeRegistry<Block> game = event.getRegistry();
    
    if(Features.light_block.get()){ game.register(Init.light_block); }
    if(Features.null_block.get()){ game.register(Init.null_block); }
    
    if(Features.iron_frame_block.get()){ game.register(Init.iron_frame_block); }
    if(Features.black_hole.get()){ game.register(Init.black_hole); }
    
    if(Features.trophies()){
      if(Features.bronze_trophy.get()){ game.register(Trophy.bronze); }
      if(Features.silver_trophy.get()){ game.register(Trophy.silver); }
      if(Features.gold_trophy.get()){ game.register(Trophy.gold); }
      if(Features.platinum_trophy.get()){ game.register(Trophy.platinum); }
    }
    
    game.register(Wires.data_cable);
    if(Features.crystal_energy_extractor.get()){ game.register(Machines.crystal_energy_extractor); }
    if(Features.gem_converter.get()){ game.register(Machines.gem_converter); }
    game.register(Machines.inverter);
    if(Features.magic_infuser.get()){ game.register(Machines.magic_infuser); }
    if(Features.identifier.get()){ game.register(Machines.identifier); }
    if(Features.energy_suspension_bridge.get()){
      game.register(Machines.energy_suspension_bridge);
      game.register(Machines.white_energy_bridge);
      game.register(Machines.red_energy_bridge);
      game.register(Machines.orange_energy_bridge);
      game.register(Machines.yellow_energy_bridge);
      game.register(Machines.green_energy_bridge);
      game.register(Machines.cyan_energy_bridge);
      game.register(Machines.blue_energy_bridge);
      game.register(Machines.magenta_energy_bridge);
    }
    if(Features.portal.get()){
      game.register(Machines.portal_control_panel);
      game.register(Machines.portal_frame);
      game.register(Portal.portal);
      if(Features.unknown_dimension.get()){
        game.register(Portal.unknown_wood);
        game.register(Portal.unknown_leaves);
      }
    }
    if(Features.crystal_matter_generator.get()){ game.register(Machines.crystal_matter_generator); }
    if(Features.advanced_ore_refinery.get()){ game.register(Machines.advanced_ore_refinery); }
    if(Features.lasers.get()){
      game.register(Machines.laser_housing);
      // MAYBE: everywhere I check for each laser is enabled, I should've had the config change an enabled boolean variable in the Laser class!
      if(Features.white_laser.get()){   game.register(Laser.WHITE.cannon);   game.register(Laser.WHITE.beam);   }
      if(Features.red_laser.get()){     game.register(Laser.RED.cannon);     game.register(Laser.RED.beam);     }
      if(Features.orange_laser.get()){  game.register(Laser.ORANGE.cannon);  game.register(Laser.ORANGE.beam);  }
      if(Features.yellow_laser.get()){  game.register(Laser.YELLOW.cannon);  game.register(Laser.YELLOW.beam);  }
      if(Features.green_laser.get()){   game.register(Laser.GREEN.cannon);   game.register(Laser.GREEN.beam);   }
      if(Features.cyan_laser.get()){    game.register(Laser.CYAN.cannon);    game.register(Laser.CYAN.beam);    }
      if(Features.blue_laser.get()){    game.register(Laser.BLUE.cannon);    game.register(Laser.BLUE.beam);    }
      if(Features.magenta_laser.get()){ game.register(Laser.MAGENTA.cannon); game.register(Laser.MAGENTA.beam); }
    }
    if(Features.fusion_container.get()){
      game.register(Machines.fusion_converter);
      game.register(Machines.fusion_control_unit);
      game.register(Machines.fusion_chamber);
      game.register(Machines.fusion_control_laser);
      game.register(Machines.fusion_control_laser_beam);
    }
    
    OverpoweredMod.log.info("Finished Block Registration Event.");
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    OverpoweredMod.log.info("Begin Item Registration Event...");
    
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(Init.energy_crystal_shards);
    game.register(Init.energy_crystal);
    if(Features.light_block.get()){ game.register(OverpoweredMod.registry.getItemBlock(Init.light_block)); }
    game.register(Init.void_crystal);
    if(Features.null_block.get()){ game.register(OverpoweredMod.registry.getItemBlock(Init.null_block)); }
    
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
    if(Features.dimensional_anchor.get()){ game.register(ModItems.dimensional_anchor); }
    
    if(Features.trophies()){
      game.register(Trophy.trophy_base);
      if(Features.bronze_trophy.get()){ game.register(Trophy.BRONZE.item_block); }
      if(Features.silver_trophy.get()){ game.register(Trophy.SILVER.item_block); }
      if(Features.gold_trophy.get()){ game.register(Trophy.GOLD.item_block); }
      if(Features.platinum_trophy.get()){ game.register(Trophy.PLATINUM.item_block); }
    }
    
    game.register(OverpoweredMod.registry.getItemBlock(Wires.data_cable));
    if(Features.crystal_energy_extractor.get()){ game.register(OverpoweredMod.registry.getItemBlock(Machines.crystal_energy_extractor)); }
    if(Features.gem_converter.get()){    game.register(OverpoweredMod.registry.getItemBlock(Machines.gem_converter)); }
    game.register(OverpoweredMod.registry.getItemBlock(Machines.inverter));
    if(Features.magic_infuser.get()){    game.register(OverpoweredMod.registry.getItemBlock(Machines.magic_infuser)); }
    if(Features.identifier.get()){       game.register(OverpoweredMod.registry.getItemBlock(Machines.identifier)); }
    if(Features.energy_suspension_bridge.get()){ game.register(OverpoweredMod.registry.getItemBlock(Machines.energy_suspension_bridge)); }
    if(Features.portal.get()){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.portal_control_panel));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.portal_frame));
      // MAYBE: register Item versions of the unknown / weird tree  (but item order is specific. don't register them here.)
    }
    if(Features.crystal_matter_generator.get()){ game.register(OverpoweredMod.registry.getItemBlock(Machines.crystal_matter_generator)); }
    if(Features.advanced_ore_refinery.get()){ game.register(OverpoweredMod.registry.getItemBlock(Machines.advanced_ore_refinery)); }
    
    if(Features.lasers.get()){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.laser_housing));
      if(Features.white_laser.get()){   game.register(OverpoweredMod.registry.getItemBlock(Laser.WHITE.cannon));   }
      if(Features.red_laser.get()){     game.register(OverpoweredMod.registry.getItemBlock(Laser.RED.cannon));     }
      if(Features.orange_laser.get()){  game.register(OverpoweredMod.registry.getItemBlock(Laser.ORANGE.cannon));  }
      if(Features.yellow_laser.get()){  game.register(OverpoweredMod.registry.getItemBlock(Laser.YELLOW.cannon));  }
      if(Features.green_laser.get()){   game.register(OverpoweredMod.registry.getItemBlock(Laser.GREEN.cannon));   }
      if(Features.cyan_laser.get()){    game.register(OverpoweredMod.registry.getItemBlock(Laser.CYAN.cannon));    }
      if(Features.blue_laser.get()){    game.register(OverpoweredMod.registry.getItemBlock(Laser.BLUE.cannon));    }
      if(Features.magenta_laser.get()){ game.register(OverpoweredMod.registry.getItemBlock(Laser.MAGENTA.cannon)); }
    }
    
    if(Features.fusion_container.get()){
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_converter));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_control_unit));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_chamber));
      game.register(OverpoweredMod.registry.getItemBlock(Machines.fusion_control_laser));
    }
    
    if(Features.iron_frame_block.get()){ game.register(OverpoweredMod.registry.getItemBlock(Init.iron_frame_block)); }
    if(Features.black_hole.get()){       game.register(OverpoweredMod.registry.getItemBlock(Init.black_hole)); }
    
    if(Features.energy_tools.get()){
      for(Item tool : Tools.energy_tools.tools){ game.register(tool); }
      if(addsynth.core.config.Features.scythes.get()){
        game.register(Tools.energy_scythe);
      }
    }
    if(Features.void_tools.get()){
      for(Item tool : Tools.void_toolset.tools){ game.register(tool); }
    }
    if(Features.identifier.get()){
      for(Item[] armor_set : Tools.unidentified_armor){
        for(Item armor : armor_set){ game.register(armor); }
      }
    }
    
    game.register(Portal.portal_image);

    OverpoweredMod.log.info("Finished Item Registration Event.");
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event){ // TEST: Maybe don't register TileEntities if they aren't enabled in the config.
    Debug.log_setup_info("Begin registering Tile Entities...");
    /*
      https://github.com/MinecraftForge/MinecraftForge/pull/4681#issuecomment-405115908
      TODO: If anyone needs an example of how to fix the warning caused by this change without breaking old saved games,
      I (someone else) just updated all of McJty's mods to use a DataFixer to do so.
    */
    final IForgeRegistry<TileEntityType<?>> game = event.getRegistry();

    RegistryUtil.register(game, Tiles.CRYSTAL_ENERGY_EXTRACTOR,   Names.CRYSTAL_ENERGY_EXTRACTOR);
    RegistryUtil.register(game, Tiles.GEM_CONVERTER,              Names.GEM_CONVERTER);
    RegistryUtil.register(game, Tiles.INVERTER,                   Names.INVERTER);
    RegistryUtil.register(game, Tiles.MAGIC_INFUSER,              Names.MAGIC_INFUSER);
    RegistryUtil.register(game, Tiles.IDENTIFIER,                 Names.IDENTIFIER);
    RegistryUtil.register(game, Tiles.ENERGY_SUSPENSION_BRIDGE,   Names.ENERGY_SUSPENSION_BRIDGE);
    RegistryUtil.register(game, Tiles.LASER_MACHINE,              Names.LASER_HOUSING);
    RegistryUtil.register(game, Tiles.LASER,                      Names.LASER_CANNON);
    RegistryUtil.register(game, Tiles.LASER_BEAM,                 Names.LASER_BEAM);
    RegistryUtil.register(game, Tiles.DATA_CABLE,                 Names.DATA_CABLE);
    RegistryUtil.register(game, Tiles.PORTAL_CONTROL_PANEL,       Names.PORTAL_CONTROL_PANEL);
    RegistryUtil.register(game, Tiles.PORTAL_FRAME,               Names.PORTAL_FRAME);
    RegistryUtil.register(game, Tiles.PORTAL_BLOCK,               Names.PORTAL_RIFT);
    RegistryUtil.register(game, Tiles.CRYSTAL_MATTER_REPLICATOR,  Names.CRYSTAL_MATTER_GENERATOR);
    RegistryUtil.register(game, Tiles.ADVANCED_ORE_REFINERY,      Names.ADVANCED_ORE_REFINERY);
    RegistryUtil.register(game, Tiles.FUSION_ENERGY_CONVERTER,    Names.FUSION_CONVERTER);
    RegistryUtil.register(game, Tiles.FUSION_CHAMBER,             Names.FUSION_CHAMBER);
    RegistryUtil.register(game, Tiles.BLACK_HOLE,                 Names.BLACK_HOLE);

    Debug.log_setup_info("Finished registering Tile Entities.");
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
    final IForgeRegistry<ContainerType<?>> game = event.getRegistry();
    
    RegistryUtil.register(game, Containers.CRYSTAL_ENERGY_EXTRACTOR,   Names.CRYSTAL_ENERGY_EXTRACTOR);
    RegistryUtil.register(game, Containers.GEM_CONVERTER,              Names.GEM_CONVERTER);
    RegistryUtil.register(game, Containers.INVERTER,                   Names.INVERTER);
    RegistryUtil.register(game, Containers.IDENTIFIER,                 Names.IDENTIFIER);
    RegistryUtil.register(game, Containers.MAGIC_INFUSER,              Names.MAGIC_INFUSER);
    RegistryUtil.register(game, Containers.ENERGY_SUSPENSION_BRIDGE,   Names.ENERGY_SUSPENSION_BRIDGE);
    RegistryUtil.register(game, Containers.LASER_HOUSING,              Names.LASER_HOUSING);
    RegistryUtil.register(game, Containers.ADVANCED_ORE_REFINERY,      Names.ADVANCED_ORE_REFINERY);
    RegistryUtil.register(game, Containers.CRYSTAL_MATTER_GENERATOR,   Names.CRYSTAL_MATTER_GENERATOR);
    RegistryUtil.register(game, Containers.FUSION_CHAMBER,             Names.FUSION_CHAMBER);
    RegistryUtil.register(game, Containers.PORTAL_CONTROL_PANEL,       Names.PORTAL_CONTROL_PANEL);
    RegistryUtil.register(game, Containers.PORTAL_FRAME,               Names.PORTAL_FRAME);
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
    // game.register(WeirdDimension.weird_biome);
    Debug.log_setup_info("Finished Biome Registry Event.");
  }

}
