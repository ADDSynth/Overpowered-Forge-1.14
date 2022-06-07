package addsynth.overpoweredmod.compatability;

import addsynth.core.compat.Compatibility;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Init;
import moze_intel.projecte.api.imc.CustomEMCRegistration;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.nss.NSSItem;
import net.minecraftforge.fml.InterModComms;

public final class ProjectE {

  public static final void register_emc_values(){
    
    final String sender = OverpoweredTechnology.MOD_ID;
    final String mod = Compatibility.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;

    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.energy_crystal_shards), 7282L));
    // MAYBE: I feel as though people are going to get back at me for not calculating this correctly.
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.celestial_gem),        65536L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.light_block),         589824L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.void_crystal),        131072L));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Init.null_block),          524288L));
    
  }

}
