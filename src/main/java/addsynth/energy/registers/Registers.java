package addsynth.energy.registers;

import addsynth.core.game.RegistryUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.Debug;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipes;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ADDSynthEnergy.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class Registers {

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    Debug.log_setup_info("Begin registering blocks...");

    final IForgeRegistry<Block> game = event.getRegistry();

    game.register(EnergyBlocks.wire);
    game.register(EnergyBlocks.generator);
    if(Config.energy_storage_container.get()){   game.register(EnergyBlocks.energy_storage); }
    game.register(EnergyBlocks.compressor);
    if(Config.electric_furnace.get()){           game.register(EnergyBlocks.electric_furnace); }
    game.register(EnergyBlocks.circuit_fabricator);
    if(Config.universal_energy_interface.get()){ game.register(EnergyBlocks.universal_energy_machine); }
    
    Debug.log_setup_info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    Debug.log_setup_info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.wire));
    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.generator));
    if(Config.energy_storage_container.get()){   game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.energy_storage)); }
    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.compressor));
    if(Config.electric_furnace.get()){           game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.electric_furnace)); }
    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.circuit_fabricator));
    if(Config.universal_energy_interface.get()){ game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.universal_energy_machine)); }

    game.register(EnergyItems.power_core);
    game.register(EnergyItems.advanced_power_core);
    game.register(EnergyItems.circuit_tier_1);
    game.register(EnergyItems.circuit_tier_2);
    game.register(EnergyItems.circuit_tier_3);
    game.register(EnergyItems.circuit_tier_4);
    game.register(EnergyItems.circuit_tier_5);
    game.register(EnergyItems.circuit_tier_6);
    game.register(EnergyItems.circuit_tier_7);
    game.register(EnergyItems.circuit_tier_8);

    Debug.log_setup_info("Done registering items.");
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event){
    Debug.log_setup_info("Begin registering tile entities...");
    final IForgeRegistry<TileEntityType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Tiles.ENERGY_WIRE,                Names.ENERGY_WIRE);
    RegistryUtil.register(game, Tiles.GENERATOR,                  Names.GENERATOR);
    RegistryUtil.register(game, Tiles.ENERGY_CONTAINER,           Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Tiles.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Tiles.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
    RegistryUtil.register(game, Tiles.CIRCUIT_FABRICATOR,         Names.CIRCUIT_FABRICATOR);
    RegistryUtil.register(game, Tiles.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
    Debug.log_setup_info("Done registering tile entities.");
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
    Debug.log_setup_info("Begin registering containers...");
    final IForgeRegistry<ContainerType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Containers.GENERATOR,                  Names.GENERATOR);
    RegistryUtil.register(game, Containers.ENERGY_STORAGE_CONTAINER,   Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Containers.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Containers.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
    RegistryUtil.register(game, Containers.CIRCUIT_FABRICATOR,         Names.CIRCUIT_FABRICATOR);
    RegistryUtil.register(game, Containers.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
    Debug.log_setup_info("Done registering containers.");
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event){
    Debug.log_setup_info("Begin registering recipe serializers...");
    final IForgeRegistry<IRecipeSerializer<?>> game = event.getRegistry();
    RegistryUtil.register(game,        CompressorRecipes.INSTANCE.serializer, Names.COMPRESSOR);
    RegistryUtil.register(game, CircuitFabricatorRecipes.INSTANCE.serializer, Names.CIRCUIT_FABRICATOR);
    Debug.log_setup_info("Done registering recipe serializers.");
  }

}
