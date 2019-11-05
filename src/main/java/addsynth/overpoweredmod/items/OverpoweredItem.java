package addsynth.overpoweredmod.items;

import addsynth.core.items.BaseItem;
import addsynth.overpoweredmod.OverpoweredMod;

public class OverpoweredItem extends BaseItem {

  public OverpoweredItem(final String name){
    OverpoweredMod.registry.register_item(this, name);
  }

}
