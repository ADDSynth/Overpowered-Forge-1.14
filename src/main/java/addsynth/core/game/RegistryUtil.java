package addsynth.core.game;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.JavaUtils;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

// Remember: The order an item shows up in the creative tab depends on the ID, which is determined
//             when you register that item, so you want to register Items in a certain order.
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
    for(Entry<String, RegistryUtil> entry : registryutil_global_cache.entrySet()){
      registry = entry.getValue();
      // Blocks
      for(Block block : registry.blocks){
        location1 = block.getRegistryName();
        if(location1 == null){
          ADDSynthCore.log.fatal(new NullPointerException("WHY IS THERE A NULL BLOCK IN THE BLOCK LIST???? HOW DOES THAT EVEN HAPPEN?"));
          continue;
        }
        for(Entry<String, RegistryUtil> entry2 : registryutil_global_cache.entrySet()){
          for(Block block2 : entry2.getValue().blocks){
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
      for(Item item : registry.items){
        location1 = item.getRegistryName();
        if(location1 == null){
          ADDSynthCore.log.fatal(new NullPointerException("WHY IS THERE A NULL ITEM IN THE ITEM LIST???? HOW IN THE HECK DID THAT HAPPEN???"));
          continue;
        }
        for(Entry<String, RegistryUtil> entry2 : registryutil_global_cache.entrySet()){
          for(Item item2 : entry2.getValue().items){
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
          StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
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
    for(Item item : item_set){
      if(item instanceof BlockItem){
        if(((BlockItem)item).getBlock() == block){
          return (BlockItem)item;
        }
      }
    }
    return null;
  }

  /** Generic implementation of the RegistryUtil.getItemBlock() method. Searches through all
   *  registered Lists of items for your ItemBlock.
   */
  public static final BlockItem getRegisteredItemBlock(final Block block){
    BlockItem item_block = null;
    try{
      if(block == null){
        throw new NullPointerException("Block input for RegistryUtil.getItemBlock() was null reference.");
      }
      item_block = getVanillaItemBlock(block);
      if(item_block == null){ // search global item store
        for(Entry<String, RegistryUtil> entry : registryutil_global_cache.entrySet()){
          item_block = getModdedItemBlock(block, entry.getValue().items); // created but not yet registered
          if(item_block != null){
            break;
          }
        }
        if(item_block == null){
          throw new NullPointerException(
            "No ItemBlock exists for "+block.toString()+"! The generic version of RegistryUtil.getItemBlock()"+
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

  /** <p>Why use this? Because it checks for a few simple things. First it checks the value you're
   *  passing is not null, which could happen if you try to register your block before it's
   *  initialized. It checks if you've already added that block's registery name just like
   *  Forge does, and it adds the block to a list, just in case you want to register all blocks
   *  by calling {@link #block_registry_event(IForgeRegistry)}. And it automatically adds
   *  the correct translation key by prefixing the block's name with your mod's ID.
   *  Finally, if there WERE an error, it just reports it instead of crashing!
   *  <p>Hmm. I <i>could</i> register ItemBlocks as soon as modders register a block (by passing a
   *  boolean value to this function), but I don't for two reasons. We make modders register
   *  ItemBlocks themselves either through {@link #getItemBlock} or by explicitly registering
   *  ItemBlocks by calling {@link #register_ItemBlock(Block)}, both return a reference to the
   *  ItemBlock in case they need it. The second reason is so that modders can register ItemBlocks
   *  using a custom ItemBlock class which they can use to register ItemBlocks by calling
   *  {@link #getItemBlock(Block, Class, Object...)}.
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
      block.setTranslationKey(mod_id+"."+name); // because translation keys are not namespaced.
      blocks.add(block);
      print_debug(mod_id, "Registered block: "+block.getRegistryName());
      return;
    }
    ADDSynthCore.log.error("Tried to register "+name+" Block after one is already registered!");
  }

  public final void register_item(final Item item, final String name){
    if(item == null){
      ADDSynthCore.log.error(new NullPointerException("Stupid developer. You tried to register a null item reference."));
      Thread.dumpStack();
      return;
    }
    if(item.getRegistryName() == null){
      item.setRegistryName(name);
      item.setTranslationKey(mod_id+"."+name);
      items.add(item);
      print_debug(mod_id, "Registered item: "+item.getRegistryName());
      return;
    }
    ADDSynthCore.log.error("Tried to register "+name+" Item after one was already registered!");
  }

  /** <p>There are now 2 ways to register ItemBlocks. The first is by calling {@link #getItemBlock(Block)}, which
   *  automatically creates an ItemBlock if one doesn't already exist. We prefer you use this way because you never
   *  know which <code>getItemBlock()</code> will be called first, and each call always returns the same reference.
   *  Even if your block has a custom ItemBlock, you can pass it as a class and it will make an ItemBlock using the
   *  class you specified.
   *
   *  <p>The second way is by keeping a seperate itemBlock variable reference and calling this <code>register_ItemBlock()</code>
   *  function to register and return a new ItemBlock for your block. We only recommend using this method if you
   *  reference the ItemBlock form of the block many times, because referring to a dedicated reference is much faster
   *  than searching through a list of Items for the one already registered.
   *
   *  @param block The block you want to register an ItemBlock for. Cannot be null!
   */
  public final BlockItem register_ItemBlock(final Block block){
    if(block == null){
      throw new NullPointerException("Tried to register an ItemBlock for a null block reference! Register your block first!");
    }
    return register_ItemBlock(new BlockItem(block), block);
  }

  /** <p>There are now 2 ways to register ItemBlocks. The first is by calling {@link #getItemBlock(Block)}, which
   *  automatically creates an ItemBlock if one doesn't already exist. We prefer you use this way because you never
   *  know which <code>getItemBlock()</code> will be called first, and each call always returns the same reference.
   *  Even if your block has a custom ItemBlock, you can pass it as a class and it will make an ItemBlock using the
   *  class you specified.
   *
   *  <p>The second way is by keeping a seperate itemBlock variable reference in your Init class, and call this
   *  <code>register_ItemBlock()</code> function in your custom ItemBlock constructor. We only recommend using this
   *  method if (1) you're using a custom ItemBlock and (2) you reference the ItemBlock form of the block many times,
   *  because refering to a dedicated reference is much faster than searching through a list of Items for the one we
   *  already registered.
   *
   * @param itemblock
   */
  public final void register_ItemBlock(@Nonnull final BlockItem itemblock){
    register_ItemBlock(itemblock, itemblock.getBlock());
  }

  /**
   * @param itemblock
   * @param block
   * @return Returns the passed in ItemBlock if we successfully registered it, returns null otherwise. We do this because this
   *   function is primarily called by the {@link #getItemBlock(Block, Class, Object...)} function to create new ItemBlocks.
   */
  private final BlockItem register_ItemBlock(@Nonnull final BlockItem itemblock, @Nonnull final Block block){
    if(block.getRegistryName() == null){
      ADDSynthCore.log.error(new RuntimeException("Unable to create new ItemBlock because the input block does not have its registry name set. Please call the setRegistryName() function!"));
      Thread.dumpStack();
      return null;
    }
    if(itemblock.getRegistryName() == null){
      itemblock.setRegistryName(block.getRegistryName());
      items.add(itemblock);
      print_debug(mod_id, "Registered ItemBlock for block: "+block.getRegistryName());
      return itemblock;
    }
    ADDSynthCore.log.error("Tried to register an ItemBlock for "+block+" after one was already registered!");
    return itemblock;
  }

  /**
   * This is an advanced form of {@link Item#getItemFromBlock(Block)} that returns the ItemBlock
   * if it exists, and returns a new ItemBlock if it doesn't.
   * USE THIS INSTEAD OF <code>Item.getItemFromBlock()</code>! Especially if you're trying
   * to get the ItemBlock of a Mod item!
   *
   * Vanilla ItemBlocks already exist, so all new ItemBlocks must be from a mod.
   * Therefore, we add the Items to an internal list as soon as they're created
   * in order to avoid creating multiple ItemBlocks of the same block.
   *
   * @param block
   * @return a new ItemBlock, or the existing one if one already exists.
   */
  public final BlockItem getItemBlock(final Block block){
    return getItemBlock(block, BlockItem.class);
  }

  /**
   * This is an advanced form of {@link Item#getItemFromBlock(Block)} that returns the ItemBlock
   * if it exists, and returns a new ItemBlock if it doesn't.
   * USE THIS INSTEAD OF <code>Item.getItemFromBlock()</code>! Especially if you're trying
   * to get the ItemBlock of a Mod item!
   *
   * Vanilla ItemBlocks already exist, so all new ItemBlocks must be from a mod.
   * Therefore, we add the Items to an internal list as soon as they're created
   * in order to avoid creating multiple ItemBlocks of the same block.
   *
   * @param block
   * @param itemBlock_class if this block uses a custom ItemBlock then pass its class type to
   *          use if an ItmeBlock for this block doesn't already exist.
   * @param args These are optional arguments that will be used to call your custom ItemBlock
   *          Class, in addition to the required Block input argument which will always be
   *          the first argument, so structure your ItemBlock constructors accordingly.
   * @return a new ItemBlock, or the existing one if one already exists.
   */
  public final BlockItem getItemBlock(final Block block, @Nonnull final Class<? extends BlockItem> itemBlock_class, final Object ... args){
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
          // creates a new ItemBlock instance, which also sets unlocalized name and creative tab.
          item_block = InvokeCustomItemBlock(itemBlock_class, block, args);
          if(item_block != null){
            register_ItemBlock(item_block, block);
          }
        }
      }
    }
    catch(Throwable e){
      e.printStackTrace();
    }
    return item_block;
  }

  /** <p>Normally, I would use the new {@link JavaUtils#InvokeConstructor(Class, Object...)} function for
   *  a job such as this, but the {@link Class#getConstructor(Class...)} function explicitdly searches
   *  for a constructor that matches the types it is given. So let's say the input block is of type
   *  MusicBox. Even though MusicBox is a subtype of the base class Block, the <code>getConstructor()</code>
   *  method will look for a constructor that takes in a MusicBox as one of the parameters, instead of
   *  a Block type. That is why for the list of types we explicitly provide the Block class as the first
   *  argument. And nope, we can't even cast MusicBox to the Block class either, it would still return
   *  MusicBox as its type.
   *  <p>Note: Okay, I've just added some extra information to the {@link JavaUtils#InvokeConstructor(Class, Object...)}
   *  for when it can't find a constructor for the arguments that you specify. This should be VERY helpful
   *  when debugging this function. I could add an extra feature that detects there's only 1 constructor
   *  and attempt to use that constructor with the given arguments anyway. But I dediced that was too much
   *  work to do for now and wasn't worth the effort so I left it for another time.
   * @param clazz
   * @param block
   * @param args
   */
  private final static BlockItem InvokeCustomItemBlock(final Class<? extends BlockItem> clazz, final Block block, final Object ... args){
    // return JavaUtils.InvokeConstructor(clazz, JavaUtils.combine_arrays(new Object[] {(Block)block}, args));
    BlockItem item = null;
    final Class[] arg_types = JavaUtils.combine_arrays(new Class[] {Block.class}, JavaUtils.getTypes(args));
    final Object[] final_args = JavaUtils.combine_arrays(new Object[] {block}, args);
    try{
      item = clazz.getConstructor(arg_types).newInstance(final_args);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return item;
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

  /** Since registering the Inventory model for all your items is so common in my opinion,
   *  call this method during your mod's {@link ModelRegistryEvent} to automatically register
   *  Inventory Item models for all your items. It also assists in debugging common errors.
   */
  public final void register_inventory_item_models(){
    String item_string;
    ResourceLocation registryname;
    for(Item item : items){
      if(item != null){
        registryname = item.getRegistryName();
        item_string = "'"+item.getClass().getName()+", "+(registryname != null ? registryname : item.toString())+"'";
        if(registryname == null){
          ADDSynthCore.log.error(new NullPointerException("Cannot register Inventory Item Model for item "+item_string+" because it doesn't have its registryname set!"));
          continue; // otherwise it crashes
        }
        if(registryname.getNamespace().equals(this.mod_id) == false){
          //ADDSynthCore.log.warn("The item "+item_string+" is not registered with the expected mod "+this.mod_id+". It is actually registered with the "+registryname.getNamespace()+" mod.");
        }
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(registryname, "inventory"));
      }
    }
  }

}
