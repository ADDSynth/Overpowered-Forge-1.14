package addsynth.material;

import net.minecraft.item.Item;

public final class MaterialItem extends Item {

  public MaterialItem(final String name){
    super(new Item.Properties().group(ADDSynthMaterials.creative_tab));
    ADDSynthMaterials.registry.register_item(this, name);
  }

  public MaterialItem(final Item.Properties properties, final String name){
    super(properties.group(ADDSynthMaterials.creative_tab));
    ADDSynthMaterials.registry.register_item(this, name);
  }

}
