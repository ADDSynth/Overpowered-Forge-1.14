package addsynth.core.material;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.NetworkTagCollection;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = Bus.FORGE)
public final class MaterialsUtil {

  // FUTURE: the responders field, registerResponder method, and dispatchEvent method that you see here is
  //         exactly the same as that in the RecipeUtil class. Once we update to Java 9 it is likely that
  //         you can move this common code to an interface and use private access methods, new in Java 9.
  private static final ArrayList<Runnable> responders = new ArrayList<>();

  public static final void registerResponder(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.warn("That function is already registered as an event responder.");
      // Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  private static final void dispatchEvent(){
    for(final Runnable responder : responders){
      responder.run();
    }
  }

  @SubscribeEvent
  public static final void reload_tags_event(final TagsUpdatedEvent event){
    ADDSynthCore.log.info("Tags were Reloaded. Sending update events...");

    final NetworkTagManager tag_manager = event.getTagManager();
    // final NetworkTagCollection<Block> blocks = tag_manager.getBlocks();
    final NetworkTagCollection<Item>  items  = tag_manager.getItems();
    dispatchEvent();

    ADDSynthCore.log.info("Done responding to Tag reload.");
  }

// =======================================================================================

  public static final Collection<Item> getOres(){
    return Tags.Items.ORES.getAllElements();
  }

  public static final Tag<Item> getTag(final ResourceLocation tag_id){
    return ItemTags.getCollection().get(tag_id);
  }

  @Nullable
  public static final Collection<Item> getItemCollection(final ResourceLocation tag_id){
    final Tag<Item> item_tag = getTag(tag_id);
    return item_tag != null ? item_tag.getAllElements() : null;
  }

  public static final Ingredient getTagIngredient(final ResourceLocation tag_id){
    return Ingredient.fromTag(getTag(tag_id));
  }

// =======================================================================================

  // TODO: Move these functions in with the Material's class. Because they already have the material's id name on hand.

  @Nullable
  public static final Collection<Item> getRubyBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/ruby"));
  }

  @Nullable
  public static final Collection<Item> getTopazBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/topaz"));
  }

  @Nullable
  public static final Collection<Item> getCitrineBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/citrine"));
  }

  @Nullable
  public static final Collection<Item> getEmeraldBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/emerald"));
  }

  @Nullable
  public static final Collection<Item> getDiamondBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/diamond"));
  }

  @Nullable
  public static final Collection<Item> getSapphireBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/sapphire"));
  }

  @Nullable
  public static final Collection<Item> getAmethystBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/amethyst"));
  }

  @Nullable
  public static final Collection<Item> getQuartzBlocks(){
    return getItemCollection(new ResourceLocation("forge:storage_blocks/quartz"));
  }

  @Nullable
  public static final Collection<Item> getRubies(){
    return getItemCollection(new ResourceLocation("forge:gems/ruby"));
  }

  @Nullable
  public static final Collection<Item> getTopaz(){
    return getItemCollection(new ResourceLocation("forge:gems/topaz"));
  }

  @Nullable
  public static final Collection<Item> getCitrine(){
    return getItemCollection(new ResourceLocation("forge:gems/citrine"));
  }

  @Nullable
  public static final Collection<Item> getEmeralds(){
    return getItemCollection(new ResourceLocation("forge:gems/emerald"));
  }

  @Nullable
  public static final Collection<Item> getDiamonds(){
    return getItemCollection(new ResourceLocation("forge:gems/diamond"));
  }

  @Nullable
  public static final Collection<Item> getSapphires(){
    return getItemCollection(new ResourceLocation("forge:gems/sapphire"));
  }

  @Nullable
  public static final Collection<Item> getAmethysts(){
    return getItemCollection(new ResourceLocation("forge:gems/amethyst"));
  }

  @Nullable
  public static final Collection<Item> getQuartz(){
    return getItemCollection(new ResourceLocation("forge:gems/quartz"));
  }

  @Nullable
  public static final Ingredient getRubyIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/ruby"));
  }

  @Nullable
  public static final Ingredient getTopazIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/topaz"));
  }

  @Nullable
  public static final Ingredient getCitrineIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/citrine"));
  }

  @Nullable
  public static final Ingredient getEmeraldIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/emerald"));
  }

  @Nullable
  public static final Ingredient getDiamondIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/diamond"));
  }

  @Nullable
  public static final Ingredient getSapphireIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/sapphire"));
  }

  @Nullable
  public static final Ingredient getAmethystIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/amethyst"));
  }

  @Nullable
  public static final Ingredient getQuartzIngredient(){
    return getTagIngredient(new ResourceLocation("forge:gems/quartz"));
  }

  @Nullable
  public static final Collection<Item> getTinIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/tin"));
  }

  @Nullable
  public static final Collection<Item> getCopperIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/copper"));
  }

  @Nullable
  public static final Collection<Item> getAluminumIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/aluminum"));
  }

  @Nullable
  public static final Collection<Item> getSteelIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/steel"));
  }

  @Nullable
  public static final Collection<Item> getBronzeIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/bronze"));
  }

  @Nullable
  public static final Collection<Item> getSilverIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/silver"));
  }

  @Nullable
  public static final Collection<Item> getPlatinumIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/platinum"));
  }

  @Nullable
  public static final Collection<Item> getTitaniumIngots(){
    return getItemCollection(new ResourceLocation("forge:ingots/titanium"));
  }

// =======================================================================================

  public static final boolean match(final Item item, final Collection<Item> list){
    for(final Item check_item : list){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  /** Returns an Item array. To get back a list, use <code>Arrays.asList(Item[] input)</code>.*/
  @SafeVarargs
  public static final Item[] getFilter(final Collection<Item> ... lists){
    int count = 0;
    for(final Collection<Item> list : lists){
      if(list != null){
        count += list.size();
        continue;
      }
      ADDSynthCore.log.error(new NullPointerException(
        MaterialsUtil.class.getName()+".getFilter() has detected a null Item Collection! Maybe one of the functions in MaterialsUtil "+
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
