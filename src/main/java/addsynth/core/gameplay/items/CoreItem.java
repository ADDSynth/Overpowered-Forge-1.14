package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;

@Deprecated
public class CoreItem extends Item { // TODO: remove in v1.4

  public CoreItem(final Item.Properties properties, final String name){
    super(properties);
    ADDSynthCore.registry.register_item(this, name);
  }

  /** WARNING: This constructor should only be used by Material items! */
  public CoreItem(final String name, final boolean isGem){
    super(new Item.Properties().group(isGem ? CreativeTabs.gems_creative_tab : CreativeTabs.metals_creative_tab));
    OverpoweredMod.registry.register_item(this, name);
  }

}
