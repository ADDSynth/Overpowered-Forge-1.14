package addsynth.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import addsynth.core.config.Config;
import addsynth.core.game.Compatability;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.color.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class Debug {

  public static final boolean debug_recipes = false;
  
  public static final void log_recipe(IRecipe recipe){
    if(debug_recipes){
      ADDSynthCore.log.info("Added "+recipe.getClass().getSimpleName()+" "+recipe.getId()+".");
    }
  }

  public static final void block(final Block block){
    block(block, null);
  }

  public static final void block(final Block block, final BlockPos position){
    ADDSynthCore.log.warn(
      "Debug Block: Type: "+block.getClass().getName()+
      ", Registry Name: "+block.getRegistryName()+
      ", Translation Key: "+block.getTranslationKey()+
      (position != null ? ", Position: "+position : ""));
  }

  public static final void item(final Item item){
    ADDSynthCore.log.warn(
      "Debug Item: Type: "+item.getClass().getName()+
      ", Registry Name: "+item.getRegistryName()+
      ", Translation Key: "+item.getTranslationKey());
  }

  public static final void debug(){
    RegistryUtil.safety_check();
    if(Config.debug_mod_detection.get()){
      Compatability.debug();
    }
    if(Config.dump_map_colors.get()){
      ColorUtil.dump_map_colors();
    }
  }

  // This must be run when tags are done being loaded.
  public static final void dump_tags(){
    if(Config.dump_tags.get()){
      try{
        ADDSynthCore.log.info("Begin dumping tags...");
        
        // Sorter
        final boolean prioritize_minecraft_tag = true;
        final class TagComparer implements Comparator<ResourceLocation> {
          @Override
          public int compare(ResourceLocation o1, ResourceLocation o2){
            if(prioritize_minecraft_tag){
              if(o1.getNamespace().equals("minecraft")){
                if(o2.getNamespace().equals("minecraft")){
                  return o1.compareTo(o2);
                }
                return -1;
              }
              if(o2.getNamespace().equals("minecraft")){
                if(o1.getNamespace().equals("minecraft")){
                  return o1.compareTo(o2);
                }
                return 1;
              }
            }
            return o1.compareTo(o2);
          }
        }
        final class RegistryComparer <T extends ForgeRegistryEntry<T>> implements Comparator<ForgeRegistryEntry<T>> {
          @SuppressWarnings("null")
          @Override
          public int compare(ForgeRegistryEntry<T> o1, ForgeRegistryEntry<T> o2){
            return o1.getRegistryName().toString().compareTo(o2.getRegistryName().toString());
          }
        }
        
        final String block_tags_file = "block_tags.txt";
        final String item_tags_file = "item_tags.txt";
        
        // Blocks
        boolean can_write = true;
        final TagCollection<Block> block_tag_collection = BlockTags.getCollection();
        final TreeSet<ResourceLocation> block_tags = new TreeSet<ResourceLocation>(new TagComparer());
        block_tags.addAll(block_tag_collection.getRegisteredTags());
        File file = new File(block_tags_file);
        if(file.exists()){
          can_write = file.delete();
        }
        if(can_write){
          try(final FileWriter writer = new FileWriter(file)){
            writer.write("\nBlock Tags: "+block_tags.size()+"\n\n");
            Tag<Block> block_tag;
            TreeSet<Block> blocks;
            for(final ResourceLocation location : block_tags){
              block_tag = block_tag_collection.get(location);
              if(block_tag != null){
                writer.write(location.toString()+" {\n");
                blocks = new TreeSet<Block>(new RegistryComparer<Block>());
                blocks.addAll(block_tag.getAllElements());
                for(final Block block : blocks){
                  writer.write("  "+block.getRegistryName()+'\n');
                }
                writer.write("}\n\n");
              }
            }
          }
          catch(IOException e){
            e.printStackTrace();
          }
        }
        
        // Items
        can_write = true;
        final TagCollection<Item> item_tag_collection = ItemTags.getCollection();
        final TreeSet<ResourceLocation> item_tags = new TreeSet<ResourceLocation>(new TagComparer());
        item_tags.addAll(item_tag_collection.getRegisteredTags());
        file = new File(item_tags_file);
        if(file.exists()){
          can_write = file.delete();
        }
        if(can_write){
          try(final FileWriter writer = new FileWriter(file)){
            writer.write("\nItem Tags: "+item_tags.size()+"\n\n");
            Tag<Item> item_tag;
            TreeSet<Item> items;
            for(final ResourceLocation location : item_tags){
              item_tag = item_tag_collection.get(location);
              if(item_tag != null){
                writer.write(location.toString()+" {\n");
                items = new TreeSet<Item>(new RegistryComparer<Item>());
                items.addAll(item_tag.getAllElements());
                for(final Item item : items){
                  writer.write("  "+item.getRegistryName()+'\n');
                }
                writer.write("}\n\n");
              }
            }
          }
          catch(IOException e){
            e.printStackTrace();
          }
        }
        
        ADDSynthCore.log.info("Done dumping tags.");
      }
      catch(Exception e){
        ADDSynthCore.log.error("An error occured during tag dumping.", e);
      }
    }
  }

}
