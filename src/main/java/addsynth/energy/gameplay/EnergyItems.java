package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import net.minecraft.item.Item;

public final class EnergyItems {

  public static final Item            power_core               = register("power_core");
  public static final Item            advanced_power_core      = register("advanced_power_core");
  public static final Item            circuit_tier_1           = register("circuit_tier_1");
  public static final Item            circuit_tier_2           = register("circuit_tier_2");
  public static final Item            circuit_tier_3           = register("circuit_tier_3");
  public static final Item            circuit_tier_4           = register("circuit_tier_4");
  public static final Item            circuit_tier_5           = register("circuit_tier_5");
  public static final Item            circuit_tier_6           = register("circuit_tier_6");
  public static final Item            circuit_tier_7           = register("circuit_tier_7");
  public static final Item            circuit_tier_8           = register("circuit_tier_8");
  public static final Item[] circuit = {
    circuit_tier_1, circuit_tier_2, circuit_tier_3, circuit_tier_4, circuit_tier_5,
    circuit_tier_6, circuit_tier_7, circuit_tier_8
  };
  
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
