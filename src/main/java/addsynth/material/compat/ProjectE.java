package addsynth.material.compat;

import addsynth.core.game.Compatability;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.Material;
import addsynth.overpoweredmod.config.Features;
import net.minecraftforge.fml.InterModComms;
import moze_intel.projecte.api.imc.CustomEMCRegistration;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.nss.NSSItem;

public final class ProjectE {

  public static final void register_emc_values(){

    final String sender = ADDSynthMaterials.MOD_ID;
    final String mod = Compatability.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;
    
    if(Features.crystal_matter_generator.get()){ // REMOVE shards
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.shard),     910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.shard),    910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.EMERALD.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.DIAMOND.shard),  910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.shard), 910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.shard), 910L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.QUARTZ.shard),   910L));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.gem),     8192L)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.gem),    8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.gem),  8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.gem), 8192L)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.gem), 8192L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.block_item),     73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.block_item),    73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.block_item),  73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.block_item), 73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.block_item), 73728L));

    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.ingot),       256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.ingot),  256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.ingot),    256L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.ingot),     512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.ingot),    512L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.ingot),   2048L)); // Same emc value ProjectE gives Gold Ingot.
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.ingot), 8192L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.ingot), 8192L));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.block),       2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.block),  2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.block),    2304L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.block),     4608L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.block),    4608L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.block),   18432L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.block), 73728L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.block), 73728L));
    
    if(MaterialsCompat.addsynth_energy_loaded){
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.IRON.plating),      256L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.plating),       256L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.plating),  256L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.plating),    256L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.plating),     512L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.plating),    512L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.plating),   2048L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.GOLD.plating),     2048L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.plating), 8192L));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.plating), 8192L));
    }
  }

}
