package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final String name){
    this(name, CreativeTabs.creative_tab);
  }

  public OverpoweredItem(final String name, final ItemGroup tab){
    super(new Item.Properties().group(tab));
    OverpoweredMod.registry.register_item(this, name);
  }

}
