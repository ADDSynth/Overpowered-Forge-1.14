package addsynth.overpoweredmod.compatability;

import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Metals;
import addsynth.overpoweredmod.game.core.ModItems;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.proxy.IEMCProxy;
import net.minecraft.item.ItemStack;

public final class ProjectE {

  public static final void register_emc_values(){
    final IEMCProxy emc = ProjectEAPI.getEMCProxy();
    if(emc != null){
      
      // TODO: Move ADDSynthCore items such as gems and metal ingots to ADDSynthCore PostInit.
      
      if(Features.crystal_matter_generator){
        emc.registerCustomEMC(new ItemStack(Gems.RUBY.shard,1),     910L);
        emc.registerCustomEMC(new ItemStack(Gems.TOPAZ.shard,1),    910L);
        emc.registerCustomEMC(new ItemStack(Gems.CITRINE.shard,1),  910L);
        emc.registerCustomEMC(new ItemStack(Gems.EMERALD.shard,1),  910L);
        emc.registerCustomEMC(new ItemStack(Gems.DIAMOND.shard,1),  910L);
        emc.registerCustomEMC(new ItemStack(Gems.SAPPHIRE.shard,1), 910L);
        emc.registerCustomEMC(new ItemStack(Gems.AMETHYST.shard,1), 910L);
        emc.registerCustomEMC(new ItemStack(Gems.QUARTZ.shard,1),   910L);
      }
      
      emc.registerCustomEMC(new ItemStack(Gems.ruby,1),     8192L); // ProjectE OreDictionary defaults to 2024 emc
      emc.registerCustomEMC(new ItemStack(Gems.topaz,1),    8192L);
      emc.registerCustomEMC(new ItemStack(Gems.citrine,1),  8192L);
      emc.registerCustomEMC(new ItemStack(Gems.sapphire,1), 8192L); // ProjectE OreDictionary defaults to 2024 emc
      emc.registerCustomEMC(new ItemStack(Gems.amethyst,1), 8192L);
      
      emc.registerCustomEMC(new ItemStack(Gems.RUBY.block_item,1),     73728L);
      emc.registerCustomEMC(new ItemStack(Gems.TOPAZ.block_item,1),    73728L);
      emc.registerCustomEMC(new ItemStack(Gems.CITRINE.block_item,1),  73728L);
      emc.registerCustomEMC(new ItemStack(Gems.SAPPHIRE.block_item,1), 73728L);
      emc.registerCustomEMC(new ItemStack(Gems.AMETHYST.block_item,1), 73728L);

      emc.registerCustomEMC(new ItemStack(Init.energy_crystal_shards,1), 7282L);
      // MAYBE: I feel as though people are going to get back at me for not calculating this correctly.
      emc.registerCustomEMC(new ItemStack(Init.energy_crystal,1),       65536L);
      emc.registerCustomEMC(new ItemStack(Init.light_block,1),         589824L);
      emc.registerCustomEMC(new ItemStack(Init.void_crystal,1),        131072L);
      emc.registerCustomEMC(new ItemStack(Init.null_block,1),          524288L);
      
      emc.registerCustomEMC(new ItemStack(ModItems.unknown_technology,1), 793600L); // based on the recipe
      
      emc.registerCustomEMC(new ItemStack(Metals.TIN.ingot,1),       256L);
      emc.registerCustomEMC(new ItemStack(Metals.ALUMINUM.ingot,1),  256L);
      emc.registerCustomEMC(new ItemStack(Metals.COPPER.ingot,1),    256L);
      emc.registerCustomEMC(new ItemStack(Metals.STEEL.ingot,1),     512L);
      emc.registerCustomEMC(new ItemStack(Metals.BRONZE.ingot,1),    512L);
      emc.registerCustomEMC(new ItemStack(Metals.SILVER.ingot,1),   2048L); // Same emc value ProjectE gives Gold Ingot.
      emc.registerCustomEMC(new ItemStack(Metals.PLATINUM.ingot,1), 8192L);
      emc.registerCustomEMC(new ItemStack(Metals.TITANIUM.ingot,1), 8192L);
      
      emc.registerCustomEMC(new ItemStack(Metals.TIN.block, 1),       2304L);
      emc.registerCustomEMC(new ItemStack(Metals.ALUMINUM.block, 1),  2304L);
      emc.registerCustomEMC(new ItemStack(Metals.COPPER.block, 1),    2304L);
      emc.registerCustomEMC(new ItemStack(Metals.STEEL.block, 1),     4608L);
      emc.registerCustomEMC(new ItemStack(Metals.BRONZE.block, 1),    4608L);
      emc.registerCustomEMC(new ItemStack(Metals.SILVER.block, 1),   18432L);
      emc.registerCustomEMC(new ItemStack(Metals.PLATINUM.block, 1), 73728L);
      emc.registerCustomEMC(new ItemStack(Metals.TITANIUM.block, 1), 73728L);
      
      if(Features.compressor){
        emc.registerCustomEMC(new ItemStack(Metals.TIN.plating,1),       256L);
        emc.registerCustomEMC(new ItemStack(Metals.ALUMINUM.plating,1),  256L);
        emc.registerCustomEMC(new ItemStack(Metals.COPPER.plating,1),    256L);
        emc.registerCustomEMC(new ItemStack(Metals.STEEL.plating,1),     512L);
        emc.registerCustomEMC(new ItemStack(Metals.BRONZE.plating,1),    512L);
        emc.registerCustomEMC(new ItemStack(Metals.SILVER.plating,1),   2048L);
        emc.registerCustomEMC(new ItemStack(Metals.PLATINUM.plating,1), 8192L);
        emc.registerCustomEMC(new ItemStack(Metals.TITANIUM.plating,1), 8192L);
      }
    }
  }

}
