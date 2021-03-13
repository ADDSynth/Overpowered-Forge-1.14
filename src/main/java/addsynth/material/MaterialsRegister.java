package addsynth.material;

import addsynth.material.compat.MaterialsCompat;
import addsynth.material.types.Gem;
import addsynth.material.types.Metal;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Metals;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ADDSynthMaterials.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class MaterialsRegister {

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    final IForgeRegistry<Block> game = event.getRegistry();
    
    for(Gem gem : Gems.index){
      if(gem.custom){
        game.register(gem.block);
        game.register(gem.ore);
      }
    }
    for(Metal metal : Metals.values){
      if(metal.custom){
        game.register(metal.block);
        if(metal.ore != null){ game.register(metal.ore); }
      }
    }
  }
  
  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    final IForgeRegistry<Item> game = event.getRegistry();

    for(Gem gem : Gems.index){ if(gem.custom){ game.register(gem.gem); } }
    for(Gem gem : Gems.index){ if(gem.custom){ game.register(gem.block_item); } }
    for(Gem gem : Gems.index){ if(gem.custom){ game.register(ADDSynthMaterials.registry.getItemBlock(gem.ore)); } }

    if(Features.crystal_matter_generator.get()){ // REMOVE shards
      for(Gem gem : Gems.index){ game.register(gem.shard); }
    }

    for(Metal metal : Metals.values){ if(metal.custom){ game.register(metal.ingot); } }
    for(Metal metal : Metals.values){ if(metal.custom){ game.register(ADDSynthMaterials.registry.getItemBlock(metal.block)); } }
    for(Metal metal : Metals.values){ if(metal.custom){ if(metal.ore != null){ game.register(ADDSynthMaterials.registry.getItemBlock(metal.ore)); } } }
    if(MaterialsCompat.addsynth_energy_loaded){
      ADDSynthMaterials.log.info("ADDSynth Energy was detected. Registering Metal Plates...");
      for(Metal metal : Metals.values){ game.register(metal.plating); }
    }
  }

}
