package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.item.Item;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final String name){
    super(new Item.Properties());
    OverpoweredMod.registry.register_item(this, name);
  }

  public OverpoweredItem(final Item.Properties properties, final String name){
    super(properties);
    OverpoweredMod.registry.register_item(this, name);
  }

}
