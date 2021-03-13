package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import addsynth.material.ADDSynthMaterials;
import net.minecraft.item.Item;

@Deprecated
public class CoreItem extends Item { // REMOVE: CoreItem in v1.4

  public CoreItem(final Item.Properties properties, final String name){
    super(properties);
    ADDSynthCore.registry.register_item(this, name);
  }

  /** WARNING: This constructor should only be used by Material items! */
  public CoreItem(final String name, final boolean isGem){
    super(new Item.Properties().group(ADDSynthMaterials.creative_tab));
    ADDSynthMaterials.registry.register_item(this, name);
  }

}
