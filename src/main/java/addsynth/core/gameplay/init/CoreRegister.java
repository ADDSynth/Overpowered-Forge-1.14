package addsynth.core.gameplay.init;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Features;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.Tiles;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ADDSynthCore.MOD_ID)
public final class CoreRegister {

  @SubscribeEvent
  public static final void register_blocks(final RegistryEvent.Register<Block> event){
    ADDSynthCore.log.info("Begin registering blocks...");

    final IForgeRegistry<Block> game = event.getRegistry();

    ADDSynthCore.init_config();
    
    if(Features.caution_block.get()){ game.register(Core.caution_block); }
    if(Features.music_box.get()){ game.register(Core.music_box); }

    ADDSynthCore.log.info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void register_items(final RegistryEvent.Register<Item> event){
    ADDSynthCore.log.info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    if(Features.caution_block.get()){    game.register(ADDSynthCore.registry.getItemBlock(Core.caution_block)); }
    if(Features.music_box.get()){
      game.register(ADDSynthCore.registry.getItemBlock(Core.music_box));
      if(Features.music_sheet.get()){ game.register(Core.music_sheet); }
    }
    if(Features.scythes.get()){
      game.register(Core.wooden_scythe);
      game.register(Core.stone_scythe);
      game.register(Core.iron_scythe);
      game.register(Core.gold_scythe);
      game.register(Core.diamond_scythe);
    }

    ADDSynthCore.log.info("Done registering items.");
  }

  @SubscribeEvent
  public static final void registerModels(final ModelRegistryEvent event){
    ADDSynthCore.registry.register_inventory_item_models();
  }

  @SubscribeEvent
  public static final void register_tileentities(final RegistryEvent.Register<TileEntityType<?>> event){
    final IForgeRegistry<TileEntityType<?>> game = event.getRegistry();
    game.register(Tiles.MUSIC_BOX.setRegistryName(new ResourceLocation(ADDSynthCore.MOD_ID,"music_box")));
  }

}
