package addsynth.core.gameplay.init;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Features;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.Recipes;
import addsynth.core.gameplay.music_box.TileMusicBox;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ADDSynthCore.MOD_ID)
public final class CoreRegister {

  @SubscribeEvent
  public static final void register_blocks(final RegistryEvent.Register<Block> event){
    ADDSynthCore.log.info("Begin registering blocks...");

    final IForgeRegistry<Block> game = event.getRegistry();

    if(Setup.config_loaded == false){
      Setup.init_config();
    }
    
    if(Features.caution_block){ game.register(Core.caution_block); }
    if(Features.music_box){ game.register(Core.music_box); }

    ADDSynthCore.log.info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void register_items(final RegistryEvent.Register<Item> event){
    ADDSynthCore.log.info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    if(Features.caution_block){    game.register(ADDSynthCore.registry.getItemBlock(Core.caution_block)); }
    if(Features.music_box){
      game.register(ADDSynthCore.registry.getItemBlock(Core.music_box));
      if(Features.music_sheet){ game.register(Core.music_sheet); }
    }
    if(Features.scythes){
      game.register(Core.wooden_scythe);
      game.register(Core.stone_scythe);
      game.register(Core.iron_scythe);
      game.register(Core.gold_scythe);
      game.register(Core.diamond_scythe);
    }

    ADDSynthCore.log.info("Done registering items.");
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static final void registerModels(final ModelRegistryEvent event){
    ADDSynthCore.registry.register_inventory_item_models();
  }

  public static final void register_tileentities(){
    GameRegistry.registerTileEntity(TileMusicBox.class,               new ResourceLocation(ADDSynthCore.MOD_ID,"tile_music_box"));
  }

  @SubscribeEvent
  public static final void registerRecipes(final RegistryEvent.Register<IRecipe> event){
    Recipes.register();
  }

}
