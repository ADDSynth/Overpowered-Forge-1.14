package addsynth.core.game;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** <p>Icons are used when using ADDSynthCore to create new Creative Tabs or Achievements.
 *  <p>The first argument is the item you want to use. The second argument is a boolean value
 *     on whether that item is avialable to use. Pass true if you always want to use it.
 *     But there may be times when you want the player to be able to disable some things in
 *     a configuration file for example. In this case pass the value on whether its enabled or
 *     disabled. The Icon immediately following your preferred Icon MUST be a Vanilla
 *     item so we have something to fall back on in case your preferred Icon is disabled.
 *  <p>To pass in ItemBlocks you MUST use your mod's {@link RegistryUtil} class instance.
 *  <p>I created this feature because Creative Tabs and Achievements that used an icon that was
 *     disabled were not displaying their icon properly or not at all.
 * @author ADDSynth
 * @since October 23, 2019
 */
public final class Icon {

    public final ItemStack item;
    public final boolean available;
    
    public Icon(final Item item, final boolean available){
      this.item = new ItemStack(item, 1);
      this.available = available;
    }
    
    public Icon(final Item item){
      if(MinecraftUtility.isVanilla(item)){
        this.item = new ItemStack(item, 1);
        this.available = true;
      }
      else{
        // final IllegalArgumentException e = 
        // e.printStackTrace();
        throw new IllegalArgumentException(Icon.class.getName()+": When creating a new Game Icon, specifying a single Item without the boolean value MUST be a Vanilla Item!");
      }
    }

    public static final ItemStack get(final Icon[] icons){
      ItemStack stack = null;
      for(Icon icon : icons){
        if(icon.available){
          stack = icon.item;
          break;
        }
      }
      if(stack == null){
        ADDSynthCore.log.warn(new NullPointerException("Icon.get() failed to get a valid Icon for your Creative Tab or Achievement."));
        Thread.dumpStack();
      }
      return stack;
    }

  }
