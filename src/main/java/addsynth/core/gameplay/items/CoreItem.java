package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;

public class CoreItem extends Item {

  public CoreItem(final String name){
    super(new Item.Properties().group(ADDSynthCore.creative_tab));
    ADDSynthCore.registry.register_item(this, name);
  }

  public CoreItem(final Item.Properties properties, final String name){
    super(properties.group(ADDSynthCore.creative_tab));
    ADDSynthCore.registry.register_item(this, name);
  }

}
