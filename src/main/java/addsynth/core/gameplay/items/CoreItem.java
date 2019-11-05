package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import addsynth.core.items.BaseItem;

public class CoreItem extends BaseItem {

  public CoreItem(final String name){
    ADDSynthCore.registry.register_item(this, name);
  }

}
