package addsynth.core.game;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

// Remember: The order an item shows up in the creative tab depends on
//             the order you register Items in the Item Registry Event.
// https://www.google.com/search?q=mod+development+how+can+you+make+things+show+up+in+creative+tab+in+certain+order

public final class RegistryUtil { // cannot be named GameRegistry because it conflicts with net.minecraftforge.fml.common.registry.GameRegistry;

  /** Goes through our global registry cache and searches for duplicate entries, which could
   *  happen if things are being registered improperly. Very useful for catching errors.
   *  (This actually processes a lot faster than I expected! I'm checking for EVERYTHING!)
   */
  public static final void safety_check(){
    ADDSynthCore.log.info("Begin safety checking ADDSynthCore.RegistryUtil...");
    RegistryUtil registry;
    ResourceLocation location1;
    ResourceLocation location2;
    for(final Entry<String, RegistryUtil> entry : registryutil_global_cache.entrySet()){
      registry = entry.getValue();
      // Blocks
      for(final Block block : registry.blocks){
        location1 = block.getRegistryName();
        if(location1 == null){
          ADDSynthCore.log.fatal(new NullPointerException("WHY IS THERE A NULL BLOCK IN THE BLOCK LIST???? HOW DOES THAT EVEN HAPPEN?"));
          continue;
        }
        for(final Entry<String, RegistryUtil> entry2 : registryutil_global_cache.entrySet()){
          for(final Block block2 : entry2.getValue().blocks){
            if(block != block2){
              location2 = block2.getRegistryName();
              if(location2 != null){
                if(location1.equals(location2)){
                  ADDSynthCore.log.fatal("Found duplicate blocks with the same registry name of "+location1+"! Please keep "+location1.getPath()+" to a single mod only!");
                }
              }
            }
          }
        }
      }
      // Items
      for(final Item item : registry.items){
        location1 = item.getRegistryName();
        if(location1 == null){
          ADDSynthCore.log.fatal(new NullPointerException("WHY IS THERE A NULL ITEM IN THE ITEM LIST???? HOW IN THE HECK DID THAT HAPPEN???"));
          continue;
        }
        for(final Entry<String, RegistryUtil> entry2 : registryutil_global_cache.entrySet()){
          for(final Item item2 : entry2.getValue().items){
            if(item != item2){
              location2 = item2.getRegistryName();
              if(location2 != null){
                if(location1.equals(location2)){
                  ADDSynthCore.log.fatal("Found duplicate items with the same registry name of "+location1+"! Please keep "+location1.getPath()+" to a single mod only!");
                }
              }
            }
          }
        }
      }
    }
    ADDSynthCore.log.info("Done checking RegistryUtil.");
  }

  private static final boolean debug = false;
  /** Determines how much of the stacktrace to print to the debug log file.
   *  Set to -1 to print the whole stacktrace, or 0 to not print. */
  private static final int max_stacktrace = 8;
  private static File file;

