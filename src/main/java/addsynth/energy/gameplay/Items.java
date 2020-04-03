package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import net.minecraft.item.Item;

public final class Items {

  public static final Item            power_core               = new Item(new Item.Properties().group(ADDSynthEnergy.creative_tab));
  public static final Item            advanced_power_core      = new Item(new Item.Properties().group(ADDSynthEnergy.creative_tab));
  
  static {
    ADDSynthEnergy.registry.register_item(power_core,          "power_core");
    ADDSynthEnergy.registry.register_item(advanced_power_core, "advanced_power_core");
  }

}
