package addsynth.core.material;

import java.util.Collection;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public final class MaterialsUtil {

  public static final Collection<Item> getOres(){
    return Tags.Items.ORES.getAllElements();
  }

  @Nullable
  public static final Collection<Item> getTagCollection(final ResourceLocation tag_id){
    final Tag<Item> item_tag = ItemTags.getCollection().get(tag_id);
    return item_tag != null ? item_tag.getAllElements() : null;
  }

  @Nullable
  public static final Collection<Item> getRubyBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/ruby"));
  }

  @Nullable
  public static final Collection<Item> getTopazBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/topaz"));
  }

  @Nullable
  public static final Collection<Item> getCitrineBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/citrine"));
  }

  @Nullable
  public static final Collection<Item> getEmeraldBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/emerald"));
  }

  @Nullable
  public static final Collection<Item> getDiamondBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/diamond"));
  }

  @Nullable
  public static final Collection<Item> getSapphireBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/sapphire"));
  }

  @Nullable
  public static final Collection<Item> getAmethystBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/amethyst"));
  }

  @Nullable
  public static final Collection<Item> getQuartzBlocks(){
    return getTagCollection(new ResourceLocation("forge:storage_blocks/quartz"));
  }

  @Nullable
  public static final Collection<Item> getRubies(){
    return getTagCollection(new ResourceLocation("forge:gems/ruby"));
  }

  @Nullable
  public static final Collection<Item> getTopaz(){
    return getTagCollection(new ResourceLocation("forge:gems/topaz"));
  }

  @Nullable
  public static final Collection<Item> getCitrine(){
    return getTagCollection(new ResourceLocation("forge:gems/citrine"));
  }

  @Nullable
  public static final Collection<Item> getEmeralds(){
    return getTagCollection(new ResourceLocation("forge:gems/emerald"));
  }

  @Nullable
  public static final Collection<Item> getDiamonds(){
    return getTagCollection(new ResourceLocation("forge:gems/diamonds"));
  }

  @Nullable
  public static final Collection<Item> getSapphires(){
    return getTagCollection(new ResourceLocation("forge:gems/sapphire"));
  }

  @Nullable
  public static final Collection<Item> getAmethysts(){
    return getTagCollection(new ResourceLocation("forge:gems/amethyst"));
  }

  @Nullable
  public static final Collection<Item> getQuartz(){
    return getTagCollection(new ResourceLocation("forge:gems/quartz"));
  }

  @Nullable
  public static final Collection<Item> getTinIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/tin"));
  }

  @Nullable
  public static final Collection<Item> getCopperIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/copper"));
  }

  @Nullable
  public static final Collection<Item> getAluminumIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/aluminum"));
  }

  @Nullable
  public static final Collection<Item> getSteelIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/steel"));
  }

  @Nullable
  public static final Collection<Item> getBronzeIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/bronze"));
  }

  @Nullable
  public static final Collection<Item> getSilverIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/silver"));
  }

  @Nullable
  public static final Collection<Item> getPlatinumIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/platinum"));
  }

  @Nullable
  public static final Collection<Item> getTitaniumIngots(){
    return getTagCollection(new ResourceLocation("forge:ingots/titanium"));
  }

  public static final boolean match(final Item item, final Collection<Item> list){
    for(final Item check_item : list){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  @SafeVarargs
  public static final Item[] getFilter(final Collection<Item> ... lists){
    int count = 0;
    for(final Collection<Item> list : lists){
      if(list != null){
        count += list.size();
        continue;
      }
      ADDSynthCore.log.error(new NullPointerException(
        "ADDSynthCore.MaterialsUtil.getFilter() has detected a null Item Collection! Maybe one of the functions in MaterialsUtil "+
        "that retrieves all the Items in an Item Tag didn't return anything because there ARE no Items registered to that Item Tag!"
      ));
    }
    final Item[] filter = new Item[count];
    int i = 0;
    for(final Collection<Item> list : lists){
      if(list != null){
        for(final Item item : list){
          filter[i] = item;
          i += 1;
        }
      }
    }
    return filter;
  }

}