package addsynth.overpoweredmod.compatability;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Metals;
import addsynth.overpoweredmod.game.core.ModItems;
import moze_intel.projecte.api.imc.CustomEMCRegistration;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.nss.NSSItem;
import net.minecraftforge.fml.InterModComms;

public final class ProjectE {

  public static final void register_emc_values(){
    
    // TODO: Move ADDSynthCore items such as gems and metal ingots to ADDSynthCore PostInit.
    
    final String sender = OverpoweredMod.MOD_ID;
    final String mod = Compatability.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;
    
    if(Features.crystal_matter_generator.get()){
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.RUBY.shard),     910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.TOPAZ.shard),    910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.CITRINE.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.EMERALD.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.DIAMOND.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.SAPPHIRE.shard), 910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.AMETHYST.shard), 910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.QUARTZ.shard),   910L));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.ruby),     8192L)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.topaz),    8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.citrine),  8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.sapphire), 8192L)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.amethyst), 8192L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.RUBY.block_item),     73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.TOPAZ.block_item),    73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.CITRINE.block_item),  73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.SAPPHIRE.block_item), 73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Gems.AMETHYST.block_item), 73728L));

    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.energy_crystal_shards), 7282L));
    // MAYBE: I feel as though people are going to get back at me for not calculating this correctly.
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.energy_crystal),       65536L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.light_block),         589824L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.void_crystal),        131072L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.null_block),          524288L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(ModItems.unknown_technology), 793600L)); // based on the recipe
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TIN.ingot),       256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.ALUMINUM.ingot),  256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.COPPER.ingot),    256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.STEEL.ingot),     512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.BRONZE.ingot),    512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.SILVER.ingot),   2048L)); // Same emc value ProjectE gives Gold Ingot.
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.PLATINUM.ingot), 8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TITANIUM.ingot), 8192L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TIN.block),       2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.ALUMINUM.block),  2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.COPPER.block),    2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.STEEL.block),     4608L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.BRONZE.block),    4608L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.SILVER.block),   18432L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.PLATINUM.block), 73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TITANIUM.block), 73728L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.IRON.plating),      256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TIN.plating),       256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.ALUMINUM.plating),  256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.COPPER.plating),    256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.STEEL.plating),     512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.BRONZE.plating),    512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.SILVER.plating),   2048L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.GOLD.plating),     2048L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.PLATINUM.plating), 8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Metals.TITANIUM.plating), 8192L));
  }

}
