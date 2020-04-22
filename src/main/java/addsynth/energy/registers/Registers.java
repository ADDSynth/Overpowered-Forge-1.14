package addsynth.energy.registers;

import addsynth.core.game.RegistryUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.Config;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.compressor.recipe.CompressorRecipes;
import addsynth.energy.registers.Containers;
import addsynth.energy.registers.Names;
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
    final IForgeRegistry<Block> game = event.getRegistry();
    game.register(EnergyBlocks.wire);
    game.register(EnergyBlocks.compressor);
    if(Config.energy_storage_container.get()){   game.register(EnergyBlocks.energy_storage); }
    if(Config.universal_energy_interface.get()){ game.register(EnergyBlocks.universal_energy_machine); }
    if(Config.electric_furnace.get()){           game.register(EnergyBlocks.electric_furnace); }
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.wire));
    game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.compressor));
    if(Config.energy_storage_container.get()){   game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.energy_storage)); }
    if(Config.universal_energy_interface.get()){ game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.universal_energy_machine)); }
    if(Config.electric_furnace.get()){           game.register(ADDSynthEnergy.registry.getItemBlock(EnergyBlocks.electric_furnace)); }

    game.register(EnergyItems.power_core);
    game.register(EnergyItems.advanced_power_core);
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event){
    final IForgeRegistry<TileEntityType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Tiles.ENERGY_WIRE,                Names.ENERGY_WIRE);
    RegistryUtil.register(game, Tiles.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Tiles.ENERGY_CONTAINER,           Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Tiles.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
    RegistryUtil.register(game, Tiles.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
    final IForgeRegistry<ContainerType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Containers.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Containers.ENERGY_STORAGE_CONTAINER,   Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Containers.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
    RegistryUtil.register(game, Containers.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event){
    final IForgeRegistry<IRecipeSerializer<?>> game = event.getRegistry();
    RegistryUtil.register(game, CompressorRecipes.serializer, Names.COMPRESSOR);
  }

}