  /** This prints to a log file in the Minecraft directory and also prints messages in the default log with
   *  TRACE level, so they don't show up in the Console.
   *  A stacktrace is printed in the custom log file but not the standard log files.
   * @param mod
   * @param debug_text
   */
  private static final void print_debug(final String mod, final String debug_text){
    if(debug){
      try{
        if(file == null){
          file = new File("debug_addsynthcore_registryutil.log");
          if(file.exists()){
            file.delete();
          }
          file.createNewFile();
        }
        final FileWriter writer = new FileWriter(file,true);
        try{
          final String debug_message = (StringUtil.StringExists(mod) ? "from the "+mod+" mod: " : "from unknown mod: ")+debug_text;
          ADDSynthCore.log.trace(debug_message);
          writer.write(debug_message+"\n");
          int i;
          final int skip = 2;
          final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
          for(i = skip; (max_stacktrace >= 0 ? i < max_stacktrace + skip : true) && i < stacktrace.length; i++){
            writer.write("  "+stacktrace[i].toString()+"\n");
          }
          writer.write("\n");
        }
        catch(Exception e){
          e.printStackTrace();
        }
        writer.close();
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }
  }

  private static final HashMap<String, RegistryUtil> registryutil_global_cache = new HashMap<>(15,1.0f);

  public static final void register(final IForgeRegistry<Item> registry, final BlockItem item){
    final ResourceLocation registry_name = item.getBlock().getRegistryName();
    if(registry_name == null){
      ADDSynthCore.log.error(new IllegalArgumentException("Cannot register ItemBlock for Block: "+StringUtil.getName(item.getBlock())+", because it doesn't have its RegistryName set!"));
      return;
    }
    item.setRegistryName(registry_name);
    registry.register(item);
  }

  public static final <T extends IForgeRegistryEntry<T>> void register(final IForgeRegistry<T> registry, final T object, final ResourceLocation id){
    object.setRegistryName(id); // OPTIMIZE: This way is FAR superior to the old way of registering. Move to registering this way only for Minecraft versions 1.14+ and finalize in Overpowered v1.4.
    registry.register(object);
  }

  /** As soon as items are registered in the Item Registry event, all of your ItemBlocks are now
   *  registered in the system and this function will correctly return your ItemBlock regardless
   *  of which {@link RegistryUtil} instance you used to get the ItemBlock. But until then,
   *  you need to ensure you use the same RegistryUtil instance to get ItemBlocks.
   * @param block
   */
  private static @Nullable final BlockItem getVanillaItemBlock(@Nonnull final Block block){
    final Item vanilla_item = Item.BLOCK_TO_ITEM.get(block);
    if(vanilla_item != null){
      if(vanilla_item != Items.AIR){
        return (BlockItem)vanilla_item;
      }
    }
    return null;
  }

  private static @Nullable final BlockItem getModdedItemBlock(@Nonnull final Block block, @Nonnull final HashSet<Item> item_set){
    for(final Item item : item_set){
      if(item instanceof BlockItem){
        if(((BlockItem)item).getBlock() == block){
          return (BlockItem)item;
        }
      }
    }
    return null;
  }

  /** Generic implementation of the {@link #getItemBlock(Block)} method.
   *  Searches through all registered Lists of items for your ItemBlock.
   */
  public static final BlockItem getRegisteredItemBlock(final Block block){
    BlockItem item_block = null;
    try{
      if(block == null){
        throw new NullPointerException("Block input for RegistryUtil.getItemBlock() was null reference.");
      }
      item_block = getVanillaItemBlock(block);
      if(item_block == null){ // search global item store
        for(final Entry<String, RegistryUtil> entry : registryutil_global_cache.entrySet()){
          item_block = getModdedItemBlock(block, entry.getValue().items); // created but not yet registered
          if(item_block != null){
            break;
          }
        }
        if(item_block == null){
          throw new NullPointerException(
            "No ItemBlock exists for "+StringUtil.getName(block)+"! The generic version of RegistryUtil.getItemBlock()"+
            "wasn't able to find an ItemBlock and can't create one for you! You must call your mod's own instance"+
            "of the RegistryUtil class to get and/or create ItemBlocks for that block!"
          );
        }
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return item_block;
  }

//============================================================================================================

  private final String mod_id;

  private final HashSet<Block> blocks = new HashSet<Block>(100);
  private final HashSet<Item> items = new HashSet<Item>(200);
  // private static final ArrayList<ItemBlock> item_blocks;

  public RegistryUtil(@Nonnull final String mod_id){
    this.mod_id = mod_id;
    registryutil_global_cache.put(mod_id, this);
  }

  /** <p>Why use this? Because it checks for a few basic things. First it checks the value you're
   *  passing is not null, which could happen if you try to register your block before it's
   *  initialized. It checks if you've already added that block's registery name just like
   *  Forge does, and it adds the block to a list, just in case you want to register all blocks
   *  by calling {@link #block_registry_event(IForgeRegistry)}. And it automatically adds
   *  the correct translation key by prefixing the block's name with your mod's ID.
   *  Finally, if there WERE an error, it just reports it instead of crashing!
   *  <p>Register ItemBlocks by calling either {@link #register_ItemBlock(Block, Item.Properties)}
   *  or {@link #register_ItemBlock(BlockItem)}.
   * @param block The block you want to register.
   * @param name The block id name you want to register this block as.
   */
  public final void register_block(final Block block, final String name){
    if(block == null){
      ADDSynthCore.log.error(new NullPointerException("Hey dumbass: you tried to register a null block reference."));
      Thread.dumpStack();
      return;
    }
    if(block.getRegistryName() == null){
      block.setRegistryName(name); // I was going to add the mod id to the registry name as well, but Forge just gets the mod id itself anyway.
      blocks.add(block);
      print_debug(mod_id, "Registered block: "+block.getRegistryName());
      return;
    }
    ADDSynthCore.log.error("Tried to register "+name+" Block after one is already registered!");
  }

  /** This registers your block, and also creates a vanilla ItemBlock using
   *  the Item Properties that you specify.
   * @param block
   * @param name
   * @param properties
   */
  public final void register_block(@Nonnull final Block block, final String name, final Item.Properties properties){
    register_block(block, name);
    register_ItemBlock(block, properties);
  }

  /** <p>Use this to register Items.
   *  <p>Create a new base class that overrides the {@link Item} class, then call this in the constructor.
   * @param item
   * @param name
   */
  public final void register_item(final Item item, final String name){
    if(item == null){
      ADDSynthCore.log.error(new NullPointerException("Stupid developer. You tried to register a null item reference."));
      Thread.dumpStack();
      return;
    }
    if(item.getRegistryName() == null){
      item.setRegistryName(name);
      items.add(item);
      print_debug(mod_id, "Registered item: "+item.getRegistryName());
      return;
    }
    ADDSynthCore.log.error("Tried to register "+name+" Item after one was already registered!");
  }

  /** Use this to register vanilla ItemBlocks.
   *  @param block The block you want to register an ItemBlock for. Cannot be null!
   */
  public final BlockItem register_ItemBlock(final Block block, final Item.Properties properties){
    if(block == null){
      throw new NullPointerException("Tried to register an ItemBlock for a null block reference! Register your block first!");
    }
    return register_ItemBlock(new BlockItem(block, properties), block);
  }

  /** <p>Use this to register custom ItemBlocks.
   *  <p>Call this in your block's constructor, AFTER registering the block.
   * @param itemblock
   */
  public final void register_ItemBlock(@Nonnull final BlockItem itemblock){
    register_ItemBlock(itemblock, itemblock.getBlock());
  }

  /**
   * @param itemblock
   * @param block
   * @return Returns the passed in ItemBlock if we successfully registered it, returns null otherwise.
   */
  private final BlockItem register_ItemBlock(@Nonnull final BlockItem itemblock, @Nonnull final Block block){
    final ResourceLocation registry_name = block.getRegistryName();
    if(registry_name == null){
      ADDSynthCore.log.error(new RuntimeException("Unable to create new ItemBlock because the input block does not have its registry name set. Please call the setRegistryName() function!"));
      Thread.dumpStack();
      return null;
    }
    if(itemblock.getRegistryName() == null){
      itemblock.setRegistryName(registry_name);
      items.add(itemblock);
      print_debug(mod_id, "Registered ItemBlock for block: "+registry_name);
      return itemblock;
    }
    ADDSynthCore.log.error("Tried to register an ItemBlock for "+StringUtil.getName(block)+" after one was already registered!");
    return itemblock;
  }

  /**
   * <p>This is our version of {@link Item#getItemFromBlock(Block)}. This function returns the ItemBlock
   *    for the block you specify, if it exists.
   * <p>We prefer you use this because the vanilla method returns Blocks.AIR, if it can't find the ItemBlock
   *    for your block, and doesn't warn you.
   * @param block
   * @return the existing one or null.
   */
  public final BlockItem getItemBlock(final Block block){
    BlockItem item_block = null;
    try{
      // safety check
      if(block == null){
        throw new NullPointerException("Block input for RegistryUtil.getItemBlock() was null reference.");
      }
      item_block = getVanillaItemBlock(block); // check 1 (already registered?)
      if(item_block == null){
        item_block = getModdedItemBlock(block, items); // check 2 (created but not registered?)
        if(item_block == null){
          ADDSynthCore.log.fatal(
            "No ItemBlock exists for "+StringUtil.getName(block)+". ItemBlocks should've been registered when you"+
            "called RegistryUtil.register_block() or register_ItemBlock() with your preferred Item.Properties!"
          );
        }
      }
    }
    catch(Throwable e){
      e.printStackTrace();
    }
    return item_block;
  }

  // FEATURE: some blocks are registered just to be present in the game, not for the player to use,
  //  therefore they wouldn't have ItemBlocks. So to effectively use this I'd need to seperate the
  //  blocks that are expected to have an ItemBlock against blocks that aren't supposed to have one.
  //  maybe it would be nice to have this because one time, a block needed an ItemBlock to go with
  //  it, but I decided to have a custom ItemBlock which I registered elsewhere, but I didn't register
  //  it properly so it wasn't really registerd.
  public final void check_registry(){
  }

  /** This will register all of the blocks in the {@link #blocks} list.
   *  Call this function during your Block Registry event. Please make sure the
   *  <code>public static final</code> variables holding instances of your blocks are
   *  initialized first.
   * @param registry use event.getRegistry()
   */
  public final void block_registry_event(final IForgeRegistry<Block> registry){
    for(final Block block : blocks){
      if(block == null){
        ADDSynthCore.log.error("Blocks were not initialized before Block Registry Event!");
        break;
      }
      registry.register(block);
    }
  }

}
