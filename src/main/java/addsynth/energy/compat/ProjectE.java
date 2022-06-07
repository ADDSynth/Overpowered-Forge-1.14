package addsynth.energy.compat;

import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyItems;
import net.minecraftforge.fml.InterModComms;
import moze_intel.projecte.api.imc.CustomEMCRegistration;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.nss.NSSItem;

public final class ProjectE {

  public static final void register_emc_values(){

    final String sender = ADDSynthEnergy.MOD_ID;
    final String mod = Compatibility.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;
    
    // x1 = 900, x8 = 10000, x9 = ~12000
    // Using linear    equation: y = -400 + 1300x
    // Using quadratic equation: y = 100(x+2)^2
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_2),
      true ? 1600L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.red_dye        + EMCValue.redstone +
      EMCValue.nether_quartz + EMCValue.common_metal   + EMCValue.uncommon_metal
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_3),
      true ? 2500L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.yellow_dye     + EMCValue.redstone_torch +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_4),
      true ? 3600L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.blue_dye       + EMCValue.redstone_repeater +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal + EMCValue.glowstone_dust
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_5),
      true ? 4900L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.black_dye      + EMCValue.redstone_repeater +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal + EMCValue.diamond
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_6),
      true ? 6400L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.purple_dye     + EMCValue.redstone_comparator +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal + EMCValue.ender_pearl
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_7),
      true ? 8100L :
      EMCValue.paper         + EMCValue.common_metal   + EMCValue.orange_dye     + EMCValue.redstone_comparator +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal + EMCValue.ender_eye
    ));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(EnergyItems.circuit_tier_8),
      true ? 10_000L :
      EMCValue.common_metal  + EMCValue.common_metal   + EMCValue.white_dye      + EMCValue.redstone_lamp +
      EMCValue.silicon       + EMCValue.uncommon_metal + EMCValue.uncommon_metal + EMCValue.ender_eye
    ));

  }
  
}
