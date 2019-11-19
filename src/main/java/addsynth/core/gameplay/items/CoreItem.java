package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;

public class CoreItem extends Item {

  public CoreItem(final Item.Properties properties, final String name){
    super(properties);
    ADDSynthCore.registry.register_item(this, name);
  }

}
