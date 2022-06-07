package addsynth.material.compat;

import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
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
    final String mod = Compatibility.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;
    
    if(Features.crystal_matter_generator.get()){ // REMOVE shards
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.shard),     EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.shard),    EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.shard),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.EMERALD.shard),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.DIAMOND.shard),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.shard), EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.shard), EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.QUARTZ.shard),   EMCValue.gem_shard));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.gem),     EMCValue.gemstone)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.gem),    EMCValue.gemstone));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.gem),  EMCValue.gemstone));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.gem), EMCValue.gemstone)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.gem), EMCValue.gemstone));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.block_item),     EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.block_item),    EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.block_item),  EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.block_item), EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.block_item), EMCValue.gem_block));

    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.ingot),      EMCValue.common_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.ingot), EMCValue.common_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.ingot),   EMCValue.common_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.ingot),    EMCValue.strong_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.ingot),   EMCValue.strong_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.ingot),   EMCValue.uncommon_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.ingot), EMCValue.rare_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.ingot), EMCValue.rare_metal));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.block),      EMCValue.common_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.block), EMCValue.common_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.block),   EMCValue.common_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.block),    EMCValue.strong_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.block),   EMCValue.strong_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.block),   EMCValue.uncommon_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.block), EMCValue.rare_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.block), EMCValue.rare_metal_block));
    
    if(MaterialsCompat.addsynth_energy_loaded){
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.IRON.plating),     EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.plating),      EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.plating), EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.plating),   EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.plating),    EMCValue.strong_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.plating),   EMCValue.strong_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.plating),   EMCValue.uncommon_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.GOLD.plating),     EMCValue.uncommon_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.plating), EMCValue.rare_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.plating), EMCValue.rare_metal));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILICON.item), EMCValue.silicon));
  }

}
