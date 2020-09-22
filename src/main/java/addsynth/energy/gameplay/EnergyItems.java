package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import net.minecraft.item.Item;

public final class EnergyItems {

  public static final Item            power_core               = register("power_core");
  public static final Item            advanced_power_core      = register("advanced_power_core");
  
  private static final Item register(final String name){
    final Item item = new Item(new Item.Properties().group(ADDSynthEnergy.creative_tab));
    ADDSynthEnergy.registry.register_item(item, name);
    return item;
  }
  
  private static final Item register(final String name, final Item.Properties properties){
    final Item item = new Item(properties);
    ADDSynthEnergy.registry.register_item(item, name);
    return item;
  }
  
}
